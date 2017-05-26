package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
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
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {

	private static final String USER_DIR = "user.dir";

	private static final String SERVER_WORKERS = "server.workers";

	private static final String SERVER_MIME_CONFIG = "server.mimeConfig";

	private static final String SESSION_TIMEOUT = "session.timeout";

	private static final String SERVER_DOCUMENT_ROOT = "server.documentRoot";

	private static final String SERVER_WORKER_THREADS = "server.workerThreads";

	private static final String SERVER_PORT = "server.port";

	private static final String SERVER_ADDRESS = "server.address";

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

	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	private Random sessionRandom = new Random();

	public SmartHttpServer(String configFileName) {
		parse(configFileName);
	}

	private void parse(String configFileName) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(System.getProperty(USER_DIR) + "/config/" + configFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.address = prop.getProperty(SERVER_ADDRESS);
		this.port = Integer.valueOf(prop.getProperty(SERVER_PORT));
		this.workerThreads = Integer.valueOf(prop.getProperty(SERVER_WORKER_THREADS));
		this.documentRoot = Paths.get(prop.getProperty(SERVER_DOCUMENT_ROOT));
		this.sessionTimeout = Integer.valueOf(prop.getProperty(SESSION_TIMEOUT));

		parseMime(prop.getProperty(SERVER_MIME_CONFIG));
		parseWorkers(prop.getProperty(SERVER_WORKERS));
	}

	private void parseWorkers(String workersConfigFilePath) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(workersConfigFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<Object, Object> entry : prop.entrySet()) {

			IWebWorker iww = findIWWFromEntry(entry);
			Path path = Paths.get(System.getProperty(USER_DIR) + "/webroot/" + entry.getKey().toString());

			workersMap.put(path.toString(), iww);
		}

	}

	private IWebWorker findIWWFromEntry(Entry<Object, Object> entry) {
		Class<?> referenceToClass;
		Object iwwObject = null;

		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(entry.getValue().toString());
			iwwObject = referenceToClass.newInstance();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return (IWebWorker) iwwObject;
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
				ClearThread thread = new ClearThread();
				thread.start();
				while (isAlive()) {
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

	private static class SessionMapEntry {

		String sid;

		long validUntil;

		Map<String, String> map;
	}

	private class ClearThread extends Thread {

		private static final int SLEEP = 300000;

		@Override
		public void run() {
			while (true) {

				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (sessions) {
					for (Entry<String, SessionMapEntry> entry : sessions.entrySet()) {

						if (entry.getValue().validUntil < System.currentTimeMillis() / 1000) {
							sessions.remove(entry.getKey(), entry.getValue());
						}
					}

				}
			}
		}
	}

	private class ClientWorker implements Runnable, IDispatcher {

		private static final String ALL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		private static final int SID_LENGTH = 20;

		private Socket csocket;

		private PushbackInputStream istream;

		private OutputStream ostream;

		private String version;

		private String method;

		private Map<String, String> params = new HashMap<String, String>();

		private Map<String, String> tempParams = new HashMap<String, String>();

		private Map<String, String> permPrams = new HashMap<String, String>();

		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		private static final String SID = "sid";

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

				List<String> header = extractHeaders();
				if (header.size() < 1) {
					sendError(400, "Bad request");
					return;
				}

				String[] firstLine = header.get(0).trim().split("\\s+");
				if (!(firstLine.length != 3 || firstLine[0].equals("GET") || firstLine[2].equals("HTTP/1.0")
						|| firstLine[2].equals("HTTP/1.1"))) {

					sendError(400, "Bad request");
					return;
				}

				checkSession(header);

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

		private synchronized void checkSession(List<String> header) {
			String sidCandidate = "";

			String domain = findDomain(header);

			for (String line : header) {

				if (!line.trim().startsWith("Cookie:")) {
					continue;
				}
				line = line.substring("Cookie:".length()).trim();
				String[] cookies = line.split(";");

				/*-set sidCandidate and add other cookies to the outputCookies list*/
				sidCandidate = fillCookiesAndFindSidCandidate(sidCandidate, cookies, domain);
			}

			/*-if candidate is empty then generate new entry, otherwise try to find candidate in sessions*/
			SessionMapEntry entry = sidCandidate.equals("") ? generateNewEntry(generateSID(), domain)
					: checkCandidate(sidCandidate, domain);

			permPrams = sessions.get(entry.sid).map;
		}

		private String findDomain(List<String> header) {
			String domain = "";

			for (String line : header) {

				if (!line.trim().startsWith("Host:")) {
					continue;
				}

				line = line.substring("Host:".length()).trim();
				domain = line.substring(0, line.indexOf(":"));
				break;
			}

			return domain.equals("") ? address : domain;
		}

		private String fillCookiesAndFindSidCandidate(String sidCandidate, String[] cookies, String domain) {

			for (String cookie : cookies) {

				String[] splitedCookie = cookie.split("=");
				String cookieName = splitedCookie[0].trim();
				String cookieValue = splitedCookie[1].substring(1, splitedCookie[1].length() - 1).trim();

				if (cookieName.equals(SID)) {
					sidCandidate = cookieValue;
				} else {
					outputCookies.add(new RCCookie(cookieName, cookieValue, null, domain, "/", false));
				}
			}

			return sidCandidate;
		}

		private SessionMapEntry checkCandidate(String sidCandidate, String domain) {

			SessionMapEntry entry = sessions.get(sidCandidate);

			if (entry == null || entry.validUntil < (System.currentTimeMillis() / 1000)) {
				sessions.remove(sidCandidate);

				/*-entry is invalid, generate new one*/
				entry = generateNewEntry(sidCandidate, domain);

			} else {
				entry.validUntil = (System.currentTimeMillis() / 1000) + sessionTimeout;
			}

			return entry;
		}

		private SessionMapEntry generateNewEntry(String sid, String domain) {

			SessionMapEntry entry = new SessionMapEntry();

			entry.sid = sid;
			entry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			entry.map = new ConcurrentHashMap<>();
			entry.map.put(SID, entry.sid);

			for (RCCookie cookie : outputCookies) {
				entry.map.put(cookie.getName(), cookie.getValue());
			}

			sessions.put(entry.sid, entry);
			outputCookies.add(new RCCookie(SID, entry.sid, null, domain, "/", true));

			return entry;
		}

		private String generateSID() {

			char[] sid = new char[SID_LENGTH];

			for (int i = 0; i < SID_LENGTH; i++) {
				sid[i] = ALL_CHARACTERS.charAt(sessionRandom.nextInt(ALL_CHARACTERS.length()));
			}

			return String.valueOf(sid);
		}

		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (urlPath.startsWith("/private") && directCall) {
				sendError(405, "Access denied");
				return;
			}

			if (urlPath.startsWith("/ext/")) {
				IWebWorker iww = findIWWFromPath(urlPath);
				initializeContext();
				iww.processRequest(context);
				return;
			}
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));

			if (workersMap.get(requestedPath.toString()) != null) {
				initializeContext();
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

			String extension = findExtension(requestedPath);
			String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");

			if (extension.equals("smscr")) {
				initializeContext();

				SmartScriptParser parser = new SmartScriptParser(new String(Files.readAllBytes(requestedPath)));
				new SmartScriptEngine(parser.getDocumentNode(), context).execute();

			} else {
				initializeContext();

				byte[] data = Files.readAllBytes(requestedPath);
				context.setMimeType(mimeType);
				context.write(data);
			}
		}

		private IWebWorker findIWWFromPath(String urlPath) {
			Class<?> referenceToClass;
			Object newObject = null;
			try {
				String classPath = this.getClass().getPackage().getName() + ".workers."
						+ urlPath.substring("/ext/".length());

				referenceToClass = this.getClass().getClassLoader().loadClass(classPath);
				newObject = referenceToClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

				e.printStackTrace();
				System.exit(0);
			}

			IWebWorker iww = (IWebWorker) newObject;
			return iww;
		}

		private void initializeContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
		}

		private String findExtension(Path requestedPath) {
			int i = requestedPath.toString().lastIndexOf('.');

			/*-ako je pronašao točku vraća sve nakon nje, ako nije vraća prazan string*/
			return (i > 0) ? requestedPath.toString().substring(i + 1) : " ";
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

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}
}