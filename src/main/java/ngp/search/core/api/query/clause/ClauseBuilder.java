package ngp.search.core.api.query.clause;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Query;

import ngp.search.core.api.params.QueryParams;

/**
 * Query clause builder
 * 
 * Implementations of this build typed (Match, QueryStringQuery, etc)
 * @author falcon
 *
 */
public interface ClauseBuilder {
	
	/**
	 * Build clause
	 * 
	 * @param configurationObject
	 * @param queryParams
	 * @return
	 * @throws Exception
	 */
	public Query buildClause(
		JSONObject configurationObject, QueryParams queryParams)
		throws Exception;
	
	/**
	 * Check if this builder can build the requested query type
	 * 
	 * @param queryType
	 * @return
	 */
	public boolean canBuild(String queryType);
}
