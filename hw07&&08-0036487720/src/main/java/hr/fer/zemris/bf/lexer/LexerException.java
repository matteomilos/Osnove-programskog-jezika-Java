package hr.fer.zemris.bf.lexer;

/**
 * Class <code>LexerException</code> represents exception that is thrown in case
 * of error in lexical analysis of text. It is derived from
 * {@linkplain RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class LexerException extends RuntimeException {

	/** Generated serialVersionUID */
	private static final long serialVersionUID = -1793002611864185529L;

	/**
	 * Default public constructor, delegates its work to superclass constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Public constructor whose argument is message that explains thrown
	 * exception, delegates its work to superclass constructor.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public LexerException(String message) {
		super(message);
	}

}
