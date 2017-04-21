package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class BinaryOperatorNode.
 */
public class BinaryOperatorNode implements Node {

	/** The name. */
	String name;

	/** The children. */
	List<Node> children;

	/** The operator. */
	BinaryOperator<Boolean> operator;

	/**
	 * Instantiates a new binary operator node.
	 *
	 * @param name the name
	 * @param children the children
	 * @param operator the operator
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.bf.model.Node#accept(hr.fer.zemris.bf.utils.NodeVisitor)
	 */
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
