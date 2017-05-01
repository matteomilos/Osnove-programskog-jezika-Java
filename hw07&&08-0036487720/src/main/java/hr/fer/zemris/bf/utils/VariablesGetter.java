package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Visitor <code>VariablesGetter</code> implements interface
 * {@link NodeVisitor}. It is used for calculating and getting all the variables
 * from given expression. It ignores all nodes except {@link VariableNode}. Each
 * variable is then added to the list {@link VariablesGetter#variables} if this
 * list doesn't already contain that value. Values are sorted in lexicographical
 * order.
 * 
 * @author Matteo Miloš
 *
 */
public class VariablesGetter implements NodeVisitor {

	/**
	 * List of variable from given expression.
	 */
	List<String> variables;

	/**
	 * Public constructor used for instatiating new list of variables.
	 */
	public VariablesGetter() {
		variables = new ArrayList<>();
	}

	@Override
	public void visit(ConstantNode node) {
	}

	@Override
	public void visit(VariableNode node) {
		if (!variables.contains(node.getName())) {
			variables.add(node.getName());
		}

	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
	}

	/**
	 * Public method that is used for getting list of variables of the
	 * expression. Before returning of the list, list is sorted
	 * lexicographically.
	 * 
	 * @return list of variables
	 */
	public List<String> getVariables() {
		Collections.sort(variables);
		return variables;
	}

}
