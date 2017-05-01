package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.parser.Parser;

/**
 * Interface <code>NodeVisitor</code> is used for application of Visitor
 * pattern. It is consisted of four unimplemented visit() methods, each for one
 * type of node that can be created by {@link Parser}.
 * 
 * @author Matteo Miloš
 *
 */
public interface NodeVisitor {

	/**
	 * Method used for visiting {@link ConstantNode}. Based on implementation of
	 * this interface is determined what will be done on visiting the node.
	 * 
	 * @param node
	 *            node representing constant
	 */
	void visit(ConstantNode node);

	/**
	 * Method used for visiting {@link VariableNode}. Based on implementation of
	 * this interface is determined what will be done on visiting the node.
	 * 
	 * @param node
	 *            node representing variable
	 */
	void visit(VariableNode node);

	/**
	 * Method used for visiting {@link UnaryOperatorNode}. Based on
	 * implementation of this interface is determined what will be done on
	 * visiting the node.
	 * 
	 * @param node
	 *            node representing unary operator
	 */
	void visit(UnaryOperatorNode node);

	/**
	 * Method used for visiting {@link BinaryOperatorNode}. Based on
	 * implementation of this interface is determined what will be done on
	 * visiting the node.
	 * 
	 * @param node
	 *            node representing binary operator
	 */
	void visit(BinaryOperatorNode node);

}
