package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// @formatter:off   isključen formatter radi ljepše preglednosti
		sb.	append("{$ FOR ").
			append(variable.toString()).
			append(" ").
			append(startExpression.toString()).
			append(" ").
			append(endExpression.toString()).
			append(" ");
		
		if (stepExpression != null) {
			sb.	append(stepExpression.toString()).
				append(" ");
		}
		sb.	append("$}").
			append(getChildrenToString(this)).
			append("{$END$}");
		// @formatter:on

		return sb.toString();
	}

}
