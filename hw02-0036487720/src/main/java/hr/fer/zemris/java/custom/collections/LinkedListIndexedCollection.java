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

	protected LinkedListIndexedCollection() {
		first = last = null;
	}

	public LinkedListIndexedCollection(Collection collection) {
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
		if (indexOf(value) < 0) {
			return false;
		}
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
