package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Parameterized class <code>SimpleHashTable</code> represents implementation of
 * an hash table. Table is used for storage of key-value pairs that are realized
 * by nested static class {@linkplain TableEntry}. Parameter K represents data
 * type of the key, and parameter V represents data type of the value. For
 * determining in which slot we will put pair, we use calculation of hash value
 * on key. Class implements parameterized interface <{@linkplain Iterable} which
 * gives us possibility to visit pairs of hash table through for-each loop.
 * 
 * @author Matteo Miloš
 *
 * @param <K>
 *            parameter that defines data type of the key
 * @param <V>
 *            parameter that defines data type of the value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Default capacity of the table.
	 */
	public static final int DEFAULT_TABLE_SIZE = 16;
	/**
	 * Threshold, if table has more elements than
	 * <code>ALLOWED_PERCENTAGE_BEFORE_RESIZE</code> * <code>size</code>, then
	 * table will be resized
	 */
	public static final double ALLOWED_PERCENTAGE_BEFORE_RESIZE = 0.75;
	/**
	 * Number of elements that are stored in this collection.
	 */
	private int size;
	/**
	 * Reference to the array of parameterized objects of type
	 * {@linkplain TableEntry}.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Numerator of changes of the content of the hash table. This numerator is
	 * updated by methods {@linkplain #put(Object, Object)},
	 * {@linkplain #clear()} and {@linkplain #remove(Object)}.
	 */
	private int modCount;

	/**
	 * Public default constructor of the hash table without given value, it
	 * creates array of <code>TableEntry</code> objects with the capacity of 16.
	 * Delegates its work to more complex constructor.
	 */
	public SimpleHashtable() {
		this(DEFAULT_TABLE_SIZE);
	}

	/**
	 * Public constructor of the hash table with given value, it creates array
	 * of <code>TableEntry</code> objects with the given capacity.
	 * 
	 * @param capacity
	 *            capacity of the hash table
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Invalid size of the table.");
		}
		int newCapacity = 1;
		while (newCapacity < capacity) {
			newCapacity *= 2;
		}
		this.table = new TableEntry[newCapacity];
	}

	/**
	 * Public static nested parameterized class <code>TableEntry</code>
	 * represents object that encapsulates key-value pair of the hash table.
	 * Class owns three private instance variables, key and value of the pair,
	 * and the pointer to the next object of the type <code>TableEntry</code>.
	 * 
	 * @author Matteo Miloš
	 *
	 * @param <K>
	 *            Parameter that defines data type of the instance variable
	 *            'key'
	 * @param <V>
	 *            Parameter that defines data type of the instance variable
	 *            'value'
	 */
	public static class TableEntry<K, V> {
		/**
		 * Key of the pair that is in the hash table.
		 */
		private K key;
		/**
		 * Value of the pair that is in the hash table.
		 */
		private V value;
		/**
		 * Pointer to the next object of the type <code>TableEntry</code>.
		 */
		private TableEntry<K, V> next;

		/**
		 * Public constructor that creates new TableEntry instance. It gets
		 * three parameters and sets key, value, and pointer to the next object
		 * using given parameters.f
		 * 
		 * @param key
		 *            key of the pair
		 * @param value
		 *            value of the pair
		 * @param next
		 *            pointer to the next object of type <code>TableEntry</code>
		 * @throws IllegalArgumentException
		 *             in case if given key is null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null.");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for the instance variable value.
		 * 
		 * @return value of the pair
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for the instance variable value.
		 * 
		 * @param value
		 *            value on which pair will be set
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for the instance variable key.
		 * 
		 * @return key with whom pair is defined
		 */
		public K getKey() {
			return key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * This method puts pair in the hash table. If pair with the specified key
	 * already exists, new pair won't be put in the table but we will update
	 * value of the pair. If pair doesn't exist, then we will create new
	 * instance of {@linkplain TableEntry} and put it in belonged slot in the
	 * table.
	 * 
	 * @param key
	 *            key of the (new) pair
	 * @param value
	 *            value of the (new) pair
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		int position = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> helper = table[position];

		if (helper == null) {
			table[position] = new TableEntry<K, V>(key, value, null);
			size++;
			modCount++;
			if (size >= ALLOWED_PERCENTAGE_BEFORE_RESIZE * table.length) {
				resizeTable();
			}
			return;
		}

		while (helper.next != null && !helper.getKey().equals(key)) {
			helper = helper.next;
		}

		if (helper.getKey().equals(key)) {
			helper.setValue(value);
		} else {
			helper.next = new TableEntry<K, V>(key, value, null);
			size++;
			modCount++;
			if (size >= ALLOWED_PERCENTAGE_BEFORE_RESIZE * table.length) {
				resizeTable();
			}
		}
	}

	/**
	 * Private helper method that creates new table of the double capacity of
	 * the old table and copies all the elements from the old to the new table.
	 */
	@SuppressWarnings("unchecked")
	private void resizeTable() {
		TableEntry<K, V>[] helpTable = table;
		table = new TableEntry[2 * helpTable.length];
		size = 0;

		for (int i = 0; i < helpTable.length; i++) {
			TableEntry<K, V> helper = helpTable[i];

			while (helper != null) {
				this.put(helper.key, helper.value);
				helper = helper.next;
			}
		}
	}

	/**
	 * Public method that returns value of the pair if the pair exists in the
	 * table, otherwise returns null. If given key parameter is null, method
	 * automatically returns null. However, we can expect that return value is
	 * null because table supports storage of the null value.
	 * 
	 * @param key
	 *            key of the pair
	 * @return value of the pair if it exists, null otherwise
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int position = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> helper = table[position];

		while (helper != null && !helper.getKey().equals(key)) {
			helper = helper.next;
		}

		if (helper == null) {
			return null;
		}

		return helper.getValue();
	}

	/**
	 * Method that returns size that represents number of stored object in the
	 * table.
	 * 
	 * @return size of that table
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Method that checks if table contains specified key. If given key is null,
	 * method automatically returns null because table can't contain null key.
	 * 
	 * @param key
	 *            key whose presence is trying to be found
	 * @return <code>true</code> if table contains given key,
	 *         <code>false</code>otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}

		int position = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> helper = table[position];

		while (helper != null && !helper.getKey().equals(key)) {
			helper = helper.next;
		}

		if (helper == null) {
			return false;
		}

		return true;
	}

	/**
	 * Method that checks if table contains one or more keys with specified
	 * value.
	 * 
	 * @param value
	 *            value whose presence is trying to be foundF
	 * @return <code>true</code> if table contains given key,
	 *         <code>false</code>otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> helper = table[i];
			while (helper != null) {
				/*-provjeravam i sa "==" i sa equals zbog moguće null vrijednosti*/
				if (helper.getValue() == value || (helper.value != null && helper.getValue().equals(value))) {
					return true;
				}
				helper = helper.next;

			}
		}
		return false;
	}

	/**
	 * Method that removes {@linkplain TableEntry} with given key if it exists
	 * in the table. Otherwise, or if the given key is null, method doesn't do
	 * anything.
	 * 
	 * @param key
	 *            key of the table entry to be removed
	 */
	public void remove(Object key) {
		if (key == null) {
			return; /*-rečeno je da metoda NIŠTA ne radi ako je ključ null (ne baca se iznimka).
					Također, iako bismo mogli tu odraditi provjeru containsKey(), pa odma izaći
					ako metoda ne sadrži ključ, možemo primijetiti da bismo time radili dupli posao 
					jer sličnu strukturu koda kao iz metode containsKey() prolazimo
					u sljedećim retcima*/
		}

		int position = Math.abs(key.hashCode() % table.length);
		TableEntry<K, V> helper = table[position];

		if (helper == null) {
			return;
		}

		if (helper.getKey().equals(key)) {
			table[position] = (helper.next == null) ? null : helper.next;
			helper = null;
			modCount++;
			this.size--;
			return;
		}

		while (helper.next != null && !helper.next.getKey().equals(key)) {
			helper = helper.next;
		}

		if (helper.next == null) {
			return;
		}

		if (helper.next.getKey().equals(key)) {
			helper.next = helper.next.next;
			modCount++;
			this.size--;
		}
	}

	/**
	 * Method that checks if table is empty.
	 * 
	 * @return <code>true</code> if table is empty, <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Method that deletes all elements from the table. Table is now empty and
	 * its size is 0.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = new TableEntry[table.length];
		size = 0;
		modCount++;
		/*-garbage collector će se riješiti nereferenciranih TableEntrya*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < table.length; i++) {
			boolean prazan = true;
			sb.append("[");
			TableEntry<K, V> helper = table[i];

			while (helper != null) {
				sb.append(helper.toString()).append(", ");
				helper = helper.next;
				prazan = false;
			}
			if (!prazan) {
				sb.delete(sb.length() - 2, sb.length());/*-brišemo nepotreban zarez i razmak s kraja*/
				sb.append("]\n");
			} else {
				sb.delete(sb.length() - 1, sb.length());
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Private class that implements parameterizes interface
	 * {@linkplain Iterator} and represents object which gives us option to
	 * visit pairs of hash table through for-each loop.
	 * 
	 * @author Matteo Miloš
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Copy of the number of modifications of table, is updated at the
		 * moment of the creation of this instance.
		 */
		private int iteratorModCount;
		/**
		 * Reference to current entry(key-value pair) in hash table.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Reference to next entry(key-value pair) in hash table.
		 */
		private TableEntry<K, V> nextEntry;
		/**
		 * Current row of the table
		 */
		private int index;

		/**
		 * Constructor of the object <code>IteratorImpl</code>, doesn't get any
		 * parameters
		 */
		public IteratorImpl() {
			iteratorModCount = modCount;
			if (size > 0) {
				findNext();
			}
		}

		/**
		 * Private helper method, used to find next entry in hash table.
		 */
		private void findNext() {
			for (; index < table.length && nextEntry == null; index++) {
				nextEntry = table[index];
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {

			if (iteratorModCount != modCount) {
				throw new ConcurrentModificationException(
						"Collection mustn't be modified during the iteration process.");
			}

			return nextEntry != null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public TableEntry<K, V> next() {

			if (iteratorModCount != modCount) {
				throw new ConcurrentModificationException(
						"Collection mustn't be modified during the iteration process.");
			}
			if (!hasNext()) {
				throw new NoSuchElementException("Iteration is over. No more elements in collection.");
			}
			currentEntry = nextEntry;
			nextEntry = currentEntry.next;
			if (nextEntry == null) {
				findNext();
			}

			return currentEntry;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			if (iteratorModCount != modCount) {
				throw new ConcurrentModificationException(
						"Collection mustn't be modified during the iteration process.");
			}

			if (currentEntry == null) {
				throw new IllegalStateException("You can't remove last object twice.");
			}

			SimpleHashtable.this.remove(currentEntry.key);
			currentEntry = null;
			iteratorModCount = modCount;
		}

	}

}
