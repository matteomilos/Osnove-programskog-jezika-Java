package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	private String query;

	private String string;

	public static void main(String[] args) {
		QueryParser qp1 = new QueryParser("query jmbag =\"0123456789\" ");
		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
		System.out.println("size: " + qp1.getQuery().size()); // 1
		QueryParser qp2 = new QueryParser("query jmbag=\"0123456789\" and lastName>\"J\"");
		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
		// System.out.println(qp2.getQueriedJMBAG()); // would throw!
		System.out.println("size: " + qp2.getQuery().size()); // 2
	}

	public QueryParser(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text given to parser can't be null");
		}

		if (text.trim().startsWith("query")) {
			this.query = text.trim().substring(5).trim();
		} else {
			this.query = text.trim();
		}
	}

	public boolean isDirectQuery() {
		if (query.matches("jmbag\\s*=\\s*\\\"[0-9]+\\\"")) {
			return true;
		}
		return false;
	}

	public String getQueriedJMBAG() {

		if (isDirectQuery()) {
			String jmbag = query.split("=")[1].trim();
			return jmbag.substring(1, jmbag.length() - 1);
		}

		throw new IllegalStateException("Query wasn't direct.");
	}

	public List<ConditionalExpression> getQuery() {
		String[] array = query.split("\\s*[Aa][Nn][Dd]\\s*");/*-splitaj po and, case insensitive*/
		List<ConditionalExpression> lista = new ArrayList<>();

		IFieldValueGetter fieldValue;
		IComparisonOperator operator;
		String literal;

		for (String helper : array) {
			this.string = helper;

			fieldValue = findFieldValue(string);
			operator = findOperator(string);
			literal = string.substring(1, string.length() - 1);

			lista.add(new ConditionalExpression(fieldValue, literal, operator));
		}

		return lista;
	}

	private IComparisonOperator findOperator(String string2) {
		IComparisonOperator operator;

		if (string.charAt(0) == 'L') {

			if (string.startsWith("LIKE")) {
				string = string.substring(4).trim();
				operator = ComparisonOperators.LIKE;

			} else {
				throw new IllegalArgumentException("Only allowed word operator is 'LIKE'");
			}

		} else if (string.charAt(0) == '<') {
			string = string.substring(1).trim();

			if (string.charAt(0) == '=') {
				string = string.substring(1).trim();
				operator = ComparisonOperators.LESS_OR_EQUALS;
			}

			operator = ComparisonOperators.LESS;

		} else if (string.charAt(0) == '>') {
			string = string.substring(1).trim();

			if (string.charAt(0) == '=') {
				string = string.substring(1).trim();
				operator = ComparisonOperators.GREATER_OR_EQUALS;
			}

			operator = ComparisonOperators.GREATER;

		} else if (string.charAt(0) == '=') {
			string = string.substring(1).trim();
			operator = ComparisonOperators.EQUALS;

		} else if (string.charAt(0) == '!' && string.charAt(1) == '=') {
			string = string.substring(2).trim();
			operator = ComparisonOperators.NOT_EQUALS;

		} else {
			throw new IllegalArgumentException("Only allowed mathematical operators and 'LIKE'");
		}
		return operator;
	}

	private IFieldValueGetter findFieldValue(String string2) {
		IFieldValueGetter fieldValue;

		if (string.startsWith("firstName")) {
			string = string.substring(9).trim();
			fieldValue = FieldValueGetters.FIRST_NAME;

		} else if (string.startsWith("lastName")) {
			string = string.substring(8).trim();
			fieldValue = FieldValueGetters.LAST_NAME;

		} else if (string.startsWith("jmbag")) {
			string = string.substring(5).trim();
			fieldValue = FieldValueGetters.JMBAG;

		} else {
			throw new QueryParserException("Wrong field value.");
		}

		return fieldValue;
	}

}
