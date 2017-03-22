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

/**
 * Class <code>SmartScriptParser</code> represents object that performs sintax
 * analysis of the given text document. Text is being processed in a way that
 * its elements can belong to one of three logical units, and base on their
 * belonging, they are being encapsulated in nodes that continue to build sintax
 * tree of document. All nodes of document are children of the first, uppermost
 * node, document node. First logical unit is text that is places outside the
 * tags (Tags are defined with boundaries "{$"-beginning of the tag and "$}"-end
 * of the tag. Second logical unit is text inside the FOR tag. This tag is
 * defined with first word after beginning of the tag which is "for". Third and
 * the last logical unit is "empty" tag that can have any given name.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptParser {
	/**
	 * Lexer that performs lexical analysis and provides parser with tokens.
	 */
	private SmartScriptLexer lexer;
	/**
	 * First, uppermost node, all other nodes are children of this node.
	 */
	private DocumentNode documentNode;
	/**
	 * Stack for pushing nodes that are children of the main node.
	 */
	private ObjectStack stack;

	/**
	 * Public constructor that accepts text of document as an argument. Text
	 * given will be lexically analyzed and parsed.
	 * 
	 * @param documentBody
	 *            document text
	 */
	public SmartScriptParser(String documentBody) {
		this.lexer = new SmartScriptLexer(documentBody);
		ObjectStack stack = new ObjectStack();
		documentNode = new DocumentNode();
		stack.push(documentNode);
		parse();
	}

	/**
	 * Private method used for parsing, it performs syntax analysis of the text.
	 * It creates a hierarchy of nodes and stores it in the documentNode
	 * collection of children nodes. Most of its work is delegated to other
	 * methods.
	 */
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

	/**
	 * Private method that is called if lexer has generated token that signals
	 * beginning of the empty tag. This method parses content of an empty tag.
	 * 
	 * @throws SmartScriptParserException
	 *             if empty tag is not properly closed
	 * @throws SmartScriptParserException
	 *             if there is invalid argument in empty tag
	 */
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

	/**
	 * 
	 * Private method that is called if lexer has generated token that is END
	 * (closing) tag. That tag gave us the information that current node on the
	 * stack has no more children, and document can be continued parsed outside
	 * of the current node.
	 * 
	 * @throws SmartScriptParserException
	 *             if document has more END tags than opened tags
	 * @throws SmartScriptParserException
	 *             if END tag wasn't properly closed
	 */
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

	/**
	 * Private method that is called if lexer has generated an opening FOR tag
	 * token. Method goes through the FOR tag and checks if syntax inside the
	 * tag is correct. Rules are: first arugment in tag has to be variable,
	 * which is followed by either 2 or 3 elements of any type.
	 * 
	 * @throws SmartScriptParserException
	 *             if first argument in loop is invalid
	 * @throws SmartScriptParserException
	 *             if for loop was not closed properly
	 * @throws SmartScriptParserException
	 *             if there are invalid arguments in for loop
	 * @throws SmartScriptParserException
	 *             if there is too many or too few arguments in for loop
	 */
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
					throw new SmartScriptParserException("Wrong first argument in for loop.");
				}

				forStack.push(currentToken.getValue());
				break;
			case EOF:
				throw new SmartScriptParserException("For loop was not properly closed.");
			default:
				throw new SmartScriptParserException("Invalid arguments in for loop.");
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

	/**
	 * Public getter method that gives us the base node of the document that is
	 * being parsed.
	 * 
	 * @return base node of the document
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
