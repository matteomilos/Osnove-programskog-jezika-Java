package hr.fer.zemris.java.custom.scripting.nodes;

public class TextNode extends Node {

	private String text;

	public TextNode(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{", "\\{");
	}

}
