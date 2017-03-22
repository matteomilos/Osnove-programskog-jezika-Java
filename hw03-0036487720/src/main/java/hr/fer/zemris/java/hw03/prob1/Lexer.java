package hr.fer.zemris.java.hw03.prob1;

/**
 * Class <code>Lexer</code> represents subsystem for lexical analysis. Input of
 * this subsystem is source code of the program (or some document), and output
 * is array of tokens. {@link Token} is lexical unit that groups one or more
 * chars from input. <code>Lexer</code> is lexical analyzer of type "lazy",
 * which means that it performs extraction of each token only after it is
 * explicitly demanded by calling appropriate method. Lexer can work in more
 * states, and current state is determined by instance variable
 * <code>state</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class Lexer {
	/**
	 * Array of chars that represents input which <code>Lexer</code> consumes
	 */
	private char[] data;
	/**
	 * Last token that was extracted by <code>Lexer</code>
	 */
	private Token token;
	/**
	 * Current position of the input text.
	 */
	private int currentIndex;
	/**
	 * Current state of <code>Lexer</code>
	 */
	private LexerState state;
	/**
	 * Character that signals change of the state of the lexer.
	 */
	private static final char STATE_CHANGER = '#';

	/**
	 * Public constructor of class <code>Lexer</code>. Argument given is string
	 * that represents input text.
	 * 
	 * @param text
	 *            text that will be lexically analyzed
	 * @throws IllegalArgumentException
	 *             if argument given is null value
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException();
		}
		this.data = text.trim().toCharArray();
		setState(LexerState.BASIC);
	}

	/**
	 * Public factory method that extracts token from input texts. It delegates
	 * its work to appropriate methods. Returned value is reference to an object
	 * of type <code>Token</code>.
	 * 
	 * @return reference to the next token
	 * @throws LexerException
	 *             if method is called after token that represents end of
	 *             process (EOF)
	 */
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

	/**
	 * Private factory method that generates tokens from input text when lexer
	 * is in EXTENDED state. Method generates tokens that can be of type WORD.
	 * Tokens of type WORD represents words compound of letters and possible
	 * escape sequences. Tokens of type NUMBER represent numbers that can be
	 * written in format <code>long</code>. Tokens of type SYMBOL represent
	 * symbols, which are all characters except letters, numbers and spaces. If
	 * character <code>STATE_CHANGER</code> is consumed, lexer generates token
	 * of type SYMBOL and shifts to BASIC state.
	 * 
	 * @return reference to the next token
	 */
	private Token extendedProcess() {
		StringBuilder sb = new StringBuilder();
		Token token;

		if (data[currentIndex] == STATE_CHANGER) {
			token = new Token(TokenType.SYMBOL, STATE_CHANGER);
			currentIndex++;

		} else {

			for (int duljina = data.length; currentIndex < duljina && data[currentIndex] != ' '; currentIndex++) {
				if (data[currentIndex] == STATE_CHANGER) {
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

	/**
	 * Private factory method that generates tokens from input text when lexer
	 * is in BASIC state. Method generates tokens that can be of type NUMBER,
	 * SYMBOL and WORD. Tokens of type WORD represents words compound of letters
	 * and possible escape sequences. Tokens of type NUMBER represent numbers
	 * that can be written in format <code>long</code>. Tokens of type SYMBOL
	 * represent symbols, which are all characters except letters, numbers and
	 * spaces. If character <code>STATE_CHANGER</code> is consumed, lexer
	 * generates token of type SYMBOL and shifts to EXTENDED state.
	 * 
	 * @return reference to the next token
	 */
	private Token basicProcess() {
		Token token;

		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			token = new Token(TokenType.WORD, createWord());

		} else if (Character.isDigit(data[currentIndex])) {
			token = new Token(TokenType.NUMBER, createLongNumber());

		} else if (data[currentIndex] == STATE_CHANGER) {
			setState(LexerState.EXTENDED);
			currentIndex++;
			token = new Token(TokenType.SYMBOL, STATE_CHANGER);

		} else {
			char symbol = data[currentIndex++];
			token = new Token(TokenType.SYMBOL, symbol);
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	/**
	 * Private method that generates number of type long. It is called by
	 * {@link #extendedProcess()} or {@link #basicProcess()} when lexer
	 * recognizes that next token will be of type NUMBER. If number in input is
	 * out of boundaries of type <code>long</code>, appropriate exception will
	 * be thrown.
	 * 
	 * @return number of type long
	 * @throws LexerException
	 *             if number can't be parsed as long beacuse of some reason
	 */
	private long createLongNumber() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}

		long number;
		try {
			number = Long.parseLong(sb.toString());
		} catch (NumberFormatException exception) {
			throw new LexerException("Number can not be parsed as long");
		}
		return number;

	}

	/**
	 * Private method that generates number of type long. It is called by
	 * {@link #extendedProcess()} or {@link #basicProcess()} when lexer
	 * recognizes that next token will be of type WORD. If word contains invalid
	 * escape sequence, appropriate exception will be thrown.
	 * 
	 * @return String representing the word
	 * @throws LexerException
	 *             if there is invalid escape sequence in the input
	 */
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

	/**
	 * Method that gets last generated token.
	 * 
	 * @return Last generated token.
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Changes the state of lexer.
	 * 
	 * @param state
	 *            New state to be set.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
	}

}
