package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class derived from {@linkplain Element}, represents an object that
 * encapsulates symbol in object of type <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ElementOperator extends Element {

	/**
	 * Symbol that represents operator
	 */
	private String symbol;

	/**
	 * Public constructor that creates instance of this class with symbol set to
	 * argument given.
	 * 
	 * @param symbol
	 *            desired value of the token
	 * @throws IllegalArgumentException
	 *             if argument given is null
	 */
	public ElementOperator(String symbol) {
		if (symbol == null) {
			throw new IllegalArgumentException("Value given can not be null.");
		}
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public String toString() {
		return asText();
	}

}
