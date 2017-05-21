package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class <code>SmartScriptParserException</code> represents exception that is
 * thrown in case of error in syntax analysis of text. It is derived from
 * {@linkplain RuntimeException}.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -6110800787591938252L;

	/**
	 * Default public constructor, delegates its work to superclass constructor.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Public constructor whose argument is message that explains thrown
	 * exception, delegates its work to superclass constructor.
	 * 
	 * @param message
	 *            message that explains exception
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

}
