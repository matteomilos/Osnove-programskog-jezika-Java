package hr.fer.zemris.java.hw05.observer1;

public class DoubleValue implements IntegerStorageObserver {
	private int changesLeft;

	public DoubleValue(int changesLeft) {
		this.changesLeft = changesLeft;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (changesLeft-- > 0) {
			System.out.println("Double value: " + istorage.getValue() * 2);
		}
		if (changesLeft <= 0) {
			istorage.removeObserver(this);
		}

	}

}
