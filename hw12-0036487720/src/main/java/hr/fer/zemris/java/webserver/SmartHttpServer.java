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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A Web server is a program that uses HTTP (Hypertext Transfer Protocol) to
 * serve the files that form Web pages to users, in response to their requests,
 * which are forwarded by their computers' HTTP clients. This server uses TCP
 * protocol for transferring bytes between server and the client.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartHttpServer {

	/** The Constant USER_DIR. */
	private static final String USER_DIR = "user.dir";

	/** The Constant SERVER_WORKERS. */
	private static final String SERVER_WORKERS = "server.workers";

	/** The Constant SERVER_MIME_CONFIG. */
	private static final String SERVER_MIME_CONFIG = "server.mimeConfig";

	/** The Constant SESSION_TIMEOUT. */
	private static final String SESSION_TIMEOUT = "session.timeout";

	/** The Constant SERVER_DOCUMENT_ROOT. */
	private static final String SERVER_DOCUMENT_ROOT = "server.documentRoot";

	/** The Constant SERVER_WORKER_THREADS. */
	private static final String SERVER_WORKER_THREADS = "server.workerThreads";

	/** The Constant SERVER_PORT. */
	private static final String SERVER_PORT = "server.port";

	/** The Constant SERVER_ADDRESS. */
	private static final String SERVER_ADDRESS = "server.address";

	/**
	 * Method which is called at the start of this program.
	 * 
	 * @param args
	 *            command line arguments, not used in this method
	 * @throws FileNotFoundException
	 *             if {@link FileNotFoundException} occurs
	 */
	public static void main(String[] args) throws FileNotFoundException {

		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();

		while (true) {

		}

	}

	/** The address of the server. */
	private String address;

	/** The port of the server. */
	private int port;

	/** The number of worker threads. */
	private int workerThreads;

	/** The session timeout of the server. */
	private int sessionTimeout;

	/** The mime types map of the server. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/** The server thread. */
	private ServerThread serverThread;

	/** The clients thread poll for this server. */
	private ExecutorService threadPool;

	/** The document root of the server. */
	private Path documentRoot;

	/** The workers map of the server. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/** The active sessions of the server. */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	/** The session generator, used for producing cookie values. */
	private Random sessionRandom = new Random();

	/**
	 * Instantiates a new smart http server.
	 *
	 * @param configFileName
	 *            path to the configuration file
	 */
	public SmartHttpServer(String configFileName) {
		parse(configFileName);
	}

	/**
	 * Parses the configuration file.
	 *
	 * @param configFileName
	 *            path to the configuration file
	 */
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

		parseMimeTypes(prop.getProperty(SERVER_MIME_CONFIG));
		parseWorkers(prop.getProperty(SERVER_WORKERS));
	}

	/**
	 * Parses the workers from configuration file.
	 *
	 * @param workersConfigFilePath
	 *            the workers config file path
	 */
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

	/**
	 * Method used for finding {@link IWebWorker} instance from map entry.
	 *
	 * @param entry
	 *            the entry
	 * @return the instance of {@link IWebWorker}
	 */
	private IWebWorker findIWWFromEntry(Entry<Object, Object> entry) {
		Class<?> referenceToClass;
		Object iwwObject = null;

		try {
			referenceToClass = this.getClass().getClassLoader().loadClass(entry.getValue().toString());
			iwwObject = referenceToClass.newInstance();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}

		return (IWebWorker) iwwObject;
	}

	/**
	 * Parses the mimetypes from configuration file.
	 *
	 * @param mimeConfigFilePath
	 *            the mime config file path
	 */
	private void parseMimeTypes(String mimeConfigFilePath) {
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

	/**
	 * Starts the {@link SmartHttpServer}.
	 */
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

	/**
	 * Stops {@link SmartHttpServer}.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}

	/**
	 * The Class ServerThread represents thread that is waiting for clients
	 * request and responses properly.
	 */
	protected class ServerThread extends Thread {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
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

	/**
	 * The Class SessionMapEntry represents an encapsulation for one session.
	 */
	private static class SessionMapEntry {

		/** The sid. */
		String sid;

		/** The valid until. */
		long validUntil;

		/** The map. */
		Map<String, String> map;
	}

	/**
	 * The Class ClearThread that periodically (in this e.g. minutes) goes
	 * through all session records and removes expired sessions from
	 * {@link SmartHttpServer#sessions} map.
	 */
	private class ClearThread extends Thread {

		/** The sleep time of the thread. */
		private static final int SLEEP = 300000;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while (true) {

				try {
					Thread.sleep(SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (sessions) {
					Map<String, SessionMapEntry> help = new HashMap<>(sessions);
					for (Entry<String, SessionMapEntry> entry : help.entrySet()) {

						if (entry.getValue().validUntil < System.currentTimeMillis() / 1000) {
							sessions.remove(entry.getKey(), entry.getValue());
						}
					}
				}
			}
		}
	}

	/**
	 * The Class ClientWorker is thread that is dispatched each time a new
	 * request is sent from client to server.
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** The Constant ALL_CHARACTERS. */
		private static final String ALL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		/** The Constant SID_LENGTH. */
		private static final int SID_LENGTH = 20;

		/** The client socket. */
		private Socket csocket;

		/** The input stream. */
		private PushbackInputStream istream;

		/** The output stream. */
		private OutputStream ostream;

		/** The version of the HTTP protocol. */
		private String version;

		/** The method of request. */
		private String method;

		/** The parameters map. */
		private Map<String, String> params = new HashMap<String, String>();

		/** The temporary parameters map. */
		private Map<String, String> tempParams = new HashMap<String, String>();

		/** The permanent parameters map. */
		private Map<String, String> permPrams = new HashMap<String, String>();

		/** The output cookies for the browser. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/** The Constant SID. */
		private static final String SID = "sid";

		/** The context. */
		private RequestContext context = null;

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket
		 *            the client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {

				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> header = extractHeaders();
				if (header.size() < 1) {
					sendError(400, "Header size less than one.");
					return;
				}

				String[] firstLine = header.get(0).trim().split("\\s+");
				if (firstLine.length < 3) {
					sendError(400, "Invalid first line of the header");
					return;
				}

				method = firstLine[0].toUpperCase().trim();
				version = firstLine[2].toUpperCase().trim();

				if (!(method.equals("GET") || version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
					sendError(400, "Invalid method or version.");
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

		/**
		 * Method that checks if there is a session associated with a sessionID,
		 * provided within cookies, still active, if not a new sessionID is
		 * provided or current is prolonged for a preset timeout.
		 *
		 * @param header
		 *            header of the http request
		 */
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
			SessionMapEntry entry = sidCandidate.equals("")	? generateNewEntry(generateSID(), domain)
															: checkCandidate(sidCandidate, domain);

			permPrams = sessions.get(entry.sid).map;
		}

		/**
		 * Method that checks if domain is specified in header, returns domain
		 * or empty string if it is not found.
		 *
		 * @param header
		 *            the header of the http request
		 * @return the domain
		 */
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

		/**
		 * Method that adds all the cookies from header to the
		 * {@link #outputCookies} list and finds the candidate for sid.
		 *
		 * @param sidCandidate
		 *            the sid candidate
		 * @param cookies
		 *            the cookies
		 * @param domain
		 *            the domain
		 * @return the found sid candidateF
		 */
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

		/**
		 * Checks if candidate is in {@link SmartHttpServer#sessions} map and if
		 * it is still valid.
		 *
		 * @param sidCandidate
		 *            the sid candidate
		 * @param domain
		 *            the domain
		 * @return new entry if sidcandidate is invalid, otherwise entry from
		 *         sessions map
		 */
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

		/**
		 * Generate new entry from given sid and domain.
		 *
		 * @param sid
		 *            the sid
		 * @param domain
		 *            the domain
		 * @return the session map entry
		 */
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

		/**
		 * Generates the SID.
		 *
		 * @return the string representing sid
		 */
		private String generateSID() {

			char[] sid = new char[SID_LENGTH];

			for (int i = 0; i < SID_LENGTH; i++) {
				sid[i] = ALL_CHARACTERS.charAt(sessionRandom.nextInt(ALL_CHARACTERS.length()));
			}

			return String.valueOf(sid);
		}

		/**
		 * Class that processes request and sends response back to the client.
		 *
		 * @param urlPath
		 *            the url path
		 * @param directCall
		 *            flag that signals if method is called directly or from
		 *            some other class
		 * @throws Exception
		 *             if some kind of unexpected behavior happens
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall)
				throws Exception {

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

			if (!(Files.exists(requestedPath) && Files.isReadable(requestedPath) && Files.isRegularFile(requestedPath))) {
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

		/**
		 * Method used for finding worker that is passed in request from client.
		 *
		 * @param urlPath
		 *            the url path
		 * @return the instance of {@link IWebWorker}
		 */
		private IWebWorker findIWWFromPath(String urlPath) {
			Class<?> referenceToClass;
			Object newObject = null;
			try {
				String classPath = this.getClass().getPackage().getName() + ".workers." + urlPath.substring("/ext/".length());

				referenceToClass = this.getClass().getClassLoader().loadClass(classPath);
				newObject = referenceToClass.newInstance();
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException e) {

				e.printStackTrace();
				System.exit(0);
			}

			IWebWorker iww = (IWebWorker) newObject;
			return iww;
		}

		/**
		 * Method that initializes context if it is not already initialized.
		 */
		private void initializeContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}
		}

		/**
		 * Method used for finding extension from given path.
		 *
		 * @param requestedPath
		 *            the requested path
		 * @return the extension
		 */
		private String findExtension(Path requestedPath) {
			int i = requestedPath.toString().lastIndexOf('.');

			/*-if dot is found returns everything after it, otherwise returns empty string*/
			return (i > 0) ? requestedPath.toString().substring(i + 1) : " ";
		}

		/**
		 * Parses the parameters that are passed in clients request.
		 *
		 * @param paramString
		 *            the param string
		 */
		private void parseParameters(String paramString) {
			String[] paramArray = paramString.split("&");
			for (String param : paramArray) {
				String[] parameter = param.split("=");
				params.put(parameter[0], parameter[1]);
			}
		}

		/**
		 * Method that reads a http request header and returns all lines in a
		 * list.
		 *
		 * @return the list with all header lines
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
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

		/**
		 * This method reads all bytes from a request until it reaches a
		 * '\r\n\r\n'.
		 *
		 * @param is
		 *            the input stream to read the request from
		 * @return the byte[] array of all the read bytes
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
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

		/**
		 * Method that composes and sends an error response.
		 *
		 * @param statusCode
		 *            the status code of error
		 * @param statusText
		 *            the status text
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText)
				throws IOException {

			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n" + "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.webserver.IDispatcher#dispatchRequest(java.lang.
		 * String)
		 */
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}
}