package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates value of the word in object of type <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementString extends Element {
	/**
	 * Value of the object
	 */
	private String value;

	/**
	 * Public constructor that creates instance with the name set to argument
	 * given
	 * 
	 * @param value
	 *            word that is stored
	 * @throws IllegalArgumentException
	 *             if argument given is null
	 */
	public ElementString(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Value given can not be null.");
		}
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

	@Override
	public String toString() {
		//@formatter:off
		return asText(). replace("\\", "\\\\")
						.replace("\"", "\\\"")
						.replace("\\\\r", "\\r")
						.replaceAll("\\\\n", "\\n");
	}
}
