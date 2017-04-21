package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

public class VariablesGetter implements NodeVisitor {

	List<String> variables;

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

	public List<String> getVariables() {
		Collections.sort(variables);
		return variables;
	}

}
