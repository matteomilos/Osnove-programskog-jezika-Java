package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class <code>Node</code> represents elementary node in tree structure of
 * document, and it can have none, one or more elements that are generated
 * through lexical analysis.
 * 
 * @author Matteo Miloš
 *
 */
public abstract class Node {

	/**
	 * Collection that represents children of this node, also of type
	 * <code>Node</code>.
	 */
	private ArrayIndexedCollection children;

	/**
	 * Method that adds node to collection of children nodes. Collection will be
	 * instantiated on the first call of this method.
	 * 
	 * @param child
	 *            node that is added do children collection
	 * @throws IllegalArgumentException
	 *             if given child node is <code>null</code> reference
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new IllegalArgumentException();
		}
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}

	/**
	 * Method that returns current number of children of this node
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Method that returns reference to child node in collection of children
	 * that is based on given index
	 * 
	 * @param index
	 *            child position in collection
	 * @return reference to child node
	 * @throws IndexOutOfBoundsException
	 *             if index is invalid value, valid values are [0, size-1]
	 */
	public Node getChild(int index) {
		return (Node) children.get(index); // nisam provjeravao index jer se
											// provjerava u getu od
											// collectiona
	}

	public abstract String getText();

	/**
	 * Protected method that returns string representation of the child nodes of
	 * parent node by iterating through all the children and appending them
	 * relying on the implementation of their <tt>toString()</tt> method.
	 * 
	 * @param parent
	 *            parent whose child nodes will be returned as strings
	 * @return child nodes appended to each other as string
	 */
	protected static String getChildrenToString(Node parent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, numOfChildren = parent.numberOfChildren(); i < numOfChildren; i++) {
			Node child = parent.getChild(i);
			sb.append(child.getText());
		}
		return sb.toString();
	}

	public abstract void accept(INodeVisitor visitor);

	public ArrayIndexedCollection getChildren() {
		return children;
	}

}
