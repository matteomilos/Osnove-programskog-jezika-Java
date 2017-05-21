package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates number in double precision.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Value in double precision.
	 */
	private double value;

	/**
	 * Public constructor that creates object from given value that represents
	 * real number.
	 * 
	 * @param value
	 *            real number in double precision
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	@Override
	public String toString() {
		return asText();
	}
}
