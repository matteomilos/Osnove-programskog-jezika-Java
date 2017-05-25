package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {

	public static void main(String[] args) throws FileNotFoundException {

		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();
		while (true) {

		}
	}

	private String address;

	private int port;

	private int workerThreads;

	private int sessionTimeout;

	private Map<String, String> mimeTypes = new HashMap<String, String>();

	private ServerThread serverThread;

	private ExecutorService threadPool;

	private Path documentRoot;

	private Map<String, IWebWorker> workersMap = new HashMap<>();

	public SmartHttpServer(String configFileName) {
		parse(configFileName);
	}

	private void parse(String configFileName) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(System.getProperty("user.dir") + "/config/" + configFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.address = prop.getProperty("server.address");
		this.port = Integer.valueOf(prop.getProperty("server.port"));
		this.workerThreads = Integer.valueOf(prop.getProperty("server.workerThreads"));
		this.documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
		this.sessionTimeout = Integer.valueOf(prop.getProperty("session.timeout"));
		parseMime(prop.getProperty("server.mimeConfig"));
		parseWorkers(prop.getProperty("server.workers"));
	}

	private void parseWorkers(String workersConfigFilePath) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(workersConfigFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<Object, Object> entry : prop.entrySet()) {
			Class<?> referenceToClass;
			Object newObject = null;
			try {
				referenceToClass = this.getClass().getClassLoader().loadClass(entry.getValue().toString());
				newObject = referenceToClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			IWebWorker iww = (IWebWorker) newObject;
			Path path = Paths.get(System.getProperty("user.dir") + "/webroot/" + entry.getKey().toString());
			workersMap.put(path.toString(), iww);
		}

	}

	private void parseMime(String mimeConfigFilePath) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(mimeConfigFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<Object, Object> entry : prop.entrySet()) {
			mimeTypes.put(entry.getKey().toString(), entry.getValue().toString());
		}
	}

	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
		}
		if (!serverThread.isAlive()) {
			serverThread.start();
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
	}

	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	protected class ServerThread extends Thread {

		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (serverThread.isAlive()) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private class ClientWorker implements Runnable, IDispatcher {

		private Socket csocket;

		private PushbackInputStream istream;

		private OutputStream ostream;

		private String version;

		private String method;

		private Map<String, String> params = new HashMap<String, String>();

		private Map<String, String> tempParams = new HashMap<String, String>();

		private Map<String, String> permPrams = new HashMap<String, String>();

		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		private String SID;

		private RequestContext context = null;

		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = extractHeaders();
				if (request.size() < 1) {
					sendError(400, "Bad request");
					return;
				}
				String[] firstLine = request.get(0).trim().split("\\s+");
				if (!(firstLine.length != 3 || firstLine[0].equals("GET") || firstLine[2].equals("HTTP/1.0")
						|| firstLine[2].equals("HTTP/1.1"))) {
					sendError(400, "Bad request");
					return;
				}
				String[] requestedPathArray = firstLine[1].split("\\?");
				String path = requestedPathArray[0];
				if (requestedPathArray.length > 1) {
					String paramString = requestedPathArray[1];
					parseParameters(paramString);
				}

				internalDispatchRequest(path.toString(), true);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));
			if(urlPath.startsWith("/private") && directCall){
				sendError(404, "Access denied");
			}
			if (urlPath.startsWith("/ext/")) {
				Class<?> referenceToClass;
				Object newObject = null;
				try {
					String classPath = this.getClass().getPackage().getName() + ".workers." + urlPath.substring(5);
					referenceToClass = this.getClass().getClassLoader().loadClass(classPath);
					newObject = referenceToClass.newInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					System.exit(0);
				}
				IWebWorker iww = (IWebWorker) newObject;
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies);
				}
				iww.processRequest(context);
				return;
			}
			if (workersMap.get(requestedPath.toString()) != null) {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				}
				workersMap.get(requestedPath.toString()).processRequest(context);
				return;
			}

			if (!requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}
			if (!(Files.exists(requestedPath) && Files.isReadable(requestedPath)
					&& Files.isRegularFile(requestedPath))) {
				sendError(404, "Doesn't exist");

				return;
			}

			String extension = "";
			int i = requestedPath.toString().lastIndexOf('.');
			if (i > 0) {
				extension = requestedPath.toString().substring(i + 1);
			}
			String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");

			if (extension.equals("smscr")) {
				SmartScriptParser parser = new SmartScriptParser(new String(Files.readAllBytes(requestedPath)));

				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
				}
				SmartScriptEngine engine = new SmartScriptEngine(parser.getDocumentNode(), context);
				engine.execute();
			} else {
				if (context == null) {
					context = new RequestContext(ostream, params, permPrams, outputCookies);
				}
				byte[] data = Files.readAllBytes(requestedPath);
				context.setMimeType(mimeType);
				context.write(data);
			}
		}

		private void parseParameters(String paramString) {
			String[] paramArray = paramString.split("&");
			for (String param : paramArray) {
				String[] parameter = param.split("=");
				params.put(parameter[0], parameter[1]);
			}
		}

		private List<String> extractHeaders() throws IOException {
			byte[] request = readRequest(istream);
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			String requestHeader = new String(request, StandardCharsets.US_ASCII);
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		// Jednostavan automat koji čita zaglavlje HTTP zahtjeva...
		private byte[] readRequest(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		private void sendError(int statusCode, String statusText) throws IOException {

			ostream.write(
					("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
							+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n"
							+ "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			ostream.flush();

		}

		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}
}