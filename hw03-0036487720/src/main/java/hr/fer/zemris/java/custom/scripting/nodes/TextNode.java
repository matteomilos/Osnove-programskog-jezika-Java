package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class <code>TextNode</code> is derived from class <code>Node</code> and it
 * represents node in tree that stores object of type <code>String</code>, or
 * better said, text generated by lexical analysis of document.
 * 
 * @author Matteo Miloš
 *
 */
public class TextNode extends Node {

	/**
	 * Text generated by lexical analysis
	 */
	private String text;

	/**
	 * Public constructor that accepts argument which represents text.
	 * 
	 * @param text
	 *            text to be stored
	 * @throws IllegalArgumentException
	 *             if value given is <code>null</code> value
	 */
	public TextNode(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Value given can not be null");
		}
		this.text = text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

}
