package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class <code>DocumentNode</code> is derived from class <code>Node</code> and
 * represents uppermost node in tree structure of document. Other nodes are
 * children of either this node, or children nodes of this node.
 * 
 * @author Matteo Miloš
 *
 */
public class DocumentNode extends Node {
	@Override
	public String toString() {
		return getChildrenToString(this);
	}
}
