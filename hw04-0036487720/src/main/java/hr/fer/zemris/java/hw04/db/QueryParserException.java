package hr.fer.zemris.java.hw04.db;

/**
 * Class <code>QueryParserException</code> represents exception that is thrown
 * in case of error during parsing of query. It is derived from
 * {@linkplain RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class QueryParserException extends RuntimeException {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -3667191983754123032L;

	/**
	 * Default public constructor, delegates its work to superclass constructor.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Public constructor whose argument is message that explains thrown
	 * exception, delegates its work to superclass constructor.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public QueryParserException(String message) {
		super(message);
	}
}
