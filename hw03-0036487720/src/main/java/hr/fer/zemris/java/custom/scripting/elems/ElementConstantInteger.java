package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	private int value;

	public ElementConstantInteger(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	@Override
	public String toString() {
		return asText();
	}
}
