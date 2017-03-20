package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParser {
	public SmartScriptLexer lexer;
	public DocumentNode documentNode;

	public SmartScriptParser(String documentBody) {
		this.lexer = new SmartScriptLexer(documentBody);
		ObjectStack stack = new ObjectStack();
		documentNode = new DocumentNode();
		stack.push(documentNode);
		parse();
	}

	private void parse() {
	}
}
