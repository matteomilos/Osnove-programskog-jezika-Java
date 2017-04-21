package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

public class UnaryOperatorNode implements Node {

	private String name;

	private Node child;

	private UnaryOperator<Boolean> operator;

	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
	}

	public Node getChild() {
		return child;
	}

	public String getName() {
		return name;
	}

	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

}
