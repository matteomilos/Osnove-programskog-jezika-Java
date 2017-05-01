package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Implementation of the interface {@link Node}, represents binary operator
 * which can be: 'and', 'or', 'xor'. Binary operator has three instance
 * variables, name, list of children, and operator that does some work (based on
 * the type of binary operator).
 * 
 * @author Matteo Miloš
 *
 */
public class BinaryOperatorNode implements Node {

	/** name of the operator */
	String name;

	/** list of children of the operator */
	List<Node> children;

	/** actual operator, strategy that implements operator performance */
	BinaryOperator<Boolean> operator;

	/**
	 * Public constructor that receives all the variables for the instantiation
	 * of node.
	 * 
	 * @param name
	 *            name of the operator
	 * @param children
	 *            list of the children of operator
	 * @param operator
	 *            actual operator, strategy that implements operator performance
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	/**
	 * Public getter method that returns list of the children nodes.
	 * 
	 * @return list of the children nodes
	 */
	public List<Node> getChildren() {
		return children;
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
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
