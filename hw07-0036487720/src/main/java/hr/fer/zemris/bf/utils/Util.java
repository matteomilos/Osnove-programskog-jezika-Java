package hr.fer.zemris.bf.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;

/**
 * Class <code>Util</code> is used for storing methods that are used for some
 * type of processing boolean expression retrieved from the {@link Parser}
 * class.
 * 
 * @author Matteo Miloš
 *
 */
public class Util {

	/**
	 * This method is used for generating all the combinations of the values for
	 * the given list of variables (generating truth table). After generating
	 * each combination, instance of the {@link Consumer} class is called and
	 * given action is done.
	 * 
	 * @param variables
	 *            list of variables of the logical expression
	 * @param consumer
	 *            operation that will be performed
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		int length = variables.size();
		int rows = (int) Math.pow(2, length);
		boolean[] bol = new boolean[3];

		for (int i = 0; i < rows; i++) {
			for (int j = length - 1; j >= 0; j--) {
				bol[length - j - 1] = calculateBoolean(i, j);
			}
			consumer.accept(bol);
			bol = new boolean[length];
		}
	}

	/**
	 * Public method that calculates value of the expression for each possible
	 * combination of boolean values, and then return set of combinations whose
	 * value was equal to the given expressionValue parameter.
	 * 
	 * @param variables
	 *            list of variables of the logical expression
	 * @param expression
	 *            reference to the uppermost node of the logical expression
	 * @param expressionValue
	 *            value that expression must satisfy
	 * @return set parameterized by array of boolean values(each array
	 *         represents one combination of values)
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {

		ExpressionEvaluator eval = new ExpressionEvaluator(variables);
		Set<boolean[]> matchingSet = new LinkedHashSet<>();

		Util.forEach(variables, values -> {
			eval.setValues(values);
			expression.accept(eval);
			if (eval.getResult() == expressionValue) {
				matchingSet.add(values);
			}
		});

		return matchingSet;
	}

	/**
	 * Method used for 'translating' given array of values to the integer value.
	 * Method returns integer that represents given array of boolean values,
	 * e.g. [0, 1, 1] = 3.
	 * 
	 * @param values
	 *            array of boolean values
	 * @return integer value of boolean array
	 */
	public static int booleanArrayToInt(boolean[] values) {
		int number = 0;
		for (boolean value : values)
			number = (number << 1) | (value ? 1 : 0);
		return number;
	}

	/**
	 * Method used for getting set of integers that represent sum of minterms of
	 * the logical expression. Its work is delegated to the method
	 * {@link Util#termReturn(List, Node, boolean)}.
	 * 
	 * @param variables
	 *            list of variables of the logical expression
	 * @param expression
	 *            reference to the uppermost node of the logical expression
	 * @return set that represents sum of minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {

		return termReturn(variables, expression, true);

	}

	/**
	 * Method used for getting set of integers that represent product of
	 * maxterms of the logical expression. Its work is delegated to the method
	 * {@link Util#termReturn(List, Node, boolean)}.
	 * 
	 * @param variables
	 *            list of variables of the logical expression
	 * @param expression
	 *            reference to the uppermost node of the logical expression
	 * @return set that represents sum of maxterms
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {

		return termReturn(variables, expression, false);
	}

	/**
	 * Method that is called by {@link Util#toProductOfMaxterms(List, Node)} and
	 * {@link Util#toSumOfMinterms(List, Node)}, used for calculating either
	 * maxterms or minterms, based on given expressionValue.
	 * 
	 * @param variables
	 *            list of variables of the logical expression
	 * @param expression
	 *            reference to the uppermost node of the logical expression
	 * @param expressionValue
	 *            value that must be satisfied by the expression
	 * @return set that represents minterms/maxterms based on given
	 *         expressionValue
	 */
	private static Set<Integer> termReturn(List<String> variables, Node expression, boolean expressionValue) {

		Set<Integer> terms = new LinkedHashSet<>();
		for (boolean[] value : filterAssignments(variables, expression, expressionValue)) {
			terms.add(booleanArrayToInt(value));
		}
		return terms;
	}

	/**
	 * Method that is called from {@link Util#forEach(List, Consumer)}, used for
	 * calculating current boolean value, can be either <code>true</code> or
	 * <code>false</code>.
	 * 
	 * @param i
	 *            first parameter for calculating
	 * @param j
	 *            second parameter for calculating
	 * @return boolean value
	 */
	private static boolean calculateBoolean(int i, int j) {
		return ((i / (int) Math.pow(2, j)) % 2) == 1;
	}

}
