package hr.fer.zemris.java.hw05.observer2;

/**
 * Class <code>IntegerStorageChange</code> is used as an intermediate object in
 * our observers pattern. Its reference will be served to observer objects to
 * consume changes from subject. This implementation wraps subject that stores
 * integer value.
 * 
 * @author Matteo Miloš
 *
 */
public class IntegerStorageChange {
	/**
	 * Reference to the subject
	 */
	private IntegerStorage integerStorage;
	/**
	 * Subjects old storage value
	 */
	private int oldValue;
	/**
	 * Subjects new (to be current) storage value
	 */
	private int newValue;

	/**
	 * Public constructor that receives reference to subject, subject's old and
	 * new value.
	 * 
	 * @param integerStorage
	 *            reference to the subject
	 * @param oldValue
	 *            old value of the subject
	 * @param newValue
	 *            new value of the subject
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {

		if (integerStorage == null) {
			throw new IllegalArgumentException("Integer storage given can not be null");
		}

		this.integerStorage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter for reference to the subject
	 * 
	 * @return reference to the subject
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Getter for subject's old value
	 * 
	 * @return old value
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for subject's new value
	 * 
	 * @return new value
	 */
	public int getNewValue() {
		return newValue;
	}

}
