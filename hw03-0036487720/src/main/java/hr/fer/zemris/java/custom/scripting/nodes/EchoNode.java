package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * Class <code>EchoNode</code> is derived from class <code>Node</code> and
 * represents node that contains all of the elements of the empty tag.
 * 
 * @author Matteo Miloš
 *
 */
public class EchoNode extends Node {

	/**
	 * Array of references to objects of type <code>Element</code>, content of
	 * the tag/node.
	 */
	private Element[] elements;

	/**
	 * Public constructor that gets an argument representing array of elements
	 * of type <code>Element</code>. Constructor performs their storage to node.
	 * 
	 * @param elements
	 *            elements that will be added to the node
	 */
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
