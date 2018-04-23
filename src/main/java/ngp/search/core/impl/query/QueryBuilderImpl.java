package ngp.search.core.impl.query;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.Validator;

import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.query.QueryBuilder;
import ngp.search.core.api.query.clause.ClauseBuilder;
import ngp.search.core.api.query.clause.ClauseBuilderFactory;
import ngp.search.core.impl.configuration.ModuleConfiguration;

/**
 * Query builder implementation
 * @author falcon
 *
 */
@Component(
	configurationPid = "ngp.search.core.configuration.NgpSearch",
	immediate = true,
	service = QueryBuilder.class
)
public class QueryBuilderImpl implements QueryBuilder {
	

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public BooleanQuery buildQuery(
		PortletRequest portletRequest, QueryParams queryParams) 
		throws Exception {
		
		// Build query
		
		BooleanQuery query = constructQuery(portletRequest, queryParams);
		
		return query;
	}
	
	
	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {

		_moduleConfiguration = ConfigurableUtil.createConfigurable(
			ModuleConfiguration.class, properties);
	}
	/**
	 * Construct the query
	 * 
	 * @param portletRequest
	 * @param queryParams
	 * @return
	 * @throws Exception
	 */
	protected BooleanQuery constructQuery(
		PortletRequest portletRequest, QueryParams queryParams)
		throws Exception {
		
		BooleanQuery query = new BooleanQueryImpl();
		
		// Build Query
		
		JSONArray configurationArray = JSONFactoryUtil.createJSONArray(
				_moduleConfiguration.searchFieldConfiguration());
		
		ClauseBuilder clauseBuilder;
		
		Query clause;
		
		for (int i = 0; i < configurationArray.length(); i++) {
			
			JSONObject queryItem = configurationArray.getJSONObject(i);
			String queryType = queryItem.getString("queryType");
			
			if (Validator.isNull(queryType)) {
				continue;
			}
			else {
				queryType = queryType.toLowerCase();
			}
			
			String occurString = queryItem.getString("occur");
			
			if (Validator.isNotNull(occurString)) {
				occurString = occurString.toLowerCase();
			}
			
			BooleanClauseOccur occur;
			if ("must".equalsIgnoreCase(occurString)) {
				occur = BooleanClauseOccur.MUST;
			}
			else if ("must_not".equalsIgnoreCase(occurString)) {
				occur = BooleanClauseOccur.MUST_NOT;
			}
			else {
				occur = BooleanClauseOccur.SHOULD;
			}
			
			// Get clause builder for the query type
			clauseBuilder = _clauseBuilderFactory.getClauseBuilder(queryType);
			
			if (clauseBuilder != null) {
				
				clause = clauseBuilder.buildClause(queryItem, queryParams);
				
				if (clause!=null) {
					query.add(clause, occur);
				}
			}
		}
		
		return query;
	}
	public static final DateFormat INDEX_DATE_FORMAT =
			new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Reference(unbind = "-")
	protected void setClauseBuilderFactory(
		ClauseBuilderFactory clauseBuilderFactory) {
		
		_clauseBuilderFactory = clauseBuilderFactory;
	}
	
	protected volatile ModuleConfiguration _moduleConfiguration;
	private ClauseBuilderFactory _clauseBuilderFactory;
}
