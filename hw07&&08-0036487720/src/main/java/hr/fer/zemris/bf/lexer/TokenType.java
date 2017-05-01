package hr.fer.zemris.bf.lexer;

/**
 * Enumerations defines types of token that class <code>SmartScriptLexer</code>
 * can generate in lexical analysis. Allowed types are:
 * <ul>
 * <li>VARIABLE - encapsulates <code>String</code>, name of the variable</li>
 * <li>CONSTANT - encapsulates <code>String</code>, either "1" or "0"</li>
 * <li>OPERATOR - encapsulates <code>String</code>, operator('*', '+', '!',
 * ':+:')</li>
 * <li>OPEN_BRACKET - encapsulates <code>String</code>, represents opened
 * bracket</li>
 * <li>CLOSED_BRACKET - encapsulates <code>String</code>, represents closed
 * bracket</li>
 * <li>EOF - encapsulates <code>null</code> value, end of file that is
 * processed</li>
 * </ul>
 * 
 * @author Matteo Miloš
 *
 */
public enum TokenType {

	/** token representing end of file that is processed */
	EOF,

	/** token representing variable */
	VARIABLE,

	/** token representing constant */
	CONSTANT,

	/** token representing an operator */
	OPERATOR,
	
	/** token representing open bracket */
	OPEN_BRACKET,
	/** token representing closed bracket */
	CLOSED_BRACKET;

}
