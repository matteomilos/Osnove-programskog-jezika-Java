package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

public class ConstantNode implements Node {

	private boolean value;

	public ConstantNode(boolean value) {
		this.value = value;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	public boolean getValue() {
		return value;
	}

}
