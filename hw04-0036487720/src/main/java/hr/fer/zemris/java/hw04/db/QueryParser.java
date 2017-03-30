package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents parses (syntax analyzer) of query commands.
 * 
 * @author Matteo Miloš
 */
public class QueryParser {

	/**
	 * Private instance variable that represents query that user wants
	 */
	private String query;

	/**
	 * List of all condition taken from query
	 */
	private List<ConditionalExpression> conditionalExpressionList;

	/**
	 * Public constructor that sets query to the given value. If user started
	 * his query with the keyword "query", it will be removed and to prepare
	 * query for parsing.
	 * 
	 * @param query
	 *            query that'll be parsed
	 * @throws IllegalArgumentException
	 *             if query given is null
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new IllegalArgumentException("Query given to parser can't be null");
		}

		this.query = query.trim();
		conditionalExpressionList = parse();
	}

	/**
	 * Private method used for parsing, is called in constructor when user
	 * enters query.
	 * 
	 * @return list of conditional expressions
	 */
	private List<ConditionalExpression> parse() {
		String[] array = query.split("\\s*[Aa][Nn][Dd]\\s*");/*-splitaj po and, case insensitive*/
		List<ConditionalExpression> list = new ArrayList<>();

		IFieldValueGetter fieldValue;
		IComparisonOperator operator;
		String literal;

		for (String helper : array) {
			this.query = helper;

			fieldValue = findFieldValue(query);
			operator = findOperator(query);
			literal = findLiteral();

			list.add(new ConditionalExpression(fieldValue, literal, operator));
		}
		return list;
	}

	/**
	 * Private method used for recognizing which operator is given in current
	 * expression.
	 * 
	 * @param string
	 *            current remain of query
	 * @return instance of {@linkplain IComparisonOperator}, representing an
	 *         operator
	 * @throws QueryParserException
	 *             in case of wrong operator
	 */
	private IComparisonOperator findOperator(String string) {
		IComparisonOperator operator;

		if (query.startsWith("LIKE")) {
			query = query.substring(4).trim();
			operator = ComparisonOperators.LIKE;

		} else if (query.charAt(0) == '<') {
			query = query.substring(1).trim();

			if (query.charAt(0) == '=') {
				query = query.substring(1).trim();
				operator = ComparisonOperators.LESS_OR_EQUALS;
			} else {
				operator = ComparisonOperators.LESS;
			}

		} else if (query.charAt(0) == '>') {
			query = query.substring(1).trim();

			if (query.charAt(0) == '=') {
				query = query.substring(1).trim();
				operator = ComparisonOperators.GREATER_OR_EQUALS;
			} else {
				operator = ComparisonOperators.GREATER;
			}

		} else if (query.charAt(0) == '=') {
			query = query.substring(1).trim();
			operator = ComparisonOperators.EQUALS;

		} else if (query.charAt(0) == '!' && query.charAt(1) == '=') {
			query = query.substring(2).trim();
			operator = ComparisonOperators.NOT_EQUALS;

		} else {
			throw new QueryParserException("Only allowed mathematical operators and 'LIKE'");
		}
		return operator;
	}

	/**
	 * Private method used for recognizing which field from student's record is
	 * wanted for examining.
	 * 
	 * @param string
	 *            current remain of query
	 * @return instance of {@linkplain IFieldValueGetter}, representing wanted
	 *         field
	 * @throws QueryParserException
	 *             in case of wrong field value
	 */
	private IFieldValueGetter findFieldValue(String string) {
		IFieldValueGetter fieldValue;

		if (query.startsWith("firstName")) {
			query = query.substring(9).trim();
			fieldValue = FieldValueGetters.FIRST_NAME;

		} else if (query.startsWith("lastName")) {
			query = query.substring(8).trim();
			fieldValue = FieldValueGetters.LAST_NAME;

		} else if (query.startsWith("jmbag")) {
			query = query.substring(5).trim();
			fieldValue = FieldValueGetters.JMBAG;

		} else {
			throw new QueryParserException("Wrong field value.");
		}

		return fieldValue;
	}

	/**
	 * Method used for getting literal from query.
	 * 
	 * @return literal of the query
	 * 
	 * @throws QueryParserException
	 *             in case of wrongly specified literal
	 * 
	 */
	private String findLiteral() {
		if (query.startsWith("\"") && query.endsWith("\"")) {
			return query.substring(1, query.length() - 1);
		}
		throw new QueryParserException("Wrong literal.");
	}

	/**
	 * Public method used for determining if user's query is direct.
	 * 
	 * @return <code>true</code> if query is direct, <code>false</code>
	 *         otherwise
	 */
	public boolean isDirectQuery() {
		if (query.matches("jmbag\\s*=\\s*\\\"[0-9]+\\\"")) {
			return true;
		}
		return false;
	}

	/**
	 * Public method for getting jmbag that is in direct query. If query isn't
	 * direct, exception will be thrown.
	 * 
	 * @return String representing student's jmbag without quotation marks
	 * @throws IllegalStateException
	 *             if query isn't direct
	 */
	public String getQueriedJMBAG() {

		if (isDirectQuery()) {
			String jmbag = query.split("=")[1].trim();
			return jmbag.substring(1, jmbag.length() - 1);
		}

		throw new IllegalStateException("Query wasn't direct.");
	}

	/**
	 * Public method that is used to get all conditional expressions from query
	 * to list. It splits given query by regular expression representing the
	 * word "and", case insensitive, and then parses each part. This is actual
	 * parsing of the query.
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return conditionalExpressionList;
	}

}
