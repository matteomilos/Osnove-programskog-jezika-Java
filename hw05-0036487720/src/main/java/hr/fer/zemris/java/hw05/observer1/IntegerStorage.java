package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class <code>IntegerStorage</code> represents implementation of subject from
 * observer pattern. Subject is used as a storage container for integer value.
 * 
 * @author Matteo Miloš
 *
 */
public class IntegerStorage {

	/**
	 * Value stored.
	 */
	private int value;

	/**
	 * List of observers that are subscribed to this subject.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Public constructor that sets value to the given initial value and
	 * instantiates list of observers as an <code>ArrayList</code>.
	 * 
	 * @param initialValue
	 *            initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Public method that adds observer to subject's list of observers. Observes
	 * is added only if it is different from null and list of observers doesnt't
	 * already contain that instance of observer.
	 * 
	 * @param observer
	 *            observer to be added to the list.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observer != null && !observers.contains(observer)) {
			observers = new ArrayList<>(observers); // čupićev hint
			observers.add(observer);
		}
	}

	/**
	 * Public method that removes observer from subject's list of observers.
	 * Observes is remove only if it is different from null and list of
	 * observers already contains that instance of observer.
	 * 
	 * @param observer
	 *            observer to be removed from the list.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observer != null && observers.contains(observer)) {
			observers = new ArrayList<>(observers); // čupićev hint
			observers.remove(observer);
		}
	}

	/**
	 * Public method that removes all observers from subject's list of
	 * observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Public method, getter for subject's current value.
	 * 
	 * @return current stored value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Public method, sets subject's value to the new value. All observers that
	 * are subscribed to this subject(that are in the list of observers) are
	 * informed about the change of the stored value.
	 * 
	 * @param value
	 *            new value to be set
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;

			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}