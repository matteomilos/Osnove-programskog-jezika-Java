package hr.fer.zemris.java.hw05.observer2;

public class IntegerStorageChange {
	private IntegerStorage integerStorage;
	private int oldValue;
	private int newValue;

	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
		if (integerStorage == null) {
			throw new IllegalArgumentException("Integer storage given can not be null");
		}
		this.integerStorage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	public int getOldValue() {
		return oldValue;
	}

	public int getNewValue() {
		return newValue;
	}

}
