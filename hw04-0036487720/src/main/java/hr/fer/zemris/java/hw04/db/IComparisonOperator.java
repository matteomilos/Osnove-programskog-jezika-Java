package hr.fer.zemris.java.hw04.db;

/**
 * Interface <code>IComparisonOperator</code> represents strategy which is used
 * to encapsulate condition for checking relations between two objects of type
 * <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public interface IComparisonOperator {

	/**
	 * Method that checks if condition for checking relations between two
	 * objects of type <code>String</code> is satisfied.
	 * 
	 * @param value1
	 *            first string
	 * @param value2
	 *            second string
	 * @return <code>true</code> if condition is satisfied, <code>false</code>
	 *         otherwise
	 */
	public boolean satisfied(String value1, String value2);

}
