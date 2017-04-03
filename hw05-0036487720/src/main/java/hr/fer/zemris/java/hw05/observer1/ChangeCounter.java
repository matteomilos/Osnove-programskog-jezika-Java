package hr.fer.zemris.java.hw05.observer1;

public class ChangeCounter implements IntegerStorageObserver {
	private static int numOfChanges = 0;

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++numOfChanges);
	}

}
