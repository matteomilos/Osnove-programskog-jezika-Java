package hr.fer.zemris.java.hw05.observer2;

public class DoubleValue implements IntegerStorageObserver {
	private int changesLeft;

	public DoubleValue(int changesLeft) {
		this.changesLeft = changesLeft;
	}

	@Override
	public void valueChanged(IntegerStorageChange integerStorageChange) {
		if (changesLeft-- > 0) {
			System.out.println("Double value: " + integerStorageChange.getNewValue() * 2);
		}
		if (changesLeft <= 0) {
			integerStorageChange.getIntegerStorage().removeObserver(this);
		}

	}

}
