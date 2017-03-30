package hr.fer.zemris.java.hw04.db;

import java.util.List;

/**
 * Class that implements interface {@linkplain IFilter} and represents filter of
 * student record using one or more conditions that are given through 'query'
 * command.
 * 
 * @author Matteo Miloš
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * List of the instances of the type {@linkplain ConditionalExpression},
	 * represents all of the conditions that student record has to satisfy.
	 */
	private List<ConditionalExpression> conditions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw04.db.IFilter#accepts(hr.fer.zemris.java.hw04.db.
	 * StudentRecord)
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression conditionalExpression : conditions) {
			if (!conditionalExpression.getComparisonOperator().satisfied(
					conditionalExpression.getFieldGetter().get(record), conditionalExpression.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Public constructor that gets list of the conditions that are needed to be
	 * satisfied by student record.
	 * 
	 * @param conditions
	 *            list of the conditions
	 */
	public QueryFilter(List<ConditionalExpression> conditions) {
		this.conditions = conditions;
	}

}
