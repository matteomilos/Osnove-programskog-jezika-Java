package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Interface representing node one one of the tree generated from the logical
 * expression in {@link Parser}.
 * 
 * @author Matteo Miloš
 *
 */
public interface Node {

	/**
	 * Only method of the interface <code>Node</code>, represents method that
	 * will be done after visiting this node.
	 * 
	 * @param visitor
	 *            visitor used for visiting this node
	 */
	void accept(NodeVisitor visitor);

}
