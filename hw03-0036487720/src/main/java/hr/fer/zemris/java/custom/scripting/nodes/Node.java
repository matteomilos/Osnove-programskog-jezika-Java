package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Node {
	private ArrayIndexedCollection collection;

	public void addChildNode(Node child) {
		if (child == null) {
			throw new IllegalArgumentException();
		}
		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}
		collection.add(child);
	}

	public int numberOfChildren() {
		return collection.size();
	}

	public Node getChild(int index) {
		return (Node) collection.get(index); // nisam provjeravao index jer se
												// provjerava u getu od
												// collectiona
	}


	protected static String getChildrenToString(Node parent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, numOfChildren = parent.numberOfChildren(); i < numOfChildren; i++) {
			Node child = parent.getChild(i);
			sb.append(child);
		}
		return sb.toString();
	}

}
