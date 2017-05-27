package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class used for demonstration of functionalities of {@link SmartScriptLexer}
 * and {@link SmartScriptParser} classes using a visitor pattern.
 * 
 * @author Matteo Miloš
 *
 */
public class TreeWriter {

	/**
	 * Method which is called at the start of this program.
	 * 
	 * @param args
	 *            command line arguments, not used in this method
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		String filepath = args[0];
		String docBody = null;

		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}

	/**
	 * Static class that implements {@link INodeVisitor}, used for implementing
	 * visitor pattern.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.println(node.getText());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.println(node.getText());
		}

	}

}
