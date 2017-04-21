package hr.fer.zemris.bf.parser;

/**
 * Class <code>ParserException</code> represents exception that is thrown in
 * case of error in syntax analysis of text. It is derived from
 * {@linkplain RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class ParserException extends RuntimeException {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = -921539956905845634L;

	/**
	 * Default public constructor, delegates its work to superclass constructor.
	 */
	public ParserException() {
		super();
	}

	/**
	 * Public constructor whose argument is message that explains thrown
	 * exception, delegates its work to superclass constructor.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public ParserException(String message) {
		super(message);
	}

}
