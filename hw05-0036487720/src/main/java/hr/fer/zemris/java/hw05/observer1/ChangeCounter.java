package hr.fer.zemris.java.hw05.observer1;

/**
 * Class <code>ChangeCounter</code> implements
 * {@linkplain IntegerStorageObserver} and represents implementation of an
 * observer from observer pattern. This observer counts number of subject's
 * value changes since the start of the tracking of the subject.
 * 
 * @author Matteo Miloš
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Private variable used as a counter for changes. Initially set to
	 * 0.
	 */
	private int numOfChanges = 0;

	/**
	 * {@inheritDoc} This implementation of the method prints number of value
	 * changes since the start of the tracking on the standard output.
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++numOfChanges);
	}

}
