package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates value of the variable in object of type <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementVariable extends Element {
	/**
	 * Value in form <code>String</code>
	 */
	private String name;

	/**
	 * Public constructor that creates instance of this class with value set to
	 * argument given.
	 * 
	 * @param name
	 *            name of the variable
	 * @throws IllegalArgumentException
	 *             if argument given is null
	 */
	public ElementVariable(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Value given can not be null.");
		}
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public String toString() {
		return asText();
	}

}
