package ngp.search.core.api.params;

import javax.portlet.PortletRequest;

public interface QueryParamsBuilder {
	
	public QueryParams buildQueryParams(
			PortletRequest portletRequest)
					throws Exception;
}
