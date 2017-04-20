package hr.fer.zemris.bf.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

	private String expression;

	private Token token;

	public Lexer(String expression) {
		if (expression == null) {
			throw new LexerException("Expression can't be null.");
		}
		this.expression = expression.trim();
	}

	public Token nextToken() {

		if (expression == null) {
			throw new LexerException("Expression is null");
		}

		if (expression.length() == 0) {
			this.token = new Token(TokenType.EOF, null);
			expression = null;
			return token;
		}

		expression = expression.trim();

		switch (determineValue(expression)) {
		case "identificator":
			token = processIdentificator();
			break;
		case "numeric":
			token = processNumeric();
			break;
		case "bracket":
			token = processBracket();
			break;
		case "operator":
			token = processOperator();
			break;
		default:
			throw new LexerException("Unsupported type given.");
		}

		return token;
	}

	private Token processNumeric() {

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(expression);
		String nextToken = null;
		if (matcher.find()) {
			nextToken = matcher.group(0);
			expression = expression.substring(nextToken.length());
		}

		if (nextToken.equals("0") || nextToken.equals("1")) {
			return new Token(TokenType.CONSTANT, nextToken.equals("1"));
		}

		throw new LexerException("Unexpected number: " + nextToken + ".");
	}

	private Token processBracket() {
		Character bracket = expression.charAt(0);
		expression = expression.substring(1);
		if (bracket == '(') {
			return new Token(TokenType.OPEN_BRACKET, bracket);
		}

		return new Token(TokenType.CLOSED_BRACKET, bracket);
	}

	private Token processOperator() {

		Object tokenValue = null;
		switch (expression.charAt(0)) {
		case '*':
			tokenValue = "and";
			expression = expression.substring(1);
			break;
		case '+':
			tokenValue = "or";
			expression = expression.substring(1);
			break;
		case '!':
			tokenValue = "not";
			expression = expression.substring(1);
			break;
		default:
			tokenValue = "xor";
			expression = expression.substring(3);
		}

		return new Token(TokenType.OPERATOR, tokenValue);
	}

	private Token processIdentificator() {

		Pattern pattern = Pattern.compile("[a-zA-Z_]*");
		Matcher matcher = pattern.matcher(expression);
		String nextToken = null;
		if (matcher.find()) {
			nextToken = matcher.group(0);
			expression = expression.substring(nextToken.length());
		}
		if (nextToken.matches("^(?i)(and|or|xor|not)$")) {
			return new Token(TokenType.OPERATOR, nextToken.toLowerCase());
		}

		if (nextToken.matches("^(?i)(true|false)$")) {
			return new Token(TokenType.CONSTANT, nextToken.equalsIgnoreCase("true"));
		}

		return new Token(TokenType.VARIABLE, nextToken.toUpperCase());
	}

	private String determineValue(String expression) {

		char firstCharacter = expression.charAt(0);

		if (Character.isLetter(firstCharacter)) {
			return "identificator";
		}

		if (Character.isDigit(firstCharacter)) {
			return "numeric";
		}

		if (firstCharacter == ')' || firstCharacter == '(') {
			return "bracket";
		}

		if (firstCharacter == '+' || firstCharacter == '*' || firstCharacter == '!' || expression.startsWith(":+:")) {
			return "operator";
		}

		return "unsupported";
	}

}
