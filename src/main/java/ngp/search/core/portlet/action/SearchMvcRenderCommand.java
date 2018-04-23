package ngp.search.core.portlet.action;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import ngp.search.core.constants.NgpSearchCorePortletKeys;
import ngp.search.core.constants.NgpSearchResourceKeys;
import ngp.search.core.constants.NgpSearchWebKeys;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + NgpSearchCorePortletKeys.NgpSearchCore,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class SearchMvcRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		renderRequest.setAttribute(
				NgpSearchWebKeys.SEARCH_RESULTS_URL, 
				createResourceURL(renderResponse, NgpSearchResourceKeys.GET_SEARCH_RESULTS));
		
		setInitialParameters(renderRequest);
		
		// Set namespace (a convenience alias for $id).

		String portletNamespace = renderResponse.getNamespace();
		renderRequest.setAttribute(NgpSearchWebKeys.PORTLET_NAMESPACE, portletNamespace);
		
		return "/view.jsp";

	}
	
	/**
	 * Create resource URL for a resourceId
	 * 
	 * @param renderResponse
	 * @param resourceId
	 * 
	 * @return url string
	 */
	protected String createResourceURL(RenderResponse renderResponse, String resourceId) {

		ResourceURL portletURL = renderResponse.createResourceURL();

		portletURL.setResourceID(resourceId);

		return portletURL.toString();
	}
	
	protected void setInitialParameters(RenderRequest renderRequest) {
		
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		HttpServletRequest request = PortalUtil.getOriginalServletRequest(httpServletRequest);
		
		// Basic params
		
		String keywords = ParamUtil.getString(request, NgpSearchWebKeys.KEYWORDS);
		
		Map<String, String[]>initialParameters = new HashMap<String, String[]>();
		
		if (Validator.isNotNull(keywords)) {
			initialParameters.put(NgpSearchWebKeys.KEYWORDS, new String[]{keywords});
		}
		
		renderRequest.setAttribute(NgpSearchWebKeys.INITIAL_QUERY_PARAMETERS, initialParameters);
	}

}
