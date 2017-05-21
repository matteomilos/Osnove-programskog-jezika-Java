package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Ne valja"); // TODO:
			return;
		}
		String filepath = args[0];
		/*- String filepath = "D:\\Java\\workspace\\zadace\\hw03-0036487720\\example\\primjer3.txt";*/
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
