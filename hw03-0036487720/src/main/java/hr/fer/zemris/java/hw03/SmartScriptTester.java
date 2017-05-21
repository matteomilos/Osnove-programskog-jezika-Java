package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class that is used for checking functionalities of classes
 * {@linkplain SmartScriptParser} and {@linkplain SmartScriptLexer}. Class also
 * implements static method that gives us possibility to retain original text of
 * document based on the syntax tree made from document.
 * 
 * @author Matteo Miloš
 *
 */
public class SmartScriptTester {

	/**
	 * Method that is called when program starts running.
	 * 
	 * @param args
	 *            path to file with test examples for checking functionalities
	 *            of our parser
	 */
	public static void main(String[] args) {

//		if (args.length != 1) {
//			System.out.println("Command line accepts only one argument.");
//			System.exit(0);
//		}
//		String filepath = args[0];
		String docBody = null;
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get("primjer.txt")), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		@SuppressWarnings("unused")
		String originalDocumentBody2 = createOriginalDocumentBody(document2);

		//originalDocumentBody2 and originalDocumentBody should be the same

	}

	/**
	 * Method that creates original document body based on node given as an
	 * argument.
	 * 
	 * @param document
	 *            node given, probably base node
	 * @return string representation of the nodes
	 */
	public static String createOriginalDocumentBody(Node document) {
		if (document == null) {
			throw new IllegalArgumentException("Document given can not be null.");
		}
		return document.toString();
	}

}