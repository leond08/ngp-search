package ngp.search.core.impl.query.clause;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import fi.sovelita.liferay.gsearch.query.Operator;
import fi.sovelita.liferay.gsearch.query.QueryStringQuery;
import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.query.clause.ClauseBuilder;


@Component(
		immediate = true, 
		service = ClauseBuilder.class
)
public class QueryStringQueryBuilderImpl implements ClauseBuilder {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query buildClause(
		JSONObject configurationObject, QueryParams queryParams) 
		throws Exception {
		
		// If there's a predefined value in the configuration, use that
		
		String value = configurationObject.getString("value");
		
		if (Validator.isNull(value)) {
			
			StringBundler sb = new StringBundler();
			
			String prefix = configurationObject.getString("valuePrefix");
			if (Validator.isNotNull(prefix)) {
				sb.append(prefix);
			}
			
			sb.append(queryParams.getKeywords());
			
			String suffix = configurationObject.getString("valueSuffix");
			if (Validator.isNotNull(suffix)) {
				sb.append(suffix);
			}
			
			value = sb.toString();
		}
		
		QueryStringQuery queryStringQuery = new QueryStringQuery(value);
		
		JSONArray fields = configurationObject.getJSONArray("fields");
		
		for (int i = 0; i < fields.length(); i++) {
			
			JSONObject item = fields.getJSONObject(i);
			
			// Add non translated version
			
			String fieldName = item.getString("fieldName");
			float boost = GetterUtil.getFloat(item.get("boots"), 1f);
			
			queryStringQuery.addField(fieldName, boost);
			
			// Add translated version
			
			boolean isLocalized = 
				GetterUtil.getBoolean(item.get("localalized"),false);
			
			if (isLocalized) {
				
				String localizedFieldName = 
					fieldName + "_" + queryParams.getLocale().toString();
				float fuzziness =
					GetterUtil.getFloat(configurationObject.get("fuzzines"), 0.0f);
				queryStringQuery.setFuzziness(fuzziness);
			}
		}
		
		// Operator
		
		String operator = 
			GetterUtil.getString(configurationObject.get("operator"), "and");
		
		if (operator.equals("or")) {
			queryStringQuery.setDefaultOperator(Operator.OR);
		}
		else {
			queryStringQuery.setDefaultOperator(Operator.AND);
		}
		
		// Fuziness
		
		if (Validator.isNotNull(configurationObject.get("fuziness"))) {
			float fuziness =
				GetterUtil.getFloat(configurationObject.get("fuziness"), 0.0f);
		}
		
		// Boost
		
		float boost = 
			GetterUtil.getFloat(configurationObject.get("boost"), 1.0f);
		queryStringQuery.setBoost(boost);
		
		// Analyzer
		
		String analyzer = configurationObject.getString("analyzer");
		
		if (Validator.isNotNull(analyzer)) {
			queryStringQuery.setAnalyzer(analyzer);
		}
		
		return queryStringQuery;
	}



	@Override
	public boolean canBuild(String queryType) {
		// TODO Auto-generated method stub
		return (queryType.equals(QUERY_TYPE));
	}
	
	public static final String QUERY_TYPE = "query_string";
	
}
