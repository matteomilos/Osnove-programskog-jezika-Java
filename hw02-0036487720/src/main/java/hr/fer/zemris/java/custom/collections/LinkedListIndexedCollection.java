package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

public class LinkedListIndexedCollection extends Collection {

	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}

	private int size = 0;
	private ListNode first;
	private ListNode last;

	public static void main(String[] args) {

		// LinkedListIndexedCollection kolekcija = new
		// LinkedListIndexedCollection();
		// kolekcija.add(5);
		// kolekcija.add(6);
		// kolekcija.add(7);
		// kolekcija.add(8);
		// kolekcija.add(4);
		// kolekcija.add(2);
		// kolekcija.add(1);
		// ListNode first = kolekcija.first;
		// for (int i = 0; i < kolekcija.size; i++) {
		// System.out.printf(first.value + " ");
		// first = first.next;
		// }
		// kolekcija.remove((Object) 8);
		// System.out.println();
		// first = kolekcija.first;
		// for (int i = 0; i < kolekcija.size; i++) {
		// System.out.printf(first.value + " ");
		// first = first.next;
		// }
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position
						// 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;
		System.out.println("col1 elements:");
		col.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(new Integer(20)); // removes 20 from collection (at position
										// 0)
	}

	protected LinkedListIndexedCollection() {
		first = last = null;
	}

	protected LinkedListIndexedCollection(Collection collection) {
		this();
		this.addAll(collection);
	}

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

	private Object get(int index) {
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

	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}

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

	@Override
	public boolean contains(Object value) {
		if (indexOf(value) < 0)
			return false;
		return true;
	}

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

	private void insert(Object value, int position) {
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

	}

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
			if (current.previous == null) {

			}
			current.previous.next = current.next;
			current.next.previous = current.previous;
		}

		size--;
	}

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
