package hr.fer.zemris.java.hw03.prob1;

/**
 * Class <code>Token</code> represents object that encapsulates content
 * processed by class {@link Lexer}
 * 
 * @author Matteo Miloš
 *
 */
public class Token {

	/**
	 * Enumeration that defines type of instance of the class
	 * <code>Token</code>.
	 */
	private TokenType type;
	/**
	 * Processed content of lexer, value of the token.
	 */
	private Object value;

	/**
	 * Public constructor that accepts two arguments, enumeration of type
	 * {@linkplain Token} and object {@linkplain Object} which represents value
	 * of Token
	 * 
	 * @param type
	 *            type of token
	 * @param value
	 *            value of the token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Public getter of token value
	 * 
	 * @return value of the token
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Public getter of token type
	 * 
	 * @return type of the token
	 */
	public TokenType getType() {
		return type;
	}

}
