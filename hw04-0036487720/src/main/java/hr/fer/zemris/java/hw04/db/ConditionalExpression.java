package hr.fer.zemris.java.hw04.db;

/**
 * CLass <code>ConditionalExpression</code> represents encapsulated condition
 * defined with three parameters: interface {@linkplain IFieldValueGetter},
 * interface {@linkplain IComparisonOperator} and object of type
 * <code>String</code>.
 * 
 * @author Matteo Miloš
 *
 */
public class ConditionalExpression {

	/**
	 * Item of student record that is being queried.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String against whom student record is queried.
	 */
	private String stringLiteral;
	/**
	 * Query operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Public constructor that gets three arguments, one field of
	 * {@linkplain StudentRecord}, one operator of type
	 * {@linkplain ComparisonOperators} and <code>String</code> against whom is
	 * student record queried.
	 * 
	 * @param fieldGetter
	 *            one field of student record
	 * @param stringLiteral
	 *            String against whom student record is queried
	 * @param comparisonOperator
	 *            Query operator
	 * @throws IllegalArgumentException
	 *             if either of the given arguments is null
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {

		if (fieldGetter == null || stringLiteral == null || comparisonOperator == null) {
			throw new IllegalArgumentException("Neither of the parameters can be null!");
		}

		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Public getter for the field of student record
	 * 
	 * @return field of the student record
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Public getter for string against whom student record is queried
	 * 
	 * @return string against whom student record is queried
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Public getter for comparison operator
	 * 
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
