package ngp.search.core.api;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.json.JSONObject;

import ngp.search.core.api.params.QueryParams;

public interface NgpSearch {
	
	public JSONObject getSearchResults(
			PortletRequest portletRequest, PortletResponse portletResponse,
			QueryParams queryParams)throws Exception;

}
