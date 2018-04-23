package ngp.search.core.impl;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import ngp.search.core.api.NgpSearch;
import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.query.QueryBuilder;
import ngp.search.core.api.results.ResultsBuilder;

/**
 * 
 * @author falcon
 *
 */
@Component(
	immediate = true,
	service = NgpSearch.class
)
public class NgpSearchImpl implements NgpSearch {
	
	/* Get Search Results
	 * 
	 * 
	 */
	@Override
public JSONObject getSearchResults(PortletRequest portletRequest, PortletResponse portletResponse,
			QueryParams queryParams)
			throws Exception {
		// TODO Auto-generated method stub
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_queryParams = queryParams;

		return getResults();
	}
	
	
	@SuppressWarnings("unchecked")
	protected Hits execute(SearchContext searchContext, Query query)
		throws Exception  {
		
		
			_log.info("Executing search with query: " + query.toString());
	
		BooleanClause<?> booleanClause = BooleanClauseFactoryUtil.create(query, 
				BooleanClauseOccur.MUST.getName());
		
		_log.info(booleanClause);
		
		searchContext.setBooleanClauses(new BooleanClause[] {
				booleanClause
		});
		
		String queryString = _indexSearcherHelper.getQueryString(searchContext, query);
		_log.info("Query: " + queryString);
		
		Hits hits = _indexSearcherHelper.search(searchContext, query);
		
		
			_log.info("Query: " + hits.getQuery());
			_log.info("Hits: " + hits.getLength());
			_log.info("Returned: " + hits.getDocs().length);
			_log.info("Time:" + hits.getSearchTime());
		
		return hits;
	}
	
	protected JSONObject getResults()
		throws Exception {
		
		Query query = _queryBuilder.buildQuery(_portletRequest, _queryParams);
		
		// Create SearchContext
		
		SearchContext searchContext = getSearchContext();
		
		// Execute search
		
		Hits hits = execute(searchContext, query);
		
		// Build results JSON Object
		
		JSONObject resultsObject = _resultsBuilder.buildResult(_portletRequest, 
				_portletResponse, _queryParams, 
				searchContext, hits);
		
		return resultsObject;
	}
	
	
	
	protected SearchContext getSearchContext()
		throws Exception {
		
		ThemeDisplay themeDisplay = 
				(ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		SearchContext searchContext = new SearchContext();
		searchContext.setCompanyId(themeDisplay.getCompanyId());
		searchContext.setStart(_queryParams.getStart());
		searchContext.setEnd(_queryParams.getEnd());
		
		_log.info("Search context start: " + searchContext.getStart());
		_log.info("Search context end: " + searchContext.getEnd());
		_log.info("Search context companyId: " + searchContext.getCompanyId());


		return searchContext;
	}
	
	@Reference(unbind = "-")
	protected void setIndexSearchHelper(
		IndexSearcherHelper indexSearcherHelper) {

		_indexSearcherHelper = indexSearcherHelper;
	}

	@Reference(unbind = "-")
	protected void setQueryBuilder(QueryBuilder queryBuilder) {

		_queryBuilder = queryBuilder;
	}

	@Reference(unbind = "-")
	protected void setResultsBuilder(ResultsBuilder resultsBuilder) {

		_resultsBuilder = resultsBuilder;
	}
	
	private PortletRequest _portletRequest;
	private PortletResponse _portletResponse;
	private QueryParams _queryParams;
	private QueryBuilder _queryBuilder;
	private IndexSearcherHelper _indexSearcherHelper;
	private ResultsBuilder _resultsBuilder;
	
	private static final Log _log = LogFactoryUtil.getLog(NgpSearchImpl.class);

}
