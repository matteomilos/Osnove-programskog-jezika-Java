package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class <code>ForLoopNode</code> is derived from class <code>Node</code> and
 * represents node that stores content of the for-tag. It is characterized by
 * its name "FOR", after which comes variable, and then 2 or 3 elements that
 * define looping (step expression doesn't have to be defined).
 * 
 * @author Matteo Miloš
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable of for-tag, has to be defined.
	 */
	private ElementVariable variable;

	/**
	 * Starting element of for-tag, has to be defined
	 */
	private Element startExpression;

	/**
	 * Ending element of for-tag, has to be defined
	 */
	private Element endExpression;

	/**
	 * Step element of for-tag, doesn't need to be defined
	 */
	private Element stepExpression;

	/**
	 * Public constructor that accepts all the arguments that define for-tag.
	 * Necessary elements have to be defined. If they are not, appropriate
	 * exception is thrown.
	 * 
	 * @param variable
	 *            variable of for tag
	 * @param startExpression
	 *            starting expression
	 * @param endExpression
	 *            ending expression
	 * @param stepExpression
	 *            step expression
	 * @throws IllegalArgumentException
	 *             if one of neccessary elements is null
	 */
	public ForLoopNode(
			ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression
	) {
		if (variable == null || startExpression == null || endExpression == null) {
			throw new IllegalArgumentException("Value given can not be null.");
		}
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public String getText() {
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
