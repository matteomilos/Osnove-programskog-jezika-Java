package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>ArrayIndexedCollection</code> represents resizable-array
 * implementation of the class <code>Collection</code>. Instance of this class
 * is specified with number of objects it currently stores(<code>size</code>),
 * maximal possible number of object it could store(<code>capacity</code>) and
 * with reference to array of objects where are collection elements stored.
 * 
 * @author Matteo Miloš
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Current size of collection, number of stored objects
	 */
	private int size;
	/**
	 * Current capacity of array
	 */
	private int capacity;

	/**
	 * Public getter for capacity
	 * 
	 * @return current capacity of the array
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * The array buffer where elements of the Collection are stored.
	 */
	private Object[] elements;

	/**
	 * Default initial capacity of the array
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Public constructor that allocates array of Objects with initial capacity
	 * of 16. It delegates own work to constructor which gets initial capacity
	 * of an array as an argument.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Public constructor that allocates array of Objects with initial capacity
	 * that is specified with given argument.
	 * 
	 * @param initialCapacity
	 *            initial capacity of the collection
	 * @throws IllegalArgumentException
	 *             if the specified initial capacity is not positive number
	 * 
	 */
	public ArrayIndexedCollection(int initialCapacity) {

		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	/**
	 * Public constructor that creates <code>ArrayIndexedCollection</code> with
	 * an array of specified initial capacity that contains all of the elements
	 * of the specified collection. It delegates its work to more simple
	 * constructors
	 * 
	 * @param collection
	 *            collection whose elements are added to this collection
	 * @param initialCapacity
	 *            initial capacity of this collection
	 */
	public ArrayIndexedCollection(Collection collection, int initialCapacity) {
		this(initialCapacity);
		this.addAll(collection);
	}

	/**
	 * Public constructor that creates <code>ArrayIndexedCollection</code> with
	 * an array which contains all of the elements of the given collection and
	 * whose capacity is equal to capacity of given collection
	 * 
	 * @param collection
	 *            collection that is copied to this collection, remains
	 *            unchanged
	 */
	public ArrayIndexedCollection(Collection collection) {
		this(collection, collection.size());
	}

	/**
	 * Method that is used for adding given object to the collection. Allows
	 * adding duplicate elements. If current size is equal to capacity, it
	 * creates new array with capacity equal to two times previous capacity and
	 * copies all elements in it.
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

		if (size == capacity) {
			this.elements = resize(this.elements);
		}

		elements[size++] = value;
	}

	/**
	 * Method that returns reference to an object who is located at the
	 * specified position in the list
	 * 
	 * @param index
	 *            array index of the element that is returned
	 * @return element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             if specified index is negative or greater than size of
	 *             collection
	 */
	public Object get(int index) {

		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		return elements[index];
	}

	/**
	 * Method that removes all elements from current collection and resets its
	 * size to zero.
	 */
	@Override
	public void clear() {

		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * Method that inserts specified element at the specified position of an
	 * array. It ensures that before actual insertion elements at
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

		if (size == capacity) {
			this.elements = resize(this.elements);

		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = value;
		size++;
	}

	/**
	 * Private method that returns array whose capacity is two times bigger than
	 * capacity of given array. All elements of given array are copied to
	 * returned array.
	 * 
	 * @param elements
	 *            array being resized
	 * @return array that is two times bigger than initial one
	 */
	private Object[] resize(Object[] elements) {
		Object[] help = elements;
		elements = new Object[capacity * 2];
		capacity = capacity * 2;
		System.arraycopy(help, 0, elements, 0, help.length);
		return elements;
	}

	/**
	 * Method that returns index of the first occurrence of the specified
	 * element in this array. If array doesn't contain specified element, method
	 * returns -1.
	 * 
	 * @param value
	 *            value whose index we are trying to find
	 * @return lowest index such that value on that index is equal to requested
	 *         value or -1 if there is no such value
	 */
	public int indexOf(Object value) {

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value))
				return i;
		}

		return -1;
	}

	/**
	 * Method that removes element from array at the specified index. All
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

		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[--size] = null;
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
		int location = indexOf(value);

		if (location < 0) {
			return false;
		} else {
			remove(location);
			return true;
		}
	}

	/**
	 * Method that calculates size of collection.
	 * 
	 * @return number of currently stored objects in collection
	 */
	@Override
	public int size() {
		return size;
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
		return indexOf(value) != -1;
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

		for (int i = 0; i < size; i++) {
			array[i] = this.elements[i];
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
		for (int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}
}
