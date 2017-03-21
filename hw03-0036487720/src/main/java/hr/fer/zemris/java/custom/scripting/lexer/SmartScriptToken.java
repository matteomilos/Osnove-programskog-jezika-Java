package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class SmartScriptToken {
	private SmartScriptTokenType type;
	private Element value;

	public SmartScriptToken(SmartScriptTokenType type, Element value) {
		this.type = type;
		this.value = value;
	}

	public Element getValue() {
		return value;
	}

	public SmartScriptTokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
