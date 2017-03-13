package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that is used for production of binary tree and adding nodes in it.
 * Nodes (integers) are loaded from standard input, if user enters value that is
 * not integer, program refuses to add that node and asks for another one.
 * Running of program is stopped once user enters "kraj".
 * 
 * @author Matteo Miloš
 *
 */
public class UniqueNumbers {

	/**
	 * Nested static class which represents one node of binary tree, it's
	 * defined with three variable, reference to left and right node and integer
	 * which represents value of the node.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	static class TreeNode {

		/**
		 * Reference to the left node.
		 */
		TreeNode left;
		/**
		 * Reference to the right node.
		 */
		TreeNode right;
		/**
		 * Node value
		 */
		int value;
	}

	/**
	 * Method where program starts running.
	 * 
	 * @param args
	 *            command line arguments, not used in this program
	 */
	public static void main(String[] args) {

		TreeNode head = null;
		Scanner sc = new Scanner(System.in);
		head = readInput(head, sc);
		System.out.print("Ispis od najmanjeg: ");
		printAscending(head);
		System.out.printf("%nIspis od najveceg: ");
		printDescending(head);
		sc.close();

	}

	/**
	 * 
	 * Method used for loading integers from some type of input and their adding
	 * to the binary tree. If user enters non-integer value, method asks for
	 * another input. Method ends once user enters "kraj".
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 * @param sc
	 *            instance of Scanner class, used for reading from some type of
	 *            input
	 * @return reference to first (head) node of binary tree
	 */
	private static TreeNode readInput(TreeNode head, Scanner sc) {

		while (true) {
			System.out.print("Unesite broj > ");
			if (sc.hasNextInt()) {
				head = addNode(head, sc.nextInt());
			} else {
				String inputLine = sc.next();
				if (inputLine.equals("kraj")) {
					break;
				}
				System.out.printf("'%s' nije cijeli broj.%n", inputLine);
			}
		}
		return head;
	}

	/**
	 * Method we use for descending print of values of binary tree nodes.
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 */
	private static void printDescending(TreeNode head) {
		if (head == null) {
			return;
		}
		printDescending(head.right);
		System.out.print(head.value + " ");
		printDescending(head.left);
	}

	/**
	 * Method we use for ascending print of values of binary tree nodes.
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 */
	private static void printAscending(TreeNode head) {
		if (head == null) {
			return;
		}
		printAscending(head.left);
		System.out.print(head.value + " ");
		printAscending(head.right);

	}

	/**
	 * Method that recursively checks if binary tree contains specified element.
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 * @param possibleDuplicate
	 *            number that we are trying to find in binary tree
	 * @return <code>true</code> if tree contains specified element
	 *         <code>false</code> if it doesn't contain
	 * 
	 */
	public static boolean containsValue(TreeNode head, int possibleDuplicate) {
		if (head == null) {
			return false;
		}
		if (possibleDuplicate < head.value) {
			return containsValue(head.left, possibleDuplicate);
		} else if (possibleDuplicate > head.value) {
			return containsValue(head.right, possibleDuplicate);
		} else {
			return true;
		}
	}

	/**
	 * Method that recursively calculates size (number of node) of binary tree.
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 * @return number of nodes in binary tree¸
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}

		return 1 + treeSize(head.right) + treeSize(head.left);
	}

	/**
	 * Method that is recursively adding new nodes in binary tree.
	 * 
	 * @param head
	 *            reference to first (head) node of binary tree
	 * @param newElement
	 *            value of node which we are adding to binary tree
	 * @return reference to first (head) node of binary tree
	 */
	public static TreeNode addNode(TreeNode head, int newElement) {

		if (head == null) {
			head = new TreeNode();
			head.value = newElement;
			System.out.printf("Dodano.%n");
			return head;
		}

		if (newElement < head.value) {
			head.left = addNode(head.left, newElement);
		} else if (newElement > head.value) {
			head.right = addNode(head.right, newElement);
		} else {
			System.out.printf("Broj već postoji. Preskačem.%n");
		}

		return head;
	}

}
