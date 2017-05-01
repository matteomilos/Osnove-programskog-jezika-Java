package hr.fer.zemris.bf.parser;

import java.util.ArrayList;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

//@formatter:off //used to prevent auto formatting grammar in javadoc
/**
 * Class <code>Parser</code> represents object that performs syntax analysis of
 * the given expression. Parser is using his own instance of {@link Lexer} for
 * generating next tokens from given expression. All nodes of expression are
 * children of the first, uppermost node. First node can be of type
 * {@link ConstantNode} or {@link VariableNode} only if expression is consisting
 * of only one node. Otherwise, first node will be of type
 * {@link UnaryOperatorNode} or {@link BinaryOperatorNode}, based on type of
 * logical expression.
 * 
 * This parser will be constructed as a recursive descent parser and will use
 * following grammar (uppercase - NONTERMINAL SYMBOLS, lowercase or in single
 * quotation marks - 'terminal symbols'):
 * 
 * <ul>
 *		<li>			S  -> E1							</li>
 * 		<li>			E1 -> E2 (or E2)*					</li>
 * 		<li>			E2 -> E3 (xor E3)*					</li>
 * 		<li>			E3 -> E4 (and E4)*					</li>
 * 		<li>			E4 -> not E4 | E5					</li>
 *		<li>			E5 -> var | konst | '(' E1 ')'		</li>
 * </ul>
 * 
 * Private methods {@link Parser#startParsing()}, {@link Parser#e1()},
 * {@link Parser#e2()}, {@link Parser#e3()}, {@link Parser#e4()},
 * {@link Parser#e5()} are based on this grammar and used only for that purpose.
 * 
 * @author Matteo Miloš
 *
 */
//@formatter:on

public class Parser {

	/** Lexer that performs lexical analysis and provides parser with tokens. */
	private Lexer lexer;

	/**
	 * First, uppermost node, all other nodes(if there are any of them) are
	 * children of this node.
	 */
	private Node node;

	/**
	 * Public constructor that accepts text of document as an argument. Text
	 * given will be lexically analyzed and parsed.
	 * 
	 * @param expression
	 *            logical expression
	 */
	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Expression mustn't be null");
		}

		this.lexer = new Lexer(expression);

		try {
			parse();

		} catch (LexerException e) {
			throw new ParserException("Lexer has thrown exception: " + e.getMessage());
		} 
	}

	/**
	 * Private method used for parsing, it performs syntax analysis of the text.
	 * It creates a hierarchy of nodes and stores it in the {@link Parser#node}
	 * collection of children nodes. Most of its work is delegated to other
	 * methods.
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private void parse() {

		lexer.nextToken();
		node = startParsing();

		if (lexer.getToken().getTokenType() != TokenType.EOF) {

			String message = String.format("Unexpected token: Type: %s, Value: %s, Value is instance of: %s",
					lexer.getToken().getTokenType(), lexer.getToken().getTokenValue(),
					lexer.getToken().getTokenValue().getClass().getName());

			throw new ParserException(message);
		}
	}

	/**
	 * Private method, represents first nonterminal symbol of the grammar, 'S'.
	 * Only used for calling method that represents next nonterminal symbol
	 * 'E1'.
	 * 
	 * @return first, uppermost node
	 * 
	 */
	private Node startParsing() {

		node = e1();
		return node;
	}

	/**
	 * Private method, represents 'E1' nonterminal symbol of the grammar, first
	 * is called method that represents nonterminal symbol 'E2', and then it
	 * performs its own work if there is an operator token representing operator
	 * 'or'.
	 * 
	 * @return node for the method {@link Parser#startParsing()}
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private Node e1() {
		Node node = e2();

		if (lexer.getToken().getTokenValue() == null) {
			return node;
		}

		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getToken().getTokenValue().equals("or")) {

			checkIfEOF();
			children.add(e2());

			BinaryOperatorNode orNode = new BinaryOperatorNode("or", children, (t, s) -> Boolean.logicalOr(t, s));

			if (endOfOperator("or")) {
				return orNode;
			}
		}
		return node;
	}

	/**
	 * Private method, represents 'E2' nonterminal symbol of the grammar, first
	 * is called method that represents nonterminal symbol 'E3', and then it
	 * performs its own work if there is an operator token representing operator
	 * 'and'.
	 * 
	 * @return node for the method {@link Parser#e1()}
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private Node e2() {
		Node node = e3();

		if (lexer.getToken().getTokenValue() == null) {
			return node;
		}

		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getToken().getTokenValue().equals("xor")) {

			checkIfEOF();
			children.add(e3());

			BinaryOperatorNode xorNode = new BinaryOperatorNode("xor", children, (t, s) -> Boolean.logicalXor(t, s));

			if (endOfOperator("xor")) {
				return xorNode;
			}
		}
		return node;
	}

	/**
	 * Private method, represents 'E3' nonterminal symbol of the grammar, first
	 * is called method that represents nonterminal symbol 'E4', and then it
	 * performs its own work if there is an operator token representing operator
	 * 'xor'.
	 * 
	 * @return node for the method {@link Parser#e2()}
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private Node e3() {
		Node node = e4();

		if (lexer.getToken().getTokenType() == TokenType.EOF) {
			return node;
		}

		ArrayList<Node> children = new ArrayList<>();
		children.add(node);
		while (lexer.getToken().getTokenValue().equals("and")) {

			checkIfEOF();
			children.add(e4());

			BinaryOperatorNode andNode = new BinaryOperatorNode("and", children, (t, s) -> Boolean.logicalAnd(t, s));

			if (endOfOperator("and")) {
				return andNode;
			}
		}
		return node;
	}

	/**
	 * Private method, represents 'E4' nonterminal symbol of the grammar. If our
	 * next token is of type {@link TokenType#OPERATOR} and its value is 'not',
	 * then is constructed {@link UnaryOperatorNode}, and again called method
	 * {@link Parser#e4()}, otherwise is called method {@link Parser#e5()}.
	 * 
	 * @return node for the method {@link Parser#e3()}
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private Node e4() {

		if (lexer.getToken().getTokenValue().equals("not")) {

			checkIfEOF();
			Node node = e4();
			UnaryOperatorNode notNode = new UnaryOperatorNode("not", node, t -> Boolean.logicalXor(t, true));

			return notNode;
		} else {
			return e5();
		}

	}

	/**
	 * Private method, represents 'E5' nonterminal symbol of the grammar. If our
	 * next token is of type {@link TokenType#VARIABLE} or
	 * {@link TokenType#CONSTANT}, then is created either {@link VariableNode}
	 * or {@link ConstantNode}. If token is of type
	 * {@link TokenType#OPEN_BRACKET}, that token is consumed and method
	 * {@link Parser#e1()} is called. Other cases mean that something is wrong
	 * with expression and {@link ParserException} is thrown.
	 * 
	 * @return node for the method {@link Parser#e4()}
	 * 
	 * @throws ParserException
	 *             in case of incorrect expression
	 * 
	 */
	private Node e5() {
		Token currentToken = lexer.getToken();
		Node node;

		switch (currentToken.getTokenType()) {
		case VARIABLE:
			node = new VariableNode((String) currentToken.getTokenValue());
			break;

		case CONSTANT:
			node = new ConstantNode((boolean) currentToken.getTokenValue());
			break;

		case OPEN_BRACKET:
			lexer.nextToken();
			node = e1();

			if (lexer.getToken().getTokenType() != TokenType.CLOSED_BRACKET) {
				throw new ParserException("Expected ')' but found " + lexer.getToken().getTokenType() + ".");
			}
			break;

		default:
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
					lexer.getToken().getTokenType(), lexer.getToken().getTokenValue());
			throw new ParserException(message);

		}

		lexer.nextToken();

		return node;

	}

	/**
	 * Private method used for checking if next token is of type EOF, and if it
	 * is, that means that given logical expression was wrong because we
	 * expected more tokens different from EOF.
	 */
	private void checkIfEOF() {

		if (lexer.nextToken().getTokenType() == TokenType.EOF) {
			String message = String.format("Unexpected token found: {Type: %s, Value: %s}.",
					lexer.getToken().getTokenType(), lexer.getToken().getTokenValue());

			throw new ParserException(message);
		}
	}

	/**
	 * Private method used in methods {@link Parser#e1()}, {@link Parser#e2()}
	 * and {@link Parser#e3()} for determining if next token is same operator
	 * and should we continue with our loop or return current found node.
	 * 
	 * @param operatorValue
	 *            current operator value, can be only one of those: "or", "and",
	 *            "xor"
	 * @return <code>true</code> if next token is of type EOF or different value
	 *         than operatorValue given as argument of this method,
	 *         <code>false</code> otherwise
	 */
	private boolean endOfOperator(String operatorValue) {
		return lexer.getToken().getTokenType() == TokenType.EOF
				|| !lexer.getToken().getTokenValue().equals(operatorValue);
	}

	/**
	 * Getter method used for getting the base node of expression.
	 * 
	 * @return base node of the expression
	 */
	public Node getExpression() {
		return node;
	}

}
