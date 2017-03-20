package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
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
		SmartScriptLexer lexer = new SmartScriptLexer(
				"This is sample text.  {$ FOR i 1 10 1 $} This is {$= i $}-th time this message is generated. {$END$} {$FOR i 0 10 2 $}sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $} {$END$}");
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());




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
		while (data[currentIndex] == ' ') {
			currentIndex++;
		}
		switch (state) {
		case TEXT:
			return textProcess();
		case TAG:
			return tagProcess();
		default:
			return null;
		}
	}

	private SmartScriptToken tagProcess() {
		SmartScriptToken vrato;
		if (data[currentIndex] == '$' && currentIndex + 1 < data.length) {
			if (data[currentIndex + 1] == '}') {
				vrato = new SmartScriptToken(SmartScriptTokenType.TAG, new ElementString("TAG"));
				setState(SmartScriptLexerState.TEXT);
				currentIndex += 2; // preskoci tag
			} else {
				throw new SmartScriptLexerException("Krivo zatvorene zagrade");
			}

		} else if (data[currentIndex] == '=') {
			vrato = new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("="));
			currentIndex++;
		} else if (data[currentIndex] == '@') {
			vrato = getFunction();
		} else if (data[currentIndex] == '\"') {
			vrato = getString();
		} else if (Character.isLetter(data[currentIndex])) {
			vrato = getVariable();
		} else if (Character.isDigit(data[currentIndex])) {
			vrato = getNumber();
		} else {
			vrato = getSymbol();
		}

		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;

		return vrato;
	}

	private SmartScriptToken getSymbol() {
		char symbol = data[currentIndex++];

		if (symbol == '-' && currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			return getNumber();
		} else if (symbol == '-' || symbol == '+' || symbol == '*' || symbol == '/' || symbol == '^') {
			return new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator(String.valueOf(symbol)));
		} else {
			throw new SmartScriptLexerException("Symbol (operator) is invalid.");
		}
	}

	private SmartScriptToken getNumber() {
		String number = "";
		if (data[currentIndex] == '-') {
			number += data[currentIndex++];
		}

		boolean dotFlag = false;
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			if (data[currentIndex] == '.') {
				if (dotFlag) {
					throw new SmartScriptLexerException("Too many dots.");
				}
				dotFlag = true;
			}
			number += data[currentIndex++];
		}
		SmartScriptToken token = null;
		if (number.contains(".")) {
			token = new SmartScriptToken(SmartScriptTokenType.DOUBLE_NUMBER,
					new ElementConstantDouble(Double.parseDouble(number)));
		} else {
			token = new SmartScriptToken(SmartScriptTokenType.INT_NUMBER,
					new ElementConstantDouble(Integer.parseInt(number)));
		}

		return token;
	}

	private SmartScriptToken getVariable() {
		String variable = "";
		while ((Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') && currentIndex < data.length) {
			variable += data[currentIndex++];
		}

		return new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable(variable));

	}

	private SmartScriptToken getString() {
		String string = "";
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				checkAfterEscape();

			}
			string += data[currentIndex++];
		}
		currentIndex++;
		return new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(string));
	}

	private SmartScriptToken getFunction() {
		String function = "";
		currentIndex++;
		int i = 0;
		while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {

			if (i == 0 && !Character.isLetter(data[currentIndex])) {
				throw new SmartScriptLexerException("Name is invalid");
			} else {
				function += data[currentIndex++];
				i++;
			}
		}

		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(function));
	}

	public SmartScriptToken textProcess() {
		String word = "";
		SmartScriptToken token = null;
		if (data[currentIndex] == '{' && currentIndex + 1 < data.length) {
			if (data[currentIndex + 1] == '$') {
				token = new SmartScriptToken(SmartScriptTokenType.TAG, new ElementString("TAG"));
				setState(SmartScriptLexerState.TAG);
				currentIndex += 2; // preskoci tag
			} else {
				throw new SmartScriptLexerException("Krivo otvorene zagrade");
			}
		} else {
			token = getText();
		}
		data = new String(data).substring(currentIndex).trim().toCharArray();
		currentIndex = 0;
		return token;
	}

	public SmartScriptToken getText() {
		String text = "";
		while (currentIndex < data.length && data[currentIndex] != '{') {
			if (data[currentIndex] == '\\') {
				checkAfterEscape();
				text += data[currentIndex++];
			} else {
				text += data[currentIndex++];
			}
		}

		return new SmartScriptToken(SmartScriptTokenType.TEXT, new ElementString(text));
	}

	private void checkAfterEscape() {
		if (currentIndex + 1 >= data.length) {
			throw new SmartScriptLexerException();
		}
		char next = data[currentIndex + 1];
		switch (state) {
		case TEXT:
			if (next == '\\' || next == '{' || next == 'r' || next == 'n' || next == 't') {
				return;
			}
		case TAG:
			if (next == '\\' || next == '"' || next == 'r' || next == 'n' || next == 't') {
				return;
			}
		}
		throw new SmartScriptLexerException("Krivi znak poslije escapea");
	}

	public SmartScriptToken getToken() {

		return null;
	}
}
