package hr.fer.zemris.bf.lexer;

public class Token {

	TokenType tokenType;

	Object tokenValue;

	public Token(TokenType tokenType, Object tokenValue) {
		if (tokenType == null) {
			throw new LexerException("Type of the token mustn't be null!");
		}
		this.tokenType = tokenType;
		this.tokenValue = tokenValue;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public Object getTokenValue() {
		return tokenValue;
	}

	@Override
	public String toString() {

		String tokenClass = (tokenValue == null) ? "" : tokenValue.getClass().toString().substring(5);
		if (tokenValue == null) {
			return String.format("Type: %s, Value: %s", tokenType, tokenValue);
		}

		return String.format("Type: %s, Value: %s, Value is instance of:%s", tokenType, tokenValue, tokenClass);
	}

}
