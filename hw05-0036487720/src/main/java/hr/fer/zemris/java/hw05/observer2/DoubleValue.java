package hr.fer.zemris.java.hw05.observer2;

/**
 * Class <code>DoubleValue</code> implements {@linkplain IntegerStorageObserver}
 * and represents implementation of an observer from observer pattern. This
 * observer displays doubled value of subject's current storage on standard
 * output. Observers performs this action for limited number of times, which are
 * given to him through constructor, and after that it will deregister itself
 * from subject.
 * 
 * @author Matteo Miloš
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Private variable used to store number of times action will be performed
	 * by observer.
	 */
	private int changesLeft;

	/**
	 * Public constructor that receives integer value that represents number of
	 * times that action will be performed by observer.
	 * 
	 * @param changesLeft
	 *            number of times this action will be performed
	 * @throws IllegalArgumentException
	 *             in case if given argument is number less than 1
	 */
	public DoubleValue(int changesLeft) {
		if (changesLeft < 1) {
			throw new IllegalArgumentException("You can't add observer with 0 as output limit.");
		}
		this.changesLeft = changesLeft;
	}

	/**
	 * {@inheritDoc} This implementation of the method prints the double value
	 * of subject's current storage on the standard output for limited number of
	 * times. After limit is reached, observer is deregistered from subject.
	 */
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
