package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Implementation of the interface {@link Node}, represents variable in the
 * expression which was gotten from {@link Parser}. Variable is represented with
 * its name which can be any instance of the {@link String}.
 * 
 * @author Matteo Miloš
 *
 */
public class VariableNode implements Node {

	/** Name of the variable */
	private String name;

	/**
	 * Public constructor that initializes variable to the given name.
	 * 
	 * @param name
	 *            name of the variable
	 */
	public VariableNode(String name) {
		this.name = name;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Public getter method used for returning name of the variable.
	 * 
	 * @return name of the variable
	 */
	public String getName() {
		return name;
	}

}
