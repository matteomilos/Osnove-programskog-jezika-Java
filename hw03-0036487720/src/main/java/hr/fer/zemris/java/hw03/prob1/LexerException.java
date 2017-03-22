package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception that is thrown by class {@link Lexer} in case that lexical analysis
 * results in some error. Exception extends {@link RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 8932635265505043745L;

	/**
	 * Public constructor that accepts message which explains why exception
	 * happened.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Default public constructor that doesn't get arguments.
	 */
	public LexerException() {
		super();
	}

}
