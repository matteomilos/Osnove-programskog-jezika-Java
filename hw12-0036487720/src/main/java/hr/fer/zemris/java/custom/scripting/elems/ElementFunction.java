package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates name of the function in object of type <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementFunction extends Element {

	/** Name of the function. */
	private String name;

	/**
	 * Public constructor that creates instance of this class with name of
	 * function set to argument given.
	 * 
	 * @param name
	 *            name of the function
	 * @throws IllegalArgumentException
	 *             if argument given is null
	 */
	public ElementFunction(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Value given can not be null.");
		}
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return asText();
	}
}
