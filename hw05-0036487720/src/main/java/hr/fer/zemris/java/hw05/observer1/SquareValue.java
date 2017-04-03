package hr.fer.zemris.java.hw05.observer1;

public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		
		int newValue = istorage.getValue(); /*-da ne moram više puta pozivati getValue()*/
		
		System.out.printf("Provided new value: %d, square is %d%n", newValue, newValue * newValue);
	}

}
