package hr.fer.zemris.java.custom.collections;

/**
 * 
 * Class <code>LinkedListIndexedCollection</code> extends class
 * <code>Collection</code> and represents collection of objects implemented by
 * doubly linked list. All of the operations perform as could be expected for
 * doubly linked list. Instance of this class is specified with number of
 * objects currently stored in collection (<code>size</code>), and references to
 * the first and last element of the collection.
 * 
 * 
 * @author Matteo Miloš
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Private nested static class <code>ListNode</code> represents one node of
	 * doubly linked list. Node is specified with its value, and has reference
	 * to previous and next node in the list.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private static class ListNode {
		/**
		 * Reference to the previous node.
		 */
		ListNode previous;
		/**
		 * Reference to the next node.
		 */
		ListNode next;
		/**
		 * Value of the node.
		 */
		Object value;
	}

	/**
	 * Current size of collection, number of stored objects
	 */
	private int size = 0;
	/**
	 * Reference to the first node in the list
	 */
	private ListNode first;
	/**
	 * Reference to the last node in the list
	 */
	private ListNode last;

	/**
	 * Public constructor which creates empty instance of
	 * <code>LinkedListIndexedCollection</code>.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
	}

	/**
	 * Public constructor which creates instance of
	 * <code>LinkedListIndexedCollection</code> and fills it with all elements
	 * of the given collection
	 * 
	 * @param collection
	 *            collection whose elements are going to be placed in this
	 *            collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		this();
		this.addAll(collection);
	}

	/**
	 * Method that allocates new array with size equals to the size of this
	 * collection and fills it with collection content.
	 * 
	 * @return array of all elements in collection
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode current = this.first;

		for (int i = 0; i < this.size; i++) {
			array[i] = current.value;
			current = current.next;
		}

		return array;
	}

	/**
	 * Method that calls <code>processor.process(.)</code> for each element of
	 * this collection. The order in which elements will be sent is undefined.
	 * 
	 * @param processor
	 *            processor that will be used for utilizing method process
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode current = this.first; current != null; current = current.next) {
			processor.process(current.value);
		}
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Method that returns reference to an object who is located at the
	 * specified position in the list.
	 * 
	 * @param index
	 *            position in the list of the element that is returned
	 * @return element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             if specified index is negative or greater than size of
	 *             collection
	 */
	public Object get(int index) {

		if (index < 0 || index > this.size) {
			throw new IndexOutOfBoundsException();
		}

		if (index > size / 2) {
			ListNode current = this.last;

			for (int j = size - 1; j > index; j--) {
				current = current.previous;
			}

			return current.value;
		} else {
			ListNode current = this.first;

			for (int j = 0; j < index; j++) {
				current = current.next;
			}

			return current.value;
		}
	}

	/**
	 * Method that removes all elements from current collection and resets its
	 * size to zero.
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Method that is used for adding given object to the collection. Allows
	 * adding duplicate elements. Adds new element to the end of the list and
	 * that element becomes last node of the collection.
	 * 
	 * @param value
	 *            object added to the collection
	 * @throws IllegalArgumentException
	 *             if null value is given as argument
	 */
	@Override
	public void add(Object value) {

		if (value == null) {
			throw new IllegalArgumentException();
		}

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (size == 0) {
			first = last = newNode;
		} else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}

		size++;
	}

	/**
	 * Method that determines if collection contains specified value.
	 * 
	 * @param value
	 *            value that we are trying to find in collection
	 * @return <code>true</code> if collection contains given value
	 *         <code>false</code> if collection doesn't contain given value
	 */
	@Override
	public boolean contains(Object value) {
		if (indexOf(value) < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Method that returns index of the first occurrence of the specified
	 * element in this collection. If collection doesn't contain specified
	 * element, method returns -1.
	 * 
	 * @param value
	 *            value whose index we are trying to find
	 * @return lowest index such that value on that index is equal to requested
	 *         value or -1 if there is no such value
	 */
	public int indexOf(Object value) {
		ListNode current = this.first;

		for (int index = 0; current != null; index++) {
			if (current.value.equals(value)) {
				return index;
			}
			current = current.next;
		}

		return -1;
	}

	/**
	 * Method that inserts specified element at the specified position of
	 * collection. It ensures that before actual insertion elements at
	 * <code>position</code> and at greater positions must be shifted one place
	 * toward the end. Average complexity of this method is O(n)
	 * 
	 * @param value
	 *            object added to the collection
	 * @param position
	 *            position where specified element will be inserted
	 */
	public void insert(Object value, int position) {

		if (value == null) {
			throw new IllegalArgumentException();
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (position == size || size == 0) {
			this.add(value);
			return;
		}

		ListNode newNode = new ListNode();
		newNode.value = value;

		if (position == 0) {
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
		} else {
			ListNode current = this.first;

			for (int i = 0; i < position; i++) {
				current = current.next;
			}

			current.previous.next = newNode;
			newNode.previous = current.previous;
			current.previous = newNode;
			newNode.next = current;
		}
		size++;

	}

	/**
	 * Method that removes element from collection at the specified index. All
	 * elements at positions index+1 and greater are shifted one place toward
	 * the beginning.
	 * 
	 * @param index
	 *            index of the element that is being removed
	 * @throws IndexOutOfBoundsException
	 *             if specified index is negative or greater than size-1 of the
	 *             array
	 */
	public void remove(int index) {

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			first.next.previous = null;
			first = first.next;
		} else if (index == this.size - 1) {
			last.previous.next = null;
			last = last.previous;
		} else {
			ListNode current = this.first;

			for (int i = 0; i < index; i++) {
				current = current.next;
			}

			current.previous.next = current.next;
			current.next.previous = current.previous;
		}

		size--;
	}

	/**
	 * Method that removes first occurrence of specified element if it exists in
	 * the collection
	 * 
	 * @return <code>true</code> if collection contains given value and removes
	 *         one occurrence of it <code>false</code> if collection doesn't
	 *         contain given value or couldn't remove element because of some
	 *         reason
	 */
	@Override
	public boolean remove(Object value) {

		if (value == null) {
			throw new IllegalArgumentException();
		}

		int location = indexOf(value);

		if (location < 0) {
			return false;
		} else {
			remove(location);
			return true;
		}
	}

}
