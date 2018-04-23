package ngp.search.core.api.query;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.search.Query;

import ngp.search.core.api.params.QueryParams;

public interface QueryBuilder {
	
	public Query buildQuery(PortletRequest portletRequest, QueryParams queryParams)
		throws Exception;
}
