package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class used for encapsulation the information about HTTP request from user
 * (browser). It contains all the information about request.
 * 
 * @author Matteo Miloš
 *
 */
public class RequestContext {

	/** The output stream. */
	private OutputStream outputStream;

	/** The charset. */
	private Charset charset;

	/** The encoding. */
	private String encoding = "UTF-8";

	/** The status code. */
	private int statusCode;

	/** The status text. */
	private String statusText;

	/** The mime type. */
	private String mimeType;

	/** The parameters. */
	private Map<String, String> parameters;

	/** The temporary parameters. */
	private Map<String, String> temporaryParameters;

	/** The persistent parameters. */
	private Map<String, String> persistentParameters;

	/** The output cookies. */
	private List<RCCookie> outputCookies;

	/** The header generated. */
	private boolean headerGenerated;

	/** The dispatcher. */
	private IDispatcher dispatcher;

	/**
	 * Instantiates a new request context from passed parameters.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param parameters
	 *            the parameters
	 * @param persistentParameters
	 *            the persistent parameters
	 * @param outputCookies
	 *            the output cookies
	 * @param temporaryParameters
	 *            the temporary parameters
	 * @param dispatcher
	 *            the dispatcher
	 */
	public RequestContext(
			OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher
	) {

		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = (temporaryParameters == null)	? new HashMap<>()
																	: temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Instantiates a new request context from passed parameters.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param parameters
	 *            the parameters
	 * @param persistentParameters
	 *            the persistent parameters
	 * @param outputCookies
	 *            the output cookies
	 */
	public RequestContext(
			OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies
	) {

		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream mustn't be null");
		}

		this.outputStream = outputStream;
		this.parameters = (parameters == null) ? new HashMap<>() : parameters;
		this.persistentParameters = (persistentParameters == null)	? new HashMap<>()
																	: persistentParameters;
		this.outputCookies = (outputCookies == null)	? new ArrayList<>()
														: outputCookies;
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.headerGenerated = false;
	}

	/**
	 * Adds the RC cookie to the {@link #outputCookies}.
	 *
	 * @param rcCookie
	 *            the cookie added
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated!");
		}
		outputCookies.add(rcCookie);
	}

	/**
	 * Writes the byte array to the contexts output stream.
	 *
	 * @param data
	 *            byte array
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			headerGenerated = true;
			charset = Charset.forName(encoding);
			outputStream.write(createHeader().getBytes(charset));
		}

		outputStream.write(data);
		outputStream.flush();

		return this;
	}

	/**
	 * Writes the string to the contexts output stream. Delegates its work to
	 * {@link #write(byte[])} method.
	 *
	 * @param text
	 *            text to be written
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		return write(text.getBytes(Charset.forName(encoding)));
	}

	/**
	 * Method that creates header.
	 *
	 * @return the string
	 */
	private String createHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n").append(
				"Content-Type: " + mimeType + (mimeType.startsWith("text/")	? ("; charset=" + encoding)
																			: "") + "\r\n"
		);

		for (RCCookie rcCookie : outputCookies) {
			sb.append("Set-Cookie: " + rcCookie.name + "=\"" + rcCookie.value + "\"").append(
					rcCookie.domain == null ? "" : "; Domain=" + rcCookie.domain
			).append(
					rcCookie.path == null ? "" : "; Path=" + rcCookie.path
			).append(
					rcCookie.maxAge == null	? ""
											: "; Max-Age=" + rcCookie.maxAge
			).append("\r\n");
		}

		sb.append("\r\n");
		return sb.toString();
	}

	/**
	 * Gets the parameter from {@link #parameters}.
	 *
	 * @param name
	 *            name of the parameter
	 * @return value of the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Gets the parameter names from {@link #parameters}.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Gets the persistent parameter from {@link #persistentParameters}.
	 *
	 * @param name
	 *            the name of the parameter
	 * @return the value of persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Gets the persistent parameter names from {@link #persistentParameters}.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Puts new parameter in the {@link #persistentParameters} map.
	 *
	 * @param name
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 */
	public void setPersistentParameter(String name, String value) {
		if (persistentParameters == null) {
			persistentParameters = new HashMap<>();
		}
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter from {@link #persistentParameters}.
	 *
	 * @param name
	 *            the name of the parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Gets the temporary parameter from {@link #temporaryParameters} map.
	 *
	 * @param name
	 *            the name of the parameter
	 * @return the temporary parameter value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Gets the temporary parameter names from {@link #temporaryParameters}.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Puts new parameter in {@link #temporaryParameters} map.
	 *
	 * @param name
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 */
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			temporaryParameters = new HashMap<>();
		}
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the temporary parameter from {@link #temporaryParameters}.
	 *
	 * @param name
	 *            the name of the parameter
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Gets the temporary parameters from {@link #temporaryParameters}.
	 *
	 * @return the temporary parameters
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Sets the temporary parameters to the specified map.
	 *
	 * @param temporaryParameters
	 *            the temporary parameters map
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Gets the persistent parameters {@link #persistentParameters}.
	 *
	 * @return the persistent parameters
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets the persistent parameters to the specified map.
	 *
	 * @param persistentParameters
	 *            the persistent parameters
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	/**
	 * Gets the parameters from {@link #parameters}.
	 *
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Sets the encoding to the given value.
	 *
	 * @param encoding
	 *            the new encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated!");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the new status code
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated!");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText
	 *            the new status text
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated!");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *            the new mime type
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Header already generated!");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Gets the dispatcher.
	 *
	 * @return the dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * The inner class RCCookie is used as an encapsulation for cookies of the
	 * request context.
	 */
	public static class RCCookie {

		/** The name of the cookie. */
		private String name;

		/** The value of the cookie. */
		private String value;

		/** The domain of the cookie. */
		private String domain;

		/** The path of the cookie. */
		private String path;

		/** The max age of the cookie. */
		private Integer maxAge;

		/** Flag that signals if cookie is httpOnly. */
		private boolean isHttpOnly;

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name
		 *            the name of the cookie
		 * @param value
		 *            the value of the cookie
		 * @param maxAge
		 *            the max age of the cookie
		 * @param domain
		 *            the domain of the cookie
		 * @param path
		 *            the path of the cookie
		 * @param isHttpOnly
		 *            flag if cookie is httpOnly
		 */
		public RCCookie(
				String name, String value, Integer maxAge, String domain,
				String path, boolean isHttpOnly
		) {
			if (name == null || value == null) {
				throw new IllegalArgumentException("Name and value of the cookie can't be null");
			}
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.isHttpOnly = isHttpOnly;
		}

		/**
		 * Gets the name of the cookie.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value of the cookie.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain of the cookie.
		 *
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path of the cookie.
		 *
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age of the cookie.
		 *
		 * @return the max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Checks if cookie is httpOnly.
		 * 
		 * @return <code>true</code> if cookie is httpOnly, false otherwise
		 */
		public boolean isHttpOnly() {
			return isHttpOnly;
		}

	}

}
