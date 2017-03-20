package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class SmartScriptToken {
	public SmartScriptTokenType type;
	public Element value;

	public SmartScriptToken(SmartScriptTokenType type, Element value) {
		this.type = type;
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public SmartScriptTokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "SmartScriptToken [type=" + type + ", value=" + value.asText() + "]";
	}

}
