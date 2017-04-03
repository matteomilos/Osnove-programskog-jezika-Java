package hr.fer.zemris.java.hw05.observer1;

/**
 * Class <code>DoubleValue</code> implements {@linkplain IntegerStorageObserver}
 * and represents implementation of an observer from observer pattern. This
 * observer displays squared value of subject's current storage on standard
 * output. Observers performs this action for unlimited number of times.
 * 
 * @author Matteo Miloš
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * {@inheritDoc} This implementation of the method prints the double value
	 * of subject's current storage on the standard output for unlimited number
	 * of times.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {

		int newValue = istorage.getValue(); /*-da ne moram više puta pozivati getValue()*/

		System.out.printf("Provided new value: %d, square is %d%n", newValue, newValue * newValue);
	}

}
