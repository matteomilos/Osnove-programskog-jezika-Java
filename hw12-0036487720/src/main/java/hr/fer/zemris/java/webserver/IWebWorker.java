package hr.fer.zemris.java.webserver;

/**
 * Interface implemented by classes from
 * {@link hr.fer.zemris.java.webserver.workers workers} package. Used for
 * processing request of the client.
 * 
 * @author Matteo Miloš
 *
 */
public interface IWebWorker {

	/**
	 * Method for processing request from client and returning it to him
	 * (browser).
	 * 
	 * @param context
	 *            context used for processing the request
	 */
	public void processRequest(RequestContext context);

}
