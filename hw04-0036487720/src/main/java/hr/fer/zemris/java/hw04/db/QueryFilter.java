package hr.fer.zemris.java.hw04.db;

import java.util.List;

public class QueryFilter implements IFilter {
	private List<ConditionalExpression> list;

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : list) {
			if (!conditionalExpression.getComparisonOperator().satisfied(
					conditionalExpression.getFieldGetter().get(record), conditionalExpression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

}
