package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.parser.Parser;

/**
 * Visitor that implements interface {@link NodeVisitor}. It is used for
 * printing content of the tree that we got using the {@link Parser} class. It
 * minds parent-child relation of the nodes and based on that prints indentation
 * before the node value (parent and child are separated with two spaces).
 * 
 * @author Matteo Miloš
 *
 */
public class ExpressionTreePrinter implements NodeVisitor {

	/** Instance of {@link StringBuilder} */
	private StringBuilder sb = new StringBuilder();

	/** Variable used for memorizing current indentation level */
	int indent = 0;

	@Override
	public void visit(VariableNode node) {
		printIndentation(indent);
		sb.append(node.getName());
		System.out.println(sb.toString());
		sb.setLength(0);
	}

	@Override
	public void visit(ConstantNode node) {
		printIndentation(indent);
		sb.append((node.getValue() == true) ? "1" : "0");
		System.out.println(sb.toString());
		sb.setLength(0);

	}

	@Override
	public void visit(UnaryOperatorNode node) {
		printIndentation(indent);
		sb.append(node.getName()).append("\n");
		indent += 2;
		node.getChild().accept(this);
		indent -= 2;
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		printIndentation(indent);
		sb.append(node.getName()).append("\n");
		indent += 2;
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
		indent -= 2;

	}

	/**
	 * Method that is called before each child is printed, used for printing
	 * indentation before child value.
	 * 
	 * @param indentationLevel
	 *            indentation level
	 */
	private void printIndentation(int indentationLevel) {
		for (int i = 0; i < indentationLevel; i++) {
			sb.append(" ");
		}
	}

}
