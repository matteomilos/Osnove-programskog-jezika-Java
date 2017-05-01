package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class LexerTest {

	Lexer lexer;
	Token token;

	@Test
	public void testLexerWithConstantOnly1() {
		lexer = new Lexer("  0  ");
		token = lexer.nextToken();
		assertEquals(TokenType.CONSTANT, token.getTokenType());
		assertEquals(false, token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerWithConstantOnly2() {
		lexer = new Lexer("  1     ");
		token = lexer.nextToken();
		assertEquals(TokenType.CONSTANT, token.getTokenType());
		assertEquals(true, token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerWithConstantOnly3() {
		lexer = new Lexer("  fAlSe  ");
		token = lexer.nextToken();
		assertEquals(TokenType.CONSTANT, token.getTokenType());
		assertEquals(false, token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerWithConstantOnly4() {
		lexer = new Lexer("  TrUe    ");
		token = lexer.nextToken();
		assertEquals(TokenType.CONSTANT, token.getTokenType());
		assertEquals(true, token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test(expected = LexerException.class)
	public void testLexerWithInvalidConstant() {
		lexer = new Lexer("  10     ");
		token = lexer.nextToken();
	}

	@Test
	public void testLexerWithVariableOnly() {
		lexer = new Lexer("  Aas_tAs  ");
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AAS_TAS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpression1() {
		lexer = new Lexer("As AnD Be  ");
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("and", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpression2() {
		lexer = new Lexer("As oR Be  ");
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("or", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpression3() {
		lexer = new Lexer("As xOr Be  ");
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("xor", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpressionWithBrackets1() {
		lexer = new Lexer("( As AnD Be ) ");
		token = lexer.nextToken();
		assertEquals(TokenType.OPEN_BRACKET, token.getTokenType());
		assertEquals('(', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("and", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.CLOSED_BRACKET, token.getTokenType());
		assertEquals(')', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpressionWithBrackets2() {
		lexer = new Lexer(" (  As oR Be ) ");
		token = lexer.nextToken();
		assertEquals(TokenType.OPEN_BRACKET, token.getTokenType());
		assertEquals('(', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("or", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.CLOSED_BRACKET, token.getTokenType());
		assertEquals(')', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerSimpleExpression3WithBrackets() {
		lexer = new Lexer(" (  As xOr Be ) ");
		token = lexer.nextToken();
		assertEquals(TokenType.OPEN_BRACKET, token.getTokenType());
		assertEquals('(', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("AS", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("xor", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("BE", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.CLOSED_BRACKET, token.getTokenType());
		assertEquals(')', token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test
	public void testLexerWithMultipleNots() {
		lexer = new Lexer("nOt noT Not aSa");
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("not", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("not", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.OPERATOR, token.getTokenType());
		assertEquals("not", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.VARIABLE, token.getTokenType());
		assertEquals("ASA", token.getTokenValue());
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		assertEquals(null, token.getTokenValue());
	}

	@Test(expected = LexerException.class)
	public void testInvalidCharactersInLexer1() {
		lexer = new Lexer(" ? ");
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidCharactersInLexer2() {
		lexer = new Lexer(" - ");
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidCharactersInLexer3() {
		lexer = new Lexer(" :+ ");
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidCharactersInLexer4() {
		lexer = new Lexer(" a; ");
		token = lexer.nextToken();
		token = lexer.nextToken();

	}

	@Test(expected = LexerException.class)
	public void testInvalidCharactersInLexer5() {
		lexer = new Lexer(" 7 ");
		lexer.nextToken();
	}

}
