package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents general form of collection of object and is used as
 * interface for more specified types of collections.
 * 
 * @author Matteo Miloš
 * 
 */
public class Collection {

	/**
	 * Protected constructor of class <code>Collection</code. It delegates
	 * constructor to superclass.
	 */
	protected Collection() {
		super();
	}

	/**
	 * Method that determines if collection is empty(size is equal to 0).
	 * 
	 * @return <code>true</code> if collection is contains no objects
	 *         <code>false</code> if collection contains some objects.
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Method that calculates size of collection.
	 * 
	 * @return number of currently stored objects in collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method that is used for adding given object to the collection. Allows
	 * adding duplicate elements.
	 * 
	 * @param value
	 *            object added to the collection
	 */
	public void add(Object value) {

	}

	/**
	 * Method that determines if collection contains specified value.
	 * 
	 * @param value
	 *            value that we are trying to find in collection
	 * @return <code>true</code> if collection contains given value
	 *         <code>false</code> if collection doesn't contain given value
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method that is used for removal of specified element in collection. If
	 * collection contains more of the same elements, only one occurrence will
	 * be removed.
	 * 
	 * @param value
	 *            element that we are trying to remove
	 * @return <code>true</code> if collection contains given value and removes
	 *         one occurrence of it <code>false</code> if collection doesn't
	 *         contain given value or couldn't remove element because of some
	 *         reason
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that allocates new array with size equals to the size of this
	 * collection and fills it with collection content.
	 * 
	 * @return array of all elements in collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method that calls <code>processor.process(.)</code> for each element of
	 * this collection. The order in which elements will be sent is undefined.
	 * 
	 * @param processor
	 *            processor that will be used for utilizing method process
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * Method that adds into itself all elements from given collection. Other
	 * collection remains unchanged.
	 * 
	 * @param other
	 *            collection whose elements will be added to this collection
	 */
	public void addAll(Collection other) {

		Processor localProcessor = new Processor() {
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		};

		other.forEach(localProcessor);
	}

	/**
	 * Method that removes all elements from collection.
	 * 
	 */
	public void clear() {

	}

}
