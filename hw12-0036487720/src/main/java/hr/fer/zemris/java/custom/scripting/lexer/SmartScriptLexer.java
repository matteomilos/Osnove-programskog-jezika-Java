package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class represents object that lexically analyzes given document. It is being
 * analyzed in a way that its content is consumed character by character.
 * Consumed text is split into smaller lexical parts to generate tokens for
 * {@linkplain SmartScriptParser}. This lexer can work in two possible states:
 * TEXT and TAG state. While in the TEXT state, everything is treated as text
 * and is generated as one <code>String</code> token. TEXT state is default one,
 * and lexer is in that state all the way until it encounters "{$" tag which
 * means it enters TAG state. While in TAG state, lexer can generate these
 * tokens: FUNCTION, VARIABLE, STRING, INT_NUMBER, DOUBLE_NUMBER, OPERATOR, EOF,
 * TAG. More informations about all the token types are here
 * {@linkplain SmartScriptTokenType}.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptLexer {

	/**
	 * Array of characters that contains processed text.
	 */
	private char[] data;

	/**
	 * Last generated token by lexer.
	 */
	private SmartScriptToken token;

	/**
	 * Current position in processed text.
	 */
	private int currentIndex;

	/**
	 * Current working state of lexer.
	 */
	private SmartScriptLexerState state;

	/**
	 * Public constructor of this lexer, as argument it accepts text that will
	 * be analyzed.
	 * 
	 * @param documentBody
	 *            text that will be lexically analyzed
	 * @throws IllegalArgumentException
	 *             in case that given value is <code>null</code> value
	 */
	public SmartScriptLexer(String documentBody) {
		if (documentBody == null) {
			throw new IllegalArgumentException();
		}

		this.data = documentBody.trim().toCharArray();
		setState(SmartScriptLexerState.TEXT);
	}

	/**
	 * Public setter method that sets current working state of lexer.
	 * 
	 * @param state
	 *            state to be set
	 * @throws IllegalArgumentException
	 *             if given argument is <code>null</code> value
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Value given can not be null");
		}
		this.state = state;
	}

	/**
	 * Public factory method that gets next token depending on the current
	 * working state of this lexer.
	 * 
	 * @return next token from given text
	 */
	public SmartScriptToken nextToken() {
		if (data == null) {
			throw new SmartScriptLexerException("Data is null");
		}

		if (this.data.length == 0) {
			this.token = new SmartScriptToken(SmartScriptTokenType.EOF, new ElementString("EOF"));
			data = null;
			return token;
		}

		switch (state) {
		case TEXT:
			return textProcess();
		case TAG:
			while (data[currentIndex] == ' ') {
				currentIndex++;
			}
			return tagProcess();
		default:
			return null;
		}
	}

	/**
	 * Private factory method that generates next token in TAG state. Most of
	 * its work is delegated to other methods based on the first read character.
	 * Possible types of tokens are these: FUNCTION, VARIABLE, STRING,
	 * INT_NUMBER, DOUBLE_NUMBER, OPERATOR, EOF, TAG.
	 * 
	 * @return next token from given text
	 * @throws SmartScriptLexerException
	 *             if text contains invalid closing of the tag
	 */
	private SmartScriptToken tagProcess() {
		SmartScriptToken foundTag;
		char currentChar = data[currentIndex];

		if (currentChar == '$' && currentIndex + 1 < data.length) {

			if (data[currentIndex + 1] == '}') {
				foundTag = new SmartScriptToken(SmartScriptTokenType.TAG, new ElementString("TAG"));
				setState(SmartScriptLexerState.TEXT);
				currentIndex += 2; // preskoci tag
				data = new String(data).substring(currentIndex).toCharArray();
				currentIndex = 0;

				return foundTag;

			} else {
				throw new SmartScriptLexerException("Tag wasn't properly closed.");
			}

		} else if (currentChar == '=') {
			foundTag = new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("="));
			currentIndex++; /*- preskoci znak "=" */

		} else if (currentChar == '@') {
			foundTag = getFunction();

		} else if (currentChar == '\"') {
			foundTag = getString();

		} else if (Character.isLetter(currentChar)) {
			foundTag = getVariable();

		} else if (Character.isDigit(currentChar)) {
			foundTag = getNumber();

		} else {
			foundTag = getSymbol();
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;

		return foundTag;
	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#tagProcess()}. It consumes document
	 * characters and treats them like symbols. Allowed symbols are these: '+',
	 * '-', '*', '/' and '^'. If symbol is not one of those, proper exception
	 * will be thrown.
	 * 
	 * @return next token of type SYMBOL
	 * @throws SmartScriptLexerException
	 *             if symbol is invalid
	 */
	private SmartScriptToken getSymbol() {
		char symbol = data[currentIndex++];

		// @formatter:off
		if (symbol == '-' && 
			currentIndex < data.length && 
			Character.isDigit(data[currentIndex])) { /*- ako je prvi znak minus, i poslije njega slijedi broj, prebaci se u getNumber() */
			return getNumber();
			
		} else if (symbol == '-' ||  symbol == '+' ||   symbol == '*' || 
				   symbol == '/' ||  symbol == '^') {
			
			return new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator(String.valueOf(symbol)));

		} else {
			throw new SmartScriptLexerException("Symbol/operator is invalid.");
		}
		// @formatter:on
	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#tagProcess()}. It consumes document
	 * characters and treats them like numbers. Number can be either of type
	 * double or integer. If number is invalid (not double nor integer), proper
	 * exclamation will be thrown.
	 * 
	 * @return next token of type int or double
	 * @throws SmartScriptLexerException
	 *             if number is invalids
	 */
	private SmartScriptToken getNumber() {
		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] == '-') {
			sb.append(data[currentIndex++]);
		}

		boolean dotFlag = false;
		// @formatter:off
		while (currentIndex < data.length && 
				(Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
		// @formatter:on

			if (data[currentIndex] == '.') {
				if (dotFlag) {
					throw new SmartScriptLexerException(
							"Too many dots in your number. There can be only one dot in a number."
					);
				}
				dotFlag = true;
			}

			sb.append(data[currentIndex++]);
		}

		String number = sb.toString();
		SmartScriptToken token = null;

		if (number.contains(".")) {
			token = new SmartScriptToken(
					SmartScriptTokenType.DOUBLE_NUMBER, new ElementConstantDouble(Double.parseDouble(number))
			);
		} else {
			token = new SmartScriptToken(
					SmartScriptTokenType.INT_NUMBER, new ElementConstantInteger(Integer.parseInt(number))
			);
		}

		return token;
	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#tagProcess()}. It consumes document
	 * characters and treats them like variables. Variable must start with
	 * letter, and other characters can be letters, numbers and underscores.
	 * 
	 * @return next token representing variable
	 */
	private SmartScriptToken getVariable() {
		StringBuilder sb = new StringBuilder();
		char currentChar = data[currentIndex];

		while ((Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_')
				&& currentIndex < data.length) { /*- provjera valjanih znakova za varijablu */

			sb.append(data[currentIndex++]);
			currentChar = data[currentIndex];
		}

		return new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable(sb.toString()));

	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#tagProcess()}. It consumes document
	 * characters and treats them like Strings. String is anything written
	 * inside quotation marks. After closing of the quotation marks, method
	 * finishes. It is also possible to have escape sequences, but in case of
	 * invalid escape sequence, appropriate exception will be thrown
	 * 
	 * @return next token representing string
	 * @throws SmartScriptLexerException
	 *             in case of invalid escaping
	 */
	private SmartScriptToken getString() {
		StringBuilder sb = new StringBuilder();
		while (++currentIndex < data.length && data[currentIndex] != '"') {

			if (data[currentIndex] == '\\') {
				if (currentIndex + 1 < data.length) {
					char next = data[currentIndex + 1];
					if (next == 'r' || next == 't' || next == 'n') {
						sb.append(data[currentIndex++]);
						sb.append(data[currentIndex]);
					} else {
						checkAfterEscape(); /*- provjera dolazi li valjani znak nakon escape-a, baca se exception u metodi ukoliko ne dolazi */
						sb.append(data[++currentIndex]);
					}
				} else {
					throw new SmartScriptLexerException("Escape can't be the last character.");
				}
			} else {
				sb.append(data[currentIndex]);
			}
		}
		currentIndex++;

		return new SmartScriptToken(
				SmartScriptTokenType.STRING, new ElementString(sb.toString().replace("\\n", "\n").replace("\\r", "\r"))
		);
	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#tagProcess()}. It consumes document
	 * characters and treats them like functions. Name of the function must
	 * start with letter, and other characters can be letters, numbers and
	 * underscores.
	 * 
	 * @return next token representing function
	 * @throws SmartScriptLexerException
	 *             if first character is not letter
	 */
	private SmartScriptToken getFunction() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		char currentChar = data[++currentIndex];

		while (currentIndex < data.length
				&& (Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_')) {

			if (first && !Character.isLetter(data[currentIndex])) { /*- funkcija mora započinjati slovom */
				throw new SmartScriptLexerException("Function name is invalid.");

			} else {

				sb.append(currentChar);
				currentChar = data[++currentIndex];
				first = false;
			}
		}

		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(sb.toString()));
	}

	/**
	 * Private factory method that generates next token in TEXT working state of
	 * lexer. If next token is not of type TAG, method delegates its work to
	 * method {@link SmartScriptLexer#getText()}.
	 * 
	 * @return next generated token
	 * @throws SmartScriptLexerException
	 *             if tags weren't closed properly
	 */
	private SmartScriptToken textProcess() {
		SmartScriptToken token = null;

		if (data[currentIndex] == '{' && currentIndex + 1 < data.length) {

			if (data[currentIndex + 1] == '$') {
				token = new SmartScriptToken(SmartScriptTokenType.TAG, new ElementString("TAG"));
				setState(SmartScriptLexerState.TAG);
				currentIndex += 2; // preskoci tag
			} else {
				throw new SmartScriptLexerException("Tags weren't closed properly.");
			}

		} else {
			token = getText();
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	/**
	 * Private factory method that is called from method
	 * {@linkplain SmartScriptLexer#textProcess()}. It consumes document
	 * characters and groups them all in one object of type <code>String</code>.
	 * Method continues consuming characters until it reaches this '{'
	 * character, or reaches end of document. It also allows escape sequences,
	 * but throws exception in case of invalid escape sequence.
	 * 
	 * @return next token representing text
	 * @throws SmartScriptLexerException
	 *             in case of invalid escaping
	 */
	private SmartScriptToken getText() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length && data[currentIndex] != '{') {

			if (data[currentIndex] == '\\') {
				checkAfterEscape();
				sb.append(data[++currentIndex]);
				currentIndex++;

			} else {
				sb.append(data[currentIndex++]);
			}
		}
		return new SmartScriptToken(SmartScriptTokenType.TEXT, new ElementString(sb.toString()));
	}

	/**
	 * Private helper method that checks if the escape sequence in text is
	 * correct and throw appropriate exception if it is not.
	 * 
	 * @throws SmartScriptLexerException
	 *             if escape character is the last character of document
	 * @throws SmartScriptLexerException
	 *             if there is invalid escape sequence
	 */
	private void checkAfterEscape() {

		if (currentIndex + 1 >= data.length) {
			throw new SmartScriptLexerException("Text can't end with escape sign.");
		}

		char next = data[currentIndex + 1];

		switch (state) {
		case TEXT:
			if (next == '\\' || next == '{') {
				return;
			}
		case TAG:
			if (next == '\\' || next == '"') {
				return;
			}
		}
		throw new SmartScriptLexerException("Wrong character after escape sign.");
	}

	/**
	 * Public getter method that returns reference to the last generated token
	 * 
	 * @return last generated token
	 */
	public SmartScriptToken getToken() {
		return token;
	}
}
