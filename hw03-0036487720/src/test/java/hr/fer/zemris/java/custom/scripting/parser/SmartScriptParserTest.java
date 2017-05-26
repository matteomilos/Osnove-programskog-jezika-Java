package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;

/**
 * Class <code>SmartScriptParserTest</code> will be used for testing both
 * {@linkplain SmartScriptLexer} and {@linkplain SmartScriptParser} classes.
 * Tests will be executed using documents from (<i>src/main/resources</i>), and
 * will be properly loaded using the
 * {@linkplain SmartScriptParserTest#loader(String)} method
 * 
 * @author Matteo Miloš
 *
 */
@SuppressWarnings("javadoc")
public class SmartScriptParserTest {

	SmartScriptParser parser1;

	String document = loader("primjer.txt");

	String document1 = loader("primjer1.txt");

	String document2 = loader("primjer2.txt");

	String document3 = loader("primjer3.txt");

	String document4 = loader("primjer4.txt");

	String document5 = loader("primjer5.txt");

	String document6 = loader("primjer6.txt");

	String document7 = loader("primjer7.txt");

	String document8 = loader("primjer8.txt");

	String document9 = loader("primjer9.txt");

	String document10 = loader("primjer10.txt");

	String document11 = loader("primjer11.txt");

	String document12 = loader("primjer12.txt");

	String document13 = loader("primjer13.txt");

	String document14 = loader("primjer14.txt");

	String document15 = loader("primjer15.txt");

	String document16 = loader("primjer16.txt");

	@Test
	public void testDocumentWithForAndEmptyTags1() {
		parser1 = new SmartScriptParser(document);
		String first = parser1.getDocumentNode().getText();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().getText();
		assertEquals(first, second);
	}

	@Test
	public void testDocumentWithEmptyTags1() {
		parser1 = new SmartScriptParser(document1);
		String first = parser1.getDocumentNode().getText();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().getText();
		assertEquals(first, second);
	}

	@Test
	public void testDocumentWithEmptyTags2() {
		parser1 = new SmartScriptParser(document2);
		String first = parser1.getDocumentNode().getText();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().getText();
		assertEquals(first, second);
	}

	@Test
	public void testDocumentWithForAndEmptyTags2() {
		parser1 = new SmartScriptParser(document3);
		String first = parser1.getDocumentNode().getText();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().getText();
		assertEquals(first, second);
	}

	@Test
	public void testDocumentEscapeBeforeTag() {
		parser1 = new SmartScriptParser(document4);
		String first = parser1.getDocumentNode().getText();
		parser1 = new SmartScriptParser(first);
		String second = parser1.getDocumentNode().getText();
		assertEquals(first, second);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithoutClosedForTag() {
		parser1 = new SmartScriptParser(document5);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithInvalidTagName() {
		parser1 = new SmartScriptParser(document6);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testWithWrongEscapeSequenceOutsideTags() {
		parser1 = new SmartScriptParser(document7);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testWithWrongEscapeSequenceOutsideTags2() {
		parser1 = new SmartScriptParser(document8);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testWithWrongEscapeSequenceInsideTags() {
		parser1 = new SmartScriptParser(document9);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithTooMuchArgsInForTag() {
		parser1 = new SmartScriptParser(document10);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithTooFewArgsInForTag() {
		parser1 = new SmartScriptParser(document11);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithWrongFirstArgumentInForLoop() {
		parser1 = new SmartScriptParser(document12);
	}

	@Test(expected = SmartScriptParserException.class)
	public void testWithTooMuchEndTags() {
		parser1 = new SmartScriptParser(document13);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testWithInvalidSymbolInsideEmptyTag() {
		parser1 = new SmartScriptParser(document14);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testNumberWithTwoDots() {
		parser1 = new SmartScriptParser(document15);
	}

	@Test(expected = SmartScriptLexerException.class)
	public void testFunctionWithInvalidName() {
		parser1 = new SmartScriptParser(document16);
	}

	/**
	 * Private method that gets file with the name that is given as an argument
	 * of the method located in the folder /resources, and converts that
	 * document to string
	 * 
	 * @param filename
	 *            name of the file to be converted
	 * @return string version of file
	 */
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