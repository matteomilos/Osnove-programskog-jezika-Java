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

public class ExpressionEvaluator implements NodeVisitor {

	private boolean[] values;

	private Map<String, Integer> positions;

	private Stack<Boolean> stack = new Stack<>();

	public ExpressionEvaluator(List<String> variables) {
		int i = 0;
		positions = new HashMap<>();
		for (String string : variables) {
			positions.put(string, i++);
		}
	}

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

	public void start() {
		stack.clear();
	}

	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("There is more than one element on the stack");
		}

		return stack.peek();
	}

}
