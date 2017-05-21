package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration that describes states in which instance of class
 * {@linkplain SmartScriptLexer} can work. State TEXT describes state in which
 * lexer treats every character as a part of string. State TAG describes state
 * in which lexer is currently inside the "tag" and where every sequence of
 * characters has its own representation.
 * 
 * @author Matteo Miloš
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Text state of lexer
	 */
	TEXT,
	/**
	 * Tag state of lexer
	 */
	TAG
}
