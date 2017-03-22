package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumerations defines types of token that class <code>SmartScriptLexer</code>
 * can generate in lexical analysis. Allowed types are:
 * <ul>
 * <li>FUNCTION - encapsulates <code>String</code>, name of the function</li>
 * <li>VARIABLE - encapsulates <code>String</code>, name of the variable</li>
 * <li>STRING - encapsulates <code>String</code>, word</li>
 * <li>TEXT - encapsulates <code>String</code>, one or more words</li>
 * <li>CONSTANT_INTEGER - encapsulates <code>Integer</code>, integer number</li>
 * <li>CONSTANT_DOUBLE - encapsulates <code>Double</code>, real number</li>
 * <li>OPERATOR - encapsulates <code>String</code>, symbol</li>
 * <li>EOF - encapsulates <code>null</code> value, end of file that is
 * processed</li>
 * <li>TAG - encapsulates <code>String</code>, represents beginning or the end
 * of the tag</li>
 * </ul>
 * 
 * @author Matteo Miloš
 *
 */
public enum SmartScriptTokenType {
	/**
	 * name of the variable
	 */
	VARIABLE,
	/**
	 * symbol
	 */
	OPERATOR,
	/**
	 * name of the function
	 */
	FUNCTION,
	/**
	 * word
	 */
	STRING,
	/**
	 * end of file that is processed
	 */
	EOF,
	/**
	 * represents beginning or the end of the tag
	 */
	TAG,
	/**
	 * Integer number
	 */
	INT_NUMBER,
	/**
	 * Real number
	 */
	DOUBLE_NUMBER,
	/**
	 * One or more words
	 */
	TEXT
}
