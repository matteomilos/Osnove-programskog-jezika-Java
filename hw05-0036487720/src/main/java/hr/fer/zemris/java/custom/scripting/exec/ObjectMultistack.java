package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <code>ObjectMultiStack</code> is a class, implemented using map, that allows
 * user to store multiple values for the same key. Multiple values for the same
 * key are wrapped as {@linkplain MultistackEntry} objects and are put to the
 * stack-like abstraction. Values stored most recently under certain key will be
 * the values who will be retrieved first when search under certain key is being
 * conducted.
 * 
 * @author Matteo Miloš
 *
 */
public class ObjectMultistack {

	/**
	 * Map whose keys are instances of type <code>String</code> and values are
	 * instances of type <code>MultistackEntry</code> connected in single-linked
	 * list if there is more than one value associated with the same key.
	 */
	private Map<String, MultistackEntry> map;

	/**
	 * Public constructor that allocates memory for our internal map.
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * <tt>MultistackEntry</tt> is inner static class which acts as a node of
	 * single-linked list, list of values, associated with key in internal map
	 * of <tt>ObjectMultistack</tt>.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private static class MultistackEntry {

		/**
		 * Value of type <tt>ValueWrapper</tt> stored in node.
		 */
		private ValueWrapper value;

		/**
		 * Reference to the next node.
		 */
		private MultistackEntry next;

		/**
		 * Public constructor which receives object of type
		 * <tt>ValueWrapper</tt> to be stored inside the node and reference to
		 * the next node.
		 * 
		 * @param value
		 *            value to be stored
		 * @param next
		 *            reference to the next node
		 * 
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

	}

	/**
	 * Public method that pushes item on the top of stack that is associated wit
	 * the given key in the map.
	 * 
	 * @param name
	 *            key of the entry
	 * @param valueWrapper
	 *            item that will be pushed to the stack
	 * @throws IllegalArgumentException
	 *             if passed key or value is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null || valueWrapper == null) {
			throw new IllegalArgumentException("You can't push null key or value.");
		}

		MultistackEntry entry = new MultistackEntry(valueWrapper, map.get(name));
		map.put(name, entry);
	}

	/**
	 * Method that removes the object at the top of stack associated with the
	 * passed key (name) and returns that object as the value of this function.
	 * 
	 * @param name
	 *            key of the entry
	 * @return object retrieved from the top of stack
	 * @throws IllegalArgumentException
	 *             if key passed is null
	 * @throws NoSuchElementException
	 *             if stack under given key is empty
	 */
	public ValueWrapper pop(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		if (!map.containsKey(name)) {
			throw new NoSuchElementException("Stack is empty.");
		}

		MultistackEntry entry = map.get(name);

		if (entry.next == null) {
			map.remove(name);
		} else {
			map.put(name, entry.next);
		}

		return entry.value;
	}

	/**
	 * Method that returns the object from the top of stack associated with the
	 * passed key (name) without removing it from the stack.
	 * 
	 * @param name
	 *            key of the entry
	 * @return the object at the top of this stack
	 * @throws IllegalArgumentException
	 *             if key passed is null
	 * @throws NoSuchElementException
	 *             if stack under given key is empty
	 */
	public ValueWrapper peek(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		if (map.get(name) == null) {
			throw new NoSuchElementException("Stack is empty.");
		}

		return map.get(name).value;
	}

	/**
	 * Checks if there are entry's stored in the map under given key.
	 * 
	 * @param name
	 *            key
	 * @return <tt>true</tt> if key has stored entry's, <tt>false</tt> otherwise
	 * @throws IllegalArgumentException
	 *             if key passed is null
	 */
	public boolean isEmpty(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		return !map.containsKey(name);
	}

}
