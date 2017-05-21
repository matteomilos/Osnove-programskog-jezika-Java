package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {

	private String address;

	private int port;

	private int workerThreads;

	private int sessionTimeout;

	private Map<String, String> mimeTypes = new HashMap<String, String>();

	private ServerThread serverThread;

	private ExecutorService threadPool;

	private Path documentRoot;

	public SmartHttpServer(String configFileName) {
		// … do stuff here …
	}

	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			serverThread.start();
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		// … start server thread if not already running …
		// … init threadpool by Executors.newFixedThreadPool(...); …
	}

	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
		// … signal server thread to stop running …
		// … shutdown threadpool …
	}

	protected class ServerThread extends Thread {

		@Override
		public void run() {
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// given in pesudo-code:
			// open serverSocket on specified port
			// while(true) {
			// Socket client = serverSocket.accept();
			// ClientWorker cw = new ClientWorker(client);
			// submit cw to threadpool for execution
			// }
		}
	}

	private class ClientWorker implements Runnable {

		private Socket csocket;

		private PushbackInputStream istream;

		private OutputStream ostream;

		private String version;

		private String method;

		private Map<String, String> params = new HashMap<String, String>();

		private Map<String, String> permPrams = null;

		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		private String SID;

		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
		}
	}
}