package hr.fer.zemris.java.hw04.db;

public class ComparisonOperators {

	public static final IComparisonOperator LESS = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) < 0;
	};

	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) <= 0;
	};

	public static final IComparisonOperator GREATER = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) > 0;
	};

	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) >= 0;
	};

	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) == 0;
	};

	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return value1.compareTo(value2) != 0;
	};

	public static final IComparisonOperator LIKE = (value1, value2) -> {
		checkNull(value1, value2);
		int numOfWildcards = value2.length() - value2.replace("*", "").length();

		switch (numOfWildcards) {
		case 1:
			value2 = value2.replace("*", ".*");/*-namjerno nisam stavio break poslije ove linije jer nakon replacea na jednak
												  način provjeravamo jesu li stringovi jedan "LIKE" drugi		*/
		case 0:
			return value1.matches(value2);

		default:
			throw new IllegalArgumentException("Value mustn't contain more than one wildcard charachter.");
		}
	};

	private static void checkNull(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Value can't be null!");
		}
	}
}
