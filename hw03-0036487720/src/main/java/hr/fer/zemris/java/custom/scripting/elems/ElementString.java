package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {
	private String value;
	
	public ElementString(String name){
		this.value = name;
	}
	
	public String getValue(){
		return value;
	}
	
	@Override
	public String asText() {
		return value;
	}

	@Override
	public String toString() {
		return "ElementString [value=" + value + "]";
	}
}
