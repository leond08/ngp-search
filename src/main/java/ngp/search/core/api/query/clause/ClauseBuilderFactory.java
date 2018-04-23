package ngp.search.core.api.query.clause;

public interface ClauseBuilderFactory {
	
	/**
	 * Clause builder factory. This service return a clause builder
	 * of the query type (Match, QueryStringQuery, etc)
	 * 
	 * @param queryType
	 * @return ClauseBuilder
	 */
	public ClauseBuilder getClauseBuilder(String queryType);
}
