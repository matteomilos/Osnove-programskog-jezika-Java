package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	public static final int DEFAULT_TABLE_SIZE = 16;
	public static final double ALLOWED_PERCENTAGE_OF_POPUNJENOSTI = 0.75; /*-ISPRAVITI OVO POPUNJENOSTI*/
	private int size;
	TableEntry<K, V>[] table;
	private int modCount;

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}
	}

	public SimpleHashtable() {
		this(DEFAULT_TABLE_SIZE);
	}

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

	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null.");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

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
			if (size >= ALLOWED_PERCENTAGE_OF_POPUNJENOSTI * table.length) {
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
			if (size >= ALLOWED_PERCENTAGE_OF_POPUNJENOSTI * table.length) {
				resizeTable();
			}
		}
	}

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

	public int size() {
		return this.size;
	}

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

	public void remove(Object key) {
		if (key == null) {
			return; /*-rečeno je da metoda NIŠTA ne radi ako je ključ null (ne baca se iznimka)*/
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

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void clear() {
		table = new TableEntry[table.length];
		size = 0;
		modCount++;
		/*-garbage collector će se riješiti nereferenciranih TableEntrya*/
	}

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
			}
			else{
				sb.delete(sb.length()-1, sb.length());
			}
		}
		return sb.toString();
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private int iteratorModCount = modCount;
		private TableEntry<K, V> currentEntry;
		private TableEntry<K, V> nextEntry;
		private int index;

		public IteratorImpl() {
			if (size > 0) {
				findNext();
			}
		}

		private void findNext() {
			for (; index < table.length && nextEntry == null; index++) {
				nextEntry = table[index];
			}
		}

		@Override
		public boolean hasNext() {
			
			if (iteratorModCount != modCount) {
				throw new ConcurrentModificationException(
						"Collection mustn't be modified during the iteration process.");
			}

			return nextEntry != null;
		}

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
