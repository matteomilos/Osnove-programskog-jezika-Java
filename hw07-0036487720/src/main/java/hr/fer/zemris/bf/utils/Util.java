package hr.fer.zemris.bf.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;

public class Util {

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

	public static int booleanArrayToInt(boolean[] values) {
		int number = 0;
		for (boolean value : values)
			number = (number << 1) | (value ? 1 : 0);
		return number;
	}

	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {

		return termReturn(variables, expression, true);

	}

	public static Set<Integer> toSumOfMaxterms(List<String> variables, Node expression) {

		return termReturn(variables, expression, false);
	}

	private static Set<Integer> termReturn(List<String> variables, Node expression, boolean expressionValue) {

		Set<Integer> terms = new LinkedHashSet<>();
		for (boolean[] value : filterAssignments(variables, expression, expressionValue)) {
			terms.add(booleanArrayToInt(value));
		}
		return terms;
	}

	private static boolean calculateBoolean(int i, int j) {
		return ((i / (int) Math.pow(2, j)) % 2) == 1;
	}

}
