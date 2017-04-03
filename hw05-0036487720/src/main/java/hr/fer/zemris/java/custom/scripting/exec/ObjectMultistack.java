package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ObjectMultistack {

	private Map<String, MultistackEntry> map = new HashMap<>();

	public void push(String name, ValueWrapper valueWrapper) {
		if (map.get(name) == null) {
			map.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}
		
		MultistackEntry entry = new MultistackEntry(valueWrapper, map.get(name));
		map.put(name, entry);
	}

	public ValueWrapper pop(String name) {
		if (map.get(name) == null) {
			throw new NoSuchElementException("Stack is empty.");
		}
		
		MultistackEntry entry = map.get(name);
		map.put(name, entry.next);
		return entry.value;
	}

	public ValueWrapper peek(String name) {
		if (map.get(name) == null) {
			throw new NoSuchElementException("Stack is empty.");
		}
		
		return map.get(name).value;
	}

	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}

	private static class MultistackEntry {
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		private ValueWrapper value;
		private MultistackEntry next = null;
	}
}
