package hr.fer.zemris.java.hw04.db;

/**
 * Interface <code>IFilter</code> represents strategy which is used for querying
 * student records.
 * 
 * @author Matteo Miloš
 *
 */
public interface IFilter {

	/**
	 * Method that executes querying student record using implemented condition.
	 * 
	 * @param record
	 *            student record
	 * @return <code>true</code> if condition is accepted, <code>false</code>
	 *         otherwise
	 */
	public boolean accepts(StudentRecord record);
}
