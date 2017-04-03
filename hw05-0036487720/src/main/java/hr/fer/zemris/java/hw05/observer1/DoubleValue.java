package hr.fer.zemris.java.hw05.observer1;

public class DoubleValue implements IntegerStorageObserver {

	private int changesLeft;

	public DoubleValue(int changesLeft) {
		if (changesLeft < 1) {
			throw new IllegalArgumentException("You can't add observer with 0 as output limit.");
		}
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
