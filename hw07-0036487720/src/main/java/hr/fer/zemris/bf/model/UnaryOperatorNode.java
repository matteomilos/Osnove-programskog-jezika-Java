package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Implementation of the interface {@link Node}, represents unary operator which
 * can be: 'not'. Unary operator has three instance variables, name, node that
 * represents child, and operator that does some work (based on the type of
 * binary operator).
 * 
 * @author Matteo Miloš
 *
 */
public class UnaryOperatorNode implements Node {

	/** name of the operator */
	private String name;

	/** child of the operator */
	private Node child;

	/** actual operator, strategy that implements operator performance */
	private UnaryOperator<Boolean> operator;

	/**
	 * Public constructor that receives all the variables for the instantiation
	 * of node.
	 * 
	 * @param name
	 *            name of the operator
	 * @param child
	 *            child of the operator
	 * @param operator
	 *            actual operator, strategy that implements operator performance
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
	}

	/**
	 * Public getter method that returns child node.
	 * 
	 * @return child node
	 */
	public Node getChild() {
		return child;
	}

	/**
	 * Public getter method that returns name of the binary operator.
	 * 
	 * @return name of the operator
	 */
	public String getName() {
		return name;
	}

	/**
	 * Public getter method that return operator (strategy that implements
	 * operator performance)
	 * 
	 * @return operator
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
