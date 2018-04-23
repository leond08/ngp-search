package ngp.search.core.portlet.action;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import ngp.search.core.api.NgpSearch;
import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.params.QueryParamsBuilder;
import ngp.search.core.constants.NgpSearchCorePortletKeys;
import ngp.search.core.constants.NgpSearchResourceKeys;


@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + NgpSearchCorePortletKeys.NgpSearchCore,
		"mvc.command.name=" + NgpSearchResourceKeys.GET_SEARCH_RESULTS
	},
	service = MVCResourceCommand.class
)
public class SearchResultsMvcResourceCommand extends BaseMVCResourceCommand {
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		// TODO Auto-generated method stub

			_log.debug("SearchResultsMvcResourceCommand.doServeResource");
		
		_log.info("Query:" + ParamUtil.getString(resourceRequest, "q"));
		
		JSONObject responseObject = null;
		
		// Build Query parameters Object
		
		QueryParams queryParams = null;
		

		try {
			queryParams = _queryParamsBuilder.buildQueryParams(
				resourceRequest);
			_log.info("Params: " + queryParams);
		}
		catch (Exception e) {
			_log.error(e, e);
			return;
		}
		
		// try to get search results
		try {
			
			responseObject = _ngpSearch.getSearchResults(
					resourceRequest, resourceResponse, queryParams);
			_log.info("ResponseObject:" + responseObject);
		}
		catch (PortalException e) {
			
			_log.error(e, e);
			return;
		}
		
		// Write response to output stream
		
		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, responseObject);
		
	}

	
	@Reference(unbind = "-")
	protected void setNgpSearch(NgpSearch ngpSearch) {

		_ngpSearch = ngpSearch;
	}

	@Reference(unbind = "-")
	protected void setQueryParamsBuilder(QueryParamsBuilder queryParamsBuilder) {

		_queryParamsBuilder = queryParamsBuilder;
	}

	@Reference
	protected NgpSearch _ngpSearch;

	@Reference
	protected QueryParamsBuilder _queryParamsBuilder;
	
	private static final Log _log =
			LogFactoryUtil.getLog(SearchResultsMvcResourceCommand.class);

}
