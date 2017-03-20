package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>ObjectStack</code> represents implementation of the stack that
 * contains objects. It implements all methods that stack usually offers with
 * adaptor pattern using the <code>ArrayIndexedCollection</code> from previous
 * problem as an adaptee.
 * 
 * @author Matteo Miloš
 *
 */
public class ObjectStack {

	/**
	 * Instance of the class <code>ArrayIndexedCollection</code> where are
	 * stored elements of the stack
	 */
	private ArrayIndexedCollection objectStack;

	/**
	 * Public constructor of the class <code>ObjectStack</code> that creates
	 * instance of the class <code>ArrayIndexedCollection</code> which is
	 * necessary for implementation of the stack.
	 */
	public ObjectStack() {
		objectStack = new ArrayIndexedCollection();
	}

	/**
	 * Method that checks if stack is empty.
	 * 
	 * @return <code>true</code> if stack is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return objectStack.isEmpty();
	}

	/**
	 * Method that returns the size of the stack. Size represents number of
	 * element currently stored on the stack.
	 * 
	 * @return size(number of elements stored) of the stack
	 */
	public int size() {
		return objectStack.size();
	}

	/**
	 * Method that pushes object given as an argument to the top of the stack.
	 * 
	 * @param value
	 *            value being added on the stack
	 * @throws IllegalArgumentException
	 *             if it is tried to push null value on the stack
	 */
	public void push(Object value) {

		if (value == null) {
			throw new IllegalArgumentException();
		}

		objectStack.add(value);
	}

	/**
	 * Method that pops(gets) the element from the top of the stack and removes
	 * it from the stack.
	 * 
	 * @return element stored on the top of the stack
	 * @throws EmptyStackException
	 *             if it is attempted to pop element from an empty stack.
	 */
	public Object pop() {
		if (objectStack.isEmpty()) {
			throw new EmptyStackException();
		}
		Object popped = peek();
		objectStack.remove(objectStack.size() - 1);
		return popped;
	}

	/**
	 * Method that returns element last added to the stack.
	 * 
	 * @return element stored on the top of the stack
	 * @throws EmptyStackException
	 *             if it is attempted to get element from an empty stack.
	 */
	public Object peek() {

		if (objectStack.isEmpty()) {
			throw new EmptyStackException();
		}

		return objectStack.get(objectStack.size() - 1);
	}

	/**
	 * Method that removes all elements from the stack.
	 */
	public void clear() {
		objectStack.clear();
	}

}
