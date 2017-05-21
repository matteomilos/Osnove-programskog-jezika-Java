package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class that represents an element of language. It encapsulates expression
 * that was created during lexical analysis of text. This class represents
 * contract that will be obeyed by other "elements" derived from this class.
 * 
 * @author Matteo Miloš
 *
 */
public class Element {

	/**
	 * Method that return value of element in text representation
	 * <code>String</code>.
	 * 
	 * @return This elements value as text.
	 */
	public String asText() {
		return "";
	}
}
