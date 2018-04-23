package ngp.search.core.impl.query.clause;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.query.clause.ClauseBuilder;

/**
 * TermQuery clause builder service Implementations
 * 
 * @author falcon
 *
 */
@Component(
	immediate = true,
	service = ClauseBuilder.class
 )
public class TermQueryBuilderImpl implements ClauseBuilder {

	@Override
	public Query buildClause(
		JSONObject configurationObject, QueryParams queryParams) 
		throws Exception {
		
		String fieldName = configurationObject.getString("fieldName");
		
		
		if (fieldName == null) {
			return null;
		}
		
		// If there's a predefined value in the configuration, use that
		
		String value = configurationObject.getString("value");
		
		if (Validator.isNull(value)) {
				value = queryParams.getKeywords();
		}
		
		TermQuery termQuery = new TermQueryImpl(fieldName, value);
		
		// Boost
		
		float boost = 
				GetterUtil.getFloat(configurationObject.get("boost"), 1.0f);
		termQuery.setBoost(boost);
		
		return termQuery;
		
	}

	@Override
	public boolean canBuild(String queryType) {
		// TODO Auto-generated method stub
		return (queryType.equals(QUERY_TYPE));
	}
	
	private static final String QUERY_TYPE = "term";
	
}
