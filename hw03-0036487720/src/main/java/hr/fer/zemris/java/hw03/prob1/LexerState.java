package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration that describes states that lexer can operate in. State is changed
 * each time '#' is read from text.
 * 
 * @author Matteo Miloš
 *
 */
public enum LexerState {
	/**
	 * Basic state of lexer
	 */
	BASIC,
	/**
	 * Extended state of lexer
	 */
	EXTENDED
}
