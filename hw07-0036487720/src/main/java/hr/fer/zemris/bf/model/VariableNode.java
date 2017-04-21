package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class VariableNode.
 */
public class VariableNode implements Node {

	/** The name. */
	private String name;

	/**
	 * Instantiates a new variable node.
	 *
	 * @param name the name
	 */
	public VariableNode(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.bf.model.Node#accept(hr.fer.zemris.bf.utils.NodeVisitor)
	 */
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
