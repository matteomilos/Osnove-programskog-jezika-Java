package hr.fer.zemris.java.hw03.prob1;

public class Token {

	public TokenType type;
	public Object value;

	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public TokenType getType() {
		return type;
	}

}
