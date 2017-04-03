package hr.fer.zemris.java.hw05.observer1;

/**
 * Interface <code>IntegerStorageObserver</code> describes observer from
 * observer pattern, object which is subscribed to subject and can be informed
 * about subject's internal state changes. Classes which implement this
 * interface define observers which are subscribed to more specific subject.
 * 
 * @author Matteo Miloš
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method which is called by subject over it's observer's references to
	 * inform them automatically of any state changes.
	 * 
	 * @param istorage
	 *            reference to subject
	 */
	public void valueChanged(IntegerStorage istorage);

}