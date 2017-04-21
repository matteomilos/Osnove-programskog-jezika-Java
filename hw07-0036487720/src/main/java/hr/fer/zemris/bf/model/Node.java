package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Interface Node.
 */
public interface Node {

	/**
	 * Accept.
	 *
	 * @param visitor the visitor
	 */
	void accept(NodeVisitor visitor);

}
