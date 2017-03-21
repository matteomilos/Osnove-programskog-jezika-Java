package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.Token;

public class SmartScriptParser {
	private SmartScriptLexer lexer;
	private DocumentNode documentNode;
	private ObjectStack stack;

	public SmartScriptParser(String documentBody) {
		this.lexer = new SmartScriptLexer(documentBody);
		stack = new ObjectStack();
		documentNode = new DocumentNode();
		stack.push(documentNode);
		parse();
	}

	private void parse() {
		SmartScriptToken token = lexer.nextToken();

		while (token.getType() != SmartScriptTokenType.EOF) {

			if (token.getType() != SmartScriptTokenType.TAG) {
				TextNode textNode = new TextNode(token.getValue().asText());
				Node current = (Node) stack.peek();
				current.addChildNode(textNode);

			} else {
				SmartScriptToken tagToken = lexer.nextToken();

				if (!(tagToken.getType().equals(SmartScriptTokenType.VARIABLE)
						|| tagToken.getValue().asText().equals("="))) {
					throw new SmartScriptParserException("Tag name invalid.");
				}

				switch (tagToken.getValue().asText().toUpperCase()) {
				case "FOR":
					addFor();
					break;
				case "END":
					addEnd();
					break;
				case "=":
					addEcho();
					break;
				default:
					throw new SmartScriptLexerException("Wrong tag name");
				}
			}

			token = lexer.nextToken();
		}

		if (stack.size() != 1) { /*- ovaj jedan koji je ostao je documentNode */
			throw new SmartScriptParserException("Document has more opened non-empty tags than END tags.");
		}
	}

	private void addEcho() {

		ObjectStack emptyStack = new ObjectStack();
		int i = 0;
		// @formatter:off
		for (	SmartScriptToken currentToken = lexer.nextToken(); 
				currentToken != null && currentToken.getType() != SmartScriptTokenType.TAG;
				i++, currentToken = lexer.nextToken()	) {
		// @formatter:on

			switch (currentToken.getType()) {
			case VARIABLE:
			case DOUBLE_NUMBER:
			case INT_NUMBER:
			case STRING:
			case FUNCTION:
			case OPERATOR:
				emptyStack.push(currentToken.getValue());
				break;
			case EOF:
				throw new SmartScriptParserException("Empty tag is not properly closed.");
			default:
				throw new SmartScriptParserException("Invalid argument in empty tag.");
			}

		}

		Element[] elements = new Element[i];

		for (i--; i >= 0; i--) {
			elements[i] = (Element) emptyStack.pop();
		}

		EchoNode echoNode = new EchoNode(elements);
		Node parent = (Node) stack.peek();
		parent.addChildNode(echoNode);
	}

	private void addEnd() {
		try {
			stack.pop();
			stack.peek(); /*- ovo ce takoder throwati EmptyStackException ako je
							 stog prazan (nakon popa stack.size() mora biti >  0) */
		} catch (EmptyStackException exception) {
			throw new SmartScriptParserException("Document has more END tags than opened tags.");
		}

		SmartScriptToken next = lexer.nextToken();

		if (next.getType() != SmartScriptTokenType.TAG) {
			throw new SmartScriptParserException("END tag wasn't properly closed.");
		}
	}

	private void addFor() {
		ObjectStack forStack = new ObjectStack();
		int i = 0;
		// @formatter:off
		for (SmartScriptToken currentToken = lexer.nextToken(); 
				currentToken != null && currentToken.getType() != SmartScriptTokenType.TAG; 
				i++, currentToken = lexer.nextToken()) { 		// @formatter:on

			SmartScriptTokenType type = currentToken.getType();

			switch (type) {
			case VARIABLE:
			case INT_NUMBER:
			case DOUBLE_NUMBER:
			case STRING:
				if (i == 0 && type != SmartScriptTokenType.VARIABLE) {
					throw new SmartScriptParserException("Wrong first argument in for loop");
				}

				forStack.push(currentToken.getValue());
				break;
			case EOF:
				throw new SmartScriptParserException("For loop was not closed before \"END\" tag");
			default:
				throw new SmartScriptParserException("Invalid arguments in for loop");
			}
		}

		ElementVariable variable = null;
		Element startExpression = null;
		Element stepExpression = null;
		Element endExpression = null;

		switch (i) {
		case 3:
			endExpression = (Element) forStack.pop();
			startExpression = (Element) forStack.pop();
			variable = (ElementVariable) forStack.pop();
			break;
		case 4:
			stepExpression = (Element) forStack.pop();
			endExpression = (Element) forStack.pop();
			startExpression = (Element) forStack.pop();
			variable = (ElementVariable) forStack.pop();
			break;
		default:
			throw new SmartScriptParserException("Too much arguments in for loop!");
		}
		ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);

		Node parent = (Node) stack.peek();

		parent.addChildNode(forLoopNode);
		stack.push(forLoopNode);

	}

	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
