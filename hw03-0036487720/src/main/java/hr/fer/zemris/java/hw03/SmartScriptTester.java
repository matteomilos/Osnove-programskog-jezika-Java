package hr.fer.zemris.java.hw03;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class SmartScriptTester {

	public static void main(String[] args) {

//		if(args.length!=1){
//			System.out.println("Command line accepts only one argument.");
//			System.exit(0);
//		}
//		String filepath = args[0];
		String filepath = "D:\\Java workspace\\zadace\\hw03-0036487720\\example\\primjer 4.txt";
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(docBody);
		System.out.println();
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);

		System.out.println(originalDocumentBody);
		System.out.println();
		System.out.println(originalDocumentBody2);
		if(originalDocumentBody.equals(originalDocumentBody2)){
			System.out.println("Bravo, uspješno riješeno");
		}else{
			System.out.println("Padaš");
		}
		


	}

	public static String createOriginalDocumentBody(Node document) {
		if (document == null) {
			throw new IllegalArgumentException("Document given can not be null.");
		}
		return document.toString();
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}