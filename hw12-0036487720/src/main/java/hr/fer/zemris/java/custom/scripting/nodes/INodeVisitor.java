package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Interface INodeVisitor.
 */
public interface INodeVisitor {

	/**
	 * Visit text node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Visit for loop node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Visit echo node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Visit document node.
	 *
	 * @param node
	 *            the node
	 */
	public void visitDocumentNode(DocumentNode node);

}
