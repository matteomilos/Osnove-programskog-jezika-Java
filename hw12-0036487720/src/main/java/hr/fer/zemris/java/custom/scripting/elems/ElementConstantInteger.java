package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates integer number.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementConstantInteger extends Element {

	/** Integer value. */
	private int value;

	/**
	 * Public constructor that creates object from given value that represents
	 * integer number.
	 * 
	 * @param value
	 *            integer number number in double precision
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return asText();
	}
}
