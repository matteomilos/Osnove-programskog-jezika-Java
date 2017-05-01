package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Implementation of the interface {@link Node}, represents constant in the
 * expression which was gotten from {@link Parser}. Constant is boolean value,
 * and therefore it can be only of value <code>true</code> or
 * <code>false</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ConstantNode implements Node {

	/** Value of the constant */
	private boolean value;

	/**
	 * Public constructor that initializes constant to the given value.
	 * 
	 * @param value
	 *            boolean value of the constant
	 */
	public ConstantNode(boolean value) {
		this.value = value;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Public getter method used for returning value of the constant.
	 * 
	 * @return value of the constant
	 */
	public boolean getValue() {
		return value;
	}

}
