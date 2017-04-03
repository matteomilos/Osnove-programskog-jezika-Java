package hr.fer.zemris.java.hw05.observer2;

public class ChangeCounter implements IntegerStorageObserver {
	
	private static int numOfChanges = 0;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++numOfChanges);
	}

}
