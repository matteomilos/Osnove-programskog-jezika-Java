package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class <code>SmartScriptLexerException</code> represents exception that is
 * thrown in case of error in lexical analysis of text. It is derived from
 * {@linkplain RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 5936656131853783560L;

	/**
	 * Default public constructor, delegates its work to superclass constructor.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Public constructor whose argument is message that explains thrown
	 * exception, delegates its work to superclass constructor.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}
