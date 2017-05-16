package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.addNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.containsValue;
import static hr.fer.zemris.java.hw01.UniqueNumbers.treeSize;
import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;;

@SuppressWarnings("javadoc")
public class UniqueNumbersTest {

	
	@Test
	public void treeSizeOfEmptyTree() {
		TreeNode head = null;
		int numOfElements = treeSize(head);
		assertEquals(0, numOfElements);
	}

	@Test
	public void treeSizeOfPopulatedTree() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		head = addNode(head, 28);
		head = addNode(head, 39);
		head = addNode(head, 72);
		head = addNode(head, 13);
		head = addNode(head, 39);
		int numOfElements = treeSize(head);
		assertEquals(8, numOfElements); 
	}

	@Test
	public void containsExistingElement() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		head = addNode(head, 28);
		boolean contains = containsValue(head, 21);
		assertEquals(true, contains);
	}

	@Test
	public void containsNonExistingElement() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		head = addNode(head, 28);
		assertFalse(containsValue(head, 42));
	}

	@Test
	public void containsElementInEmptyTree() {
		TreeNode head = null;
		assertFalse(containsValue(head, 42));

	}

	@Test
	public void correctlyAddingNodesTest() {
		TreeNode head = null;
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
		head = addNode(head, 28);
		head = addNode(head, 39);
		head = addNode(head, 72);
		head = addNode(head, 13);
		head = addNode(head, 39);
		int expected = head.left.right.left.value;
		assertEquals(28, expected);
		int expected2 = head.right.left.value;
		assertEquals(72, expected2);
		int expected3 = head.value;
		assertEquals(42, expected3);

	}

}
