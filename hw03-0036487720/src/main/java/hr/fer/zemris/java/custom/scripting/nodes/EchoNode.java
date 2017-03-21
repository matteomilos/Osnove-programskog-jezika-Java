package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

public class EchoNode extends Node {

	private Element[] elements;

	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// @formatter:off
		sb.append("{$= ");

		for (int i = 0; i < elements.length; i++) {
			if (elements[i] instanceof ElementString) {

				sb.	append("\"").
					append(elements[i]).
					append("\" ");

			} else {

				if (elements[i] instanceof ElementFunction) {
					sb.append("@");
				}

				sb.	append(elements[i]).
					append(" ");
			}
		}

		sb.append("$}");
		// @formatter:on
		return sb.toString();
	}
}
