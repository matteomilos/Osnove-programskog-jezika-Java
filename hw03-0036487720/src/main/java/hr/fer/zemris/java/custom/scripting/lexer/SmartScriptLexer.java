package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class SmartScriptLexer {
	private char[] data;
	private SmartScriptToken token;
	private int currentIndex;
	private SmartScriptLexerState state;

	public static void main(String[] args) {
	}

	public SmartScriptLexer(String documentBody) {
		if (documentBody == null) {
			throw new IllegalArgumentException();
		}

		this.data = documentBody.trim().toCharArray();
		setState(SmartScriptLexerState.TEXT);
	}

	public void setState(SmartScriptLexerState state) {
		this.state = state;
	}

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
				throw new SmartScriptLexerException("Tag wasn't properly closed");
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
							"Too many dots in your number. There can be only one dot in a number.");
				}
				dotFlag = true;
			}

			sb.append(data[currentIndex++]);
		}

		String number = sb.toString();
		SmartScriptToken token = null;

		if (number.contains(".")) {
			token = new SmartScriptToken(SmartScriptTokenType.DOUBLE_NUMBER,
					new ElementConstantDouble(Double.parseDouble(number)));
		} else {
			token = new SmartScriptToken(SmartScriptTokenType.INT_NUMBER,
					new ElementConstantInteger(Integer.parseInt(number)));
		}

		return token;
	}

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
				}
				else{
					throw new SmartScriptLexerException("Escape can't be the last character.");
				}
			} else {
				sb.append(data[currentIndex]);
			}
		}
		currentIndex++;
		return new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(sb.toString()));
	}

	private SmartScriptToken getFunction() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		char currentChar = data[++currentIndex];

		while (currentIndex < data.length
				&& (Character.isLetter(currentChar) || Character.isDigit(currentChar) || currentChar == '_')) {

			if (first && !Character.isLetter(data[currentIndex])) { /*- funkcija mora započinjati slovom */
				throw new SmartScriptLexerException("Name is invalid.");

			} else {

				sb.append(currentChar);
				currentChar = data[++currentIndex];
				first = false;
			}
		}

		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(sb.toString()));
	}

	public SmartScriptToken textProcess() {
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

	public SmartScriptToken getText() {
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

	public SmartScriptToken getToken() {

		return null;
	}
}
