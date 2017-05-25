package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class RequestContext {

	private OutputStream outputStream;

	private Charset charset;

	public String encoding = "UTF-8";

	public int statusCode; // constructor stavi na 200

	public String statusText; // constructor "OK"

	public String mimeType; // "text/html"

	private Map<String, String> parameters;

	private Map<String, String> temporaryParameters;

	private Map<String, String> persistentParameters;

	private List<RCCookie> outputCookies;

	private boolean headerGenerated; // constructor false

	private IDispatcher dispatcher;

	public RequestContext(
			OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters,
			List<RCCookie> outputCookies, Map<String, String> temporaryParameters, IDispatcher dispatcher
	) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	public RequestContext(
			OutputStream outputStream, Map<String, String> parameters, Map<String, String> persistentParameters,
			List<RCCookie> outputCookies
	) {

		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream mustn't be null");
		}

		this.outputStream = outputStream;
		this.parameters = (parameters == null) ? new HashMap<>() : parameters;
		this.persistentParameters = (persistentParameters == null) ? new HashMap<>() : persistentParameters;
		this.outputCookies = (outputCookies == null) ? new ArrayList<>() : outputCookies;
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.headerGenerated = false;
	}

	public void addRCCookie(RCCookie rcCookie) {
		outputCookies.add(rcCookie);
	}

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

	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			headerGenerated = true;
			charset = Charset.forName(encoding);
			outputStream.write(createHeader().getBytes(charset));
		}
		outputStream.write(text.getBytes(charset));
		outputStream.flush();

		return this;
	}

	private String createHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append(
				"Content-Type: " + mimeType + (mimeType.startsWith("text/") ? ("; charset=" + encoding) : "") + "\r\n"
		);
		for (RCCookie rcCookie : outputCookies) {
			sb.append("Set-Cookie: " + rcCookie.name + "=\"" + rcCookie.value + "\"");
			sb.append(rcCookie.domain == null ? "" : "; Domain=" + rcCookie.domain);
			sb.append(rcCookie.path == null ? "" : "; Path=" + rcCookie.path);
			sb.append(rcCookie.maxAge == null ? "" : "; Max-Age=" + rcCookie.maxAge);
			sb.append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}

	public String getParameter(String name) {
		return parameters.get(name);
	}

	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	public void setPersistentParameter(String name, String value) {
		if (persistentParameters == null) {
			persistentParameters = new HashMap<>();
		}
		persistentParameters.put(name, value);
	}

	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			temporaryParameters = new HashMap<>();
		}
		temporaryParameters.put(name, value);
	}

	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	public static class RCCookie {

		private String name;

		private String value;

		private String domain;

		private String path;

		private Integer maxAge;

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public Integer getMaxAge() {
			return maxAge;
		}

	}

}
