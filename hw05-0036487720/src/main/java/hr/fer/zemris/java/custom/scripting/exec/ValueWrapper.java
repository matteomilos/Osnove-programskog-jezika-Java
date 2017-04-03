package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;;

public class ValueWrapper {

	private Object value;

	public Object getValue() {
		return value;
	}

	public ValueWrapper(Object value) {
		if (!(value instanceof Double || value instanceof String || value instanceof Integer || value == null)) {
			throw new IllegalArgumentException("Value is invalid.");
		}

		this.value = determineValue(value);
	}

	public void add(Object incValue) {
		performOperation((Number) this.value, determineValue(incValue),
				(v1, v2) -> v1.doubleValue() + v2.doubleValue());
	}

	public void subtract(Object decValue) {
		performOperation((Number) this.value, determineValue(decValue),
				(v1, v2) -> v1.doubleValue() - v2.doubleValue());
	}

	public void multiply(Object mulValue) {
		performOperation((Number) this.value, determineValue(mulValue),
				(v1, v2) -> v1.doubleValue() * v2.doubleValue());
	}

	public void divide(Object divValue) {
		if (determineValue(divValue).doubleValue() == 0) {
			throw new ArithmeticException("Can't divide with zero.");
		}

		performOperation((Number) this.value, determineValue(divValue),
				(v1, v2) -> v1.doubleValue() / v2.doubleValue());
	}

	public int numCompare(Object withValue) {
		double firstOperator = ((Number) value).doubleValue();
		double secondOperator = determineValue(withValue).doubleValue();

		return Double.compare(firstOperator, secondOperator);
	}

	private void performOperation(Number first, Number second, BiFunction<Number, Number, Number> operation) {
		Number result = operation.apply(first, second);

		if (first instanceof Double || second instanceof Double) {
			this.value = new Double(result.doubleValue());

		} else {
			this.value = new Integer(result.intValue());
		}
	}

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

	public void setValue(Number number) {
		this.value = number;
	}
}
