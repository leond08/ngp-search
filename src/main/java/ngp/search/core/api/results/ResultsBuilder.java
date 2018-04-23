package ngp.search.core.api.results;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;

import ngp.search.core.api.params.QueryParams;

public interface ResultsBuilder {
	
	public JSONObject buildResult(
			PortletRequest portletRequest, PortletResponse portletResponse,
			QueryParams queryParams, SearchContext searchContext,
			Hits hits);
}
