package hr.fer.zemris.java.webserver;

/**
 * Simple functional interface, implemented by private class ClientWorker from
 * {@link SmartHttpServer}. Used to determining if dispatch is requested
 * directly or not.
 * 
 * @author Matteo Miloš
 *
 */
public interface IDispatcher {

	/**
	 * Method used for dispatching request.
	 * 
	 * @param urlPath
	 *            url path of the request
	 * @throws Exception
	 *             if some type of unexpected behavior happens.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
