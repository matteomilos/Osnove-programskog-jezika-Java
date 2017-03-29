package hr.fer.zemris.java.hw04.db;

import java.text.Collator;
import java.util.Locale;

/**
 * Class <code>ComparisonOperators</code> gives us static methods for fetching
 * instances of the interface {@linkplain IComparisonOperator},
 * which represent finished encapsulated conditions for checking relations
 * between two strings.
 * 
 * @author Matteo Miloš
 *
 */
public class ComparisonOperators {

	/**
	 * Collator set to Croatian environment, for proper processing of special
	 * characters('č', 'ć', 'đ', 'š', 'ž' and their uppercase versions).
	 */
	public static Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("hr-HR"));

	/**
	 * Condition for checking if relation "value1 < value2" is satistified.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) < 0;
	};
	/**
	 * Condition for checking if relation "value1 <= value2" is satistified.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) <= 0;
	};
	/**
	 * Condition for checking if relation "value1 > value2" is satistified.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) > 0;
	};
	/**
	 * Condition for checking if relation "value1 >= value2" is satistified.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) >= 0;
	};
	/**
	 * Condition for checking if relation "value1 == value2" is satistified.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) == 0;
	};
	/**
	 * Condition for checking if relation "value1 != value2" is satistified.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		checkNull(value1, value2);
		return COLLATOR.compare(value1, value2) != 0;
	};

	/**
	 * Contidion for checking if first string is lexically "LIKE" other string,
	 * 'wildcard' symbol ('*') is allowed. Only one wildcard per string is
	 * allowed.
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		checkNull(value1, value2);
		int numOfWildcards = value2.length() - value2.replace("*", "").length();

		switch (numOfWildcards) {
		case 1:
			value2 = value2.replace("*", ".*");/*-namjerno nisam stavio break poslije ove linije 
												  jer nakon replacea na jednak način provjeravamo 
												  jesu li stringovi jedan "LIKE" drugi		*/
		case 0:
			return value1.matches(value2);

		default:
			throw new IllegalArgumentException("Value mustn't contain more than one wildcard charachter.");
		}
	};

	/**
	 * Method that is used for checking if either of the given strings are null.
	 * 
	 * @param value1
	 *            first string
	 * @param value2
	 *            second string
	 * @throws IllegalArgumentException
	 *             if either of the values is null.
	 */
	private static void checkNull(String value1, String value2) {
		if (value1 == null || value2 == null) {
			throw new IllegalArgumentException("Value can't be null!");
		}
	}
}
