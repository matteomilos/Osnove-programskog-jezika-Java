package hr.fer.zemris.java.hw03.prob1;

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		this.data = text.trim().toCharArray();
		setState(LexerState.BASIC);
	}

	public Token nextToken() {
		if (data == null) {
			throw new LexerException("Data is null.");
		}

		if (this.data.length == 0) {
			this.token = new Token(TokenType.EOF, null);
			data = null;
			return token;
		}

		while (data[currentIndex] == ' ') {
			currentIndex++;
		}

		switch (state) {
		case BASIC:
			return basicProcess();
		case EXTENDED:
			return extendedProcess();
		default:
			return null;
		}

	}

	private Token extendedProcess() {
		StringBuilder sb = new StringBuilder();
		Token token;
		if (data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;

		} else {

			for (int duljina = data.length; currentIndex < duljina && data[currentIndex] != ' '; currentIndex++) {
				if (data[currentIndex] == '#') {
					break;
				}
				sb.append(data[currentIndex]);
			}

			token = new Token(TokenType.WORD, sb.toString());
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	private Token basicProcess() {
		Token token;
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			token = new Token(TokenType.WORD, createWord());

		} else if (Character.isDigit(data[currentIndex])) {
			token = new Token(TokenType.NUMBER, createNumber());

		} else if (data[currentIndex] == '#') {
			setState(LexerState.EXTENDED);
			currentIndex++;
			token = new Token(TokenType.SYMBOL, '#');

		} else {
			char symbol = data[currentIndex++];
			token = new Token(TokenType.SYMBOL, symbol);
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	private long createNumber() {
		long number = 0;

		for (int duljina = data.length; currentIndex < duljina; currentIndex++) {

			if (Character.isDigit(data[currentIndex])) {
				number = number * 10 + Character.getNumericValue(data[currentIndex]);

				if (number < 0) {
					throw new LexerException("Number given is too big. Overflow happened.");
				}

			} else {
				break;
			}
		}

		return number;
	}

	private String createWord() {
		StringBuilder sb = new StringBuilder();

		for (int length = data.length; currentIndex < length; currentIndex++) {

			if (data[currentIndex] == '\\') {

				if (++currentIndex < length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\')) {
					sb.append(data[currentIndex]);
				} else {
					throw new LexerException("Wrong or no character after escape sign.");
				}

			} else if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex]);

			} else {
				break;
			}
		}

		return sb.toString();
	}

	public Token getToken() {
		return token;
	}

	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
	}

}
