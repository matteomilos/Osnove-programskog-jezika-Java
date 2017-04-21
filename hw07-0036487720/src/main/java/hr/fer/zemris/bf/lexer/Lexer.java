package hr.fer.zemris.bf.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.bf.parser.Parser;

/**
 * Class represents object that lexically analyzes given expression. It is being
 * analyzed in a way that it by reading the first character of each token
 * determines in which mode it will work. Those modes are implemented in
 * methods: {@link Lexer#processBracket()}, {@link Lexer#processOperator()},
 * {@link Lexer#processIdentifier()}, {@link Lexer#processNumeric()} Consumed
 * text is split into smaller lexical parts to generate tokens for
 * {@linkplain Parser}. Lexer can generate 6 types of tokens, which are: EOF,
 * VARIABLE, CONSTANT, OPERATOR, OPEN_BRACKET, CLOSED_BRACKET. More informations
 * about all the token types are here {@linkplain TokenType}.
 * 
 * @author Matteo Miloš
 *
 */
public class Lexer {

	/** Expression that will be analyzed in this lexer */
	private String expression;

	/** Current token that is found */
	private Token token;

	/**
	 * Constructor for <code>Lexer</code> class, it checks if given expression
	 * is null, and if it is not, then it trims given expression and sets
	 * instance variable {@link Lexer#expression} to given expression.
	 * 
	 * @param expression
	 *            text that will be lexically analyzed
	 * 
	 * @throws LexerException
	 *             in case that given value is <code>null</code> value
	 * 
	 */
	public Lexer(String expression) {
		if (expression == null) {
			throw new LexerException("Expression can't be null.");
		}

		this.expression = expression.trim();
	}

	/**
	 * Public factory method that gets next token from expression.
	 * 
	 * @return next token from given text
	 * 
	 * @throws LexerException
	 *             if expression is null
	 */
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

		switch (determineMode(expression)) {

		case "identificator":
			token = processIdentifier();
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

	/**
	 * Method that is used for finding next Token if lexer has found numerical
	 * array in the given expression. Value of the token that is processed by
	 * this method can only be "1" or "0". If method has found different token
	 * it will throw appropriate exception.
	 * 
	 * @return next token of numeric type
	 * 
	 * @throws LexerException
	 *             if value of the found token is different from "1" or "0"
	 */
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

	/**
	 * Method that is used for finding next Token if lexer has found opening or
	 * closing bracket in the given expression. Value of the token that is
	 * processed by this method can only be "(" or ")". It is previously ensured
	 * that found token will not have value different from these two.
	 * 
	 * @return next token, opening or closing bracket
	 * 
	 */
	private Token processBracket() {
		Character bracket = expression.charAt(0);
		expression = expression.substring(1);

		if (bracket == '(') {
			return new Token(TokenType.OPEN_BRACKET, bracket);
		}

		return new Token(TokenType.CLOSED_BRACKET, bracket);
	}

	/**
	 * Method that is used for finding next Token if lexer has found an operator
	 * in the given expression. Operators will be 'translated' to their text
	 * versions ('*'="and", '+'="or", '!'="not", ':+:'="xor"). It is previously
	 * ensured that found token will not have value different from these four.
	 * 
	 * @return next token, representing an operator
	 * 
	 */
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

	/**
	 * Method that is used for finding next Token if lexer has found an
	 * identifier in the given expression. This method determines if found
	 * identifier is token of type OPERATOR ("and", "xor", "or", "not), or type
	 * CONSTANT ("true", "false"), or type VARIABLE(any combination of
	 * alpha-numeric and '_' characters that doesn't match previous
	 * identifiers).
	 * 
	 * @return next token, representing an identifier
	 * 
	 */
	private Token processIdentifier() {

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

	/**
	 * Private method used for determining of mode that lexer will work in while
	 * generating next token.
	 * 
	 * @param expression
	 *            expression that is processed
	 * @return String representing lexer mode
	 */
	private String determineMode(String expression) {

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

	/**
	 * Getter method used for getting current token.
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}

}
