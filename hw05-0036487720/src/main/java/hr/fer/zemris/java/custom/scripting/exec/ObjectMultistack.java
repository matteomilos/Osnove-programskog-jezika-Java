package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ObjectMultistack {

	private Map<String, MultistackEntry> map = new HashMap<>();

	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null || valueWrapper == null) {
			throw new IllegalArgumentException("You can't push null key or value.");
		}

		MultistackEntry entry = new MultistackEntry(valueWrapper, map.get(name));
		map.put(name, entry);
	}

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

	public ValueWrapper peek(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		if (map.get(name) == null) {
			throw new NoSuchElementException("Stack is empty.");
		}

		return map.get(name).value;
	}

	public boolean isEmpty(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Key can't be null.");
		}

		return !map.containsKey(name);
	}

	private static class MultistackEntry {
		
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		private ValueWrapper value;
		private MultistackEntry next;
		
		public ValueWrapper getValue() {
			return value;
		}
		public MultistackEntry getNext() {
			return next;
		}
		
		
	}
}
