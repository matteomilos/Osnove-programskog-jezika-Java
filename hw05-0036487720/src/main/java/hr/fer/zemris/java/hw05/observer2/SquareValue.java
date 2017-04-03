package hr.fer.zemris.java.hw05.observer2;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange integerStorageChange) {
		int newValue = integerStorageChange.getNewValue(); /*-da ne moram više puta pozivati getValue()*/
		System.out.printf("Provided new value: %d, square is %d%n", newValue, newValue * newValue);
	}

}
