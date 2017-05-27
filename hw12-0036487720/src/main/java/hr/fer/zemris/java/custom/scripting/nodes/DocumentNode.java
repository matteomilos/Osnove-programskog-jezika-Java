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

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.scripting.nodes.Node#getText()
	 */
	@Override
	public String getText() {
		return getChildrenToString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.scripting.nodes.Node#accept(hr.fer.zemris.java.
	 * custom.scripting.nodes.INodeVisitor)
	 */
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
