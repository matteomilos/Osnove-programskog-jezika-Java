package hr.fer.zemris.bf.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;
import hr.fer.zemris.bf.parser.Parser;

/**
 * Visitor <code>ExpressionEvaluator</code> implements interface
 * {@link NodeVisitor}. It is used for calculating value of logical expression,
 * gotten from the {@link Parser} class, using given variable values. This
 * implementation of the visitor uses stack for storing all the current values.
 * At the end, if there is more than one value on the stack,
 * {@link IllegalStateException} is thrown, otherwise, result is what we get
 * when we call {@link Stack#peek()}.
 * 
 * @author Matteo Miloš
 *
 */
public class ExpressionEvaluator implements NodeVisitor {

	/** array of boolean values for variables */
	private boolean[] values;

	/** map which is used for storing variables and their indexes. */
	private Map<String, Integer> positions;

	/**
	 * stack which is used for storing current logical results and final result
	 * of the expression
	 */
	private Stack<Boolean> stack = new Stack<>();

	/**
	 * Public constructor for this implementation of the {@link NodeVisitor},
	 * puts given variable list into the map parameterized by ordinal number of
	 * the variable.
	 * 
	 * @param variables
	 *            list of variables
	 */
	public ExpressionEvaluator(List<String> variables) {
		int i = 0;
		positions = new HashMap<>();
		for (String string : variables) {
			positions.put(string, i++);
		}
	}

	/**
	 * Public setter method used for setting variables to the given values.
	 * 
	 * @param values
	 *            boolean array of values
	 */
	public void setValues(boolean[] values) {
		this.values = values;
		start();
	}

	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	@Override
	public void visit(VariableNode node) {
		int position;
		try {
			position = positions.get(node.getName());
		} catch (NullPointerException exc) {
			throw new IllegalStateException("No information available about variable: " + node.getName());
		}
		stack.push(values[position]);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
		stack.push(node.getOperator().apply(stack.pop()));
	}

	@Override
	public void visit(BinaryOperatorNode node) {

		for (Node child : node.getChildren()) {
			child.accept(this);
		}

		int childNum = node.getChildren().size();

		for (int i = 0; i < childNum - 1; i++) {
			stack.push(node.getOperator().apply(stack.pop(), stack.pop()));

		}

	}

	/**
	 * Method used for deleting current stack and preparing it for the new
	 * evaluation.
	 */
	public void start() {
		stack.clear();
	}

	/**
	 * Method used for getting result from the logical expression from the
	 * stack.
	 * 
	 * @return result of the logical expression
	 * 
	 * @throws IllegalStateException
	 *             if there is more than one element on the stack
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("There is more than one element on the stack");
		}

		return stack.peek();
	}

}
