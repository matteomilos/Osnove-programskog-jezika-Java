package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;;

/**
 * Class <tt>ValueWrapper</tt> represents container which stores one object. If
 * object is of type <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs
 * to represent number, integer or double) and <tt>null</tt> value (will be
 * treated as zero), container can provide methods for basic arithmetic
 * operations.
 * 
 * @author Matteo Miloš
 */
public class ValueWrapper {

	/**
	 * Value stored in this wrapper.
	 */
	private Object value;

	/**
	 * Getter for value of this wrapper.
	 * 
	 * @return wrapper value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Public constructor which receives value to be stored as argument.
	 * 
	 * @param value
	 *            value to be stored

	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Method increments current value stored in wrapper for value passed as
	 * argument. Increment as arithmetic operation will be performed only if
	 * both current value stored and value passed are one of following types:
	 * <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs to represent
	 * number, integer or double) and <tt>null</tt> value (will be treated as
	 * zero).
	 * 
	 * @param incValue
	 *            value added to current value
	 */
	public void add(Object incValue) {
		performOperation((Number) this.value, determineValue(incValue),
				(v1, v2) -> v1.doubleValue() + v2.doubleValue());
	}

	/**
	 * Method decrements current value stored in wrapper for value passed as
	 * argument. Decrement as arithmetic operation will be performed only if
	 * both current value stored and value passed are one of following types:
	 * <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs to represent
	 * number, integer or double) and <tt>null</tt> value (will be treated as
	 * zero).
	 * 
	 * @param decValue
	 *            value subtracted from current value
	 */
	public void subtract(Object decValue) {
		performOperation((Number) this.value, determineValue(decValue),
				(v1, v2) -> v1.doubleValue() - v2.doubleValue());
	}

	/**
	 * Method multiplies current value stored in wrapper by value passed as
	 * argument. Multiplication as arithmetic operation will be performed only
	 * if both current value stored and value passed are one of following types:
	 * <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs to represent
	 * number, integer or double) and <tt>null</tt> value (will be treated as
	 * zero).
	 * 
	 * @param mulValue
	 *            multiplier of current value in wrapper
	 */
	public void multiply(Object mulValue) {
		performOperation((Number) this.value, determineValue(mulValue),
				(v1, v2) -> v1.doubleValue() * v2.doubleValue());
	}

	/**
	 * Method divides current value stored in wrapper by value passed as
	 * argument. Division as arithmetic operation will be performed only if both
	 * current value stored and value passed are one of following types:
	 * <tt>Double</tt>, <tt>Integer</tt>, <tt>String</tt> (needs to represent
	 * number, integer or double) and <tt>null</tt> value (will be treated as
	 * zero). Division by zero will result in an exception being thrown.
	 * 
	 * @param divValue
	 *            divider of current value in wrapper
	 */
	public void divide(Object divValue) {
		if (determineValue(divValue).doubleValue() == 0) {
			throw new ArithmeticException("Can't divide with zero.");
		}

		performOperation((Number) this.value, determineValue(divValue),
				(v1, v2) -> v1.doubleValue() / v2.doubleValue());
	}

	/**
	 * Method compares current value stored in wrapper to value passed as
	 * argument. Comparison will be performed only if both current value stored
	 * and value passed are one of following types: <tt>Double</tt>,
	 * <tt>Integer</tt>, <tt>String</tt> (needs to represent number, integer or
	 * double) and <tt>null</tt> value (will be treated as zero). If value
	 * stored is greater than value passed, result will be positive number. If
	 * value stored is smaller than value passed, result will be negative
	 * number. Otherwise, method returns zero.
	 * 
	 * @param withValue
	 *            value to be compared to
	 * @return 0 if numbers are equal, positive integer if first operand is
	 *         greater than second operand, negative integer if first operand is
	 *         smaller than second operand
	 */
	public int numCompare(Object withValue) {
		double firstOperator = ((Number) value).doubleValue();
		double secondOperator = determineValue(withValue).doubleValue();

		return Double.compare(firstOperator, secondOperator);
	}

	/**
	 * Performs arithmetic operation defined by {@link BiFunction} interface
	 * passed and stores the result, in appropriate format, in current
	 * {@link ValueWrapper}.
	 * 
	 * @param first
	 *            first operand
	 * @param second
	 *            second operand
	 * @param operation
	 *            arithmetic operation
	 */
	private void performOperation(Number first, Number second, BiFunction<Number, Number, Number> operation) {
		Number result = operation.apply(first, second);

		if (first instanceof Double || second instanceof Double) {
			this.value = new Double(result.doubleValue());

		} else {
			this.value = new Integer(result.intValue());
		}
	}

	/**
	 * Tries to parse the provided value as a double or an integer and returns
	 * its value as a number.
	 * 
	 * @param value
	 *            value to be parsed
	 * @return value as Integer or Double
	 */
	private Number determineValue(Object value) {
		if (value == null) {
			value = Integer.valueOf(0);

		} else if (value instanceof String) {
			String string = (String) value;

			try {
				if (string.contains(".") || string.contains("e") || string.contains("E")) {
					value = Double.parseDouble(string);

				} else {
					value = Integer.parseInt(string);

				}

			} catch (NumberFormatException exc) {
				throw new RuntimeException("Given string can't be parsed to number");
			}

		} else if (!(value instanceof Integer) && !(value instanceof Double)) {
			throw new RuntimeException("Given object can't be parsed to number");
		}

		return (Number) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * Setter for value of this wrapper.
	 * 
	 * @param value
	 *            value to be stored

	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
