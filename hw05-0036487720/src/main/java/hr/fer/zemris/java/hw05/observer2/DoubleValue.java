package hr.fer.zemris.java.hw05.observer2;

public class DoubleValue implements IntegerStorageObserver {

	private int changesLeft;

	public DoubleValue(int changesLeft) {
		if (changesLeft < 1) {
			throw new IllegalArgumentException("You can't add observer with 0 as output limit.");
		}
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
