package hr.fer.zemris.java.hw03.prob1;

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;

	public static void main(String[] args) {
		Lexer lexer = new Lexer("   \1sst safsafsa");
		// for(char znak : lexer.data){
		// System.out.println(znak);
		// }
		System.out.println(lexer.nextToken().value.toString());
		System.out.println(lexer.nextToken().value.toString());

		// String text = "\4";
		// char[] znak = text.toCharArray();
		// System.out.println(znak[0]);

	}

	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		this.data = text.trim().toCharArray();
		setState(LexerState.BASIC);
		// TODO: konstruktor koji prima ulazni tekst koji se tokenizira
	}

	public Token nextToken() {
		if (data == null) {
			throw new LexerException();
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
		String word = "";
		if (data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
		} else {
			for (int duljina = data.length; currentIndex < duljina && data[currentIndex] != ' '; currentIndex++) {
				if (data[currentIndex] == '#') {
					break;
				}
				word += data[currentIndex];
			}
			token = new Token(TokenType.WORD, word);
		}
		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	private Token basicProcess() {
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			token = new Token(TokenType.WORD, createWord());

		} else if (Character.isDigit(data[currentIndex])) {
			token = new Token(TokenType.NUMBER, createNumber());
		} else if (data[currentIndex] == '#') {
			setState(LexerState.EXTENDED);
			currentIndex++;
			token = new Token(TokenType.SYMBOL, '#');
		} else {
			char simbol = data[currentIndex++];
			token = new Token(TokenType.SYMBOL, simbol);
		}
		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	private long createNumber() {
		long broj = 0;
		for (int duljina = data.length; currentIndex < duljina; currentIndex++) {
			if (Character.isDigit(data[currentIndex])) {
				broj = broj * 10 + Character.getNumericValue(data[currentIndex]);
				if (broj < 0) {
					throw new LexerException();
				}
			} else {
				break;
			}
		}

		return broj;
	}

	private String createWord() {
		String word = "";
		for (int duljina = data.length; currentIndex < duljina; currentIndex++) {
			if (data[currentIndex] == '\\') {
				if (++currentIndex < duljina && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\')) {
					word += data[currentIndex];
				} else {
					throw new LexerException();
				}
			} else if (Character.isLetter(data[currentIndex])) {
				word += data[currentIndex];
			} else {
				break;
			}
		}

		return word;
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