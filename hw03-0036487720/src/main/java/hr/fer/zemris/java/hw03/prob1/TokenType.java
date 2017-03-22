package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration that describes proper types of object of type {@linkplain Token}.
 * 
 * @author Matteo Miloš
 *
 */
public enum TokenType {
	/**
	 * Enumeration that signals end of current document.
	 */
	EOF,
	/**
	 * Enumeration that represents a word
	 */
	WORD,
	/**
	 * Enumeration that represents a number
	 */
	NUMBER,
	/**
	 * Enumeration that represents a symbol
	 */
	SYMBOL
}
