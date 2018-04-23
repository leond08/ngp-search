package ngp.search.core.portlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import ngp.search.core.api.NgpSearch;
import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.params.QueryParamsBuilder;
import ngp.search.core.constants.NgpSearchCorePortletKeys;
import ngp.search.core.constants.NgpSearchResourceKeys;
import ngp.search.core.constants.NgpSearchWebKeys;

/**
 * @author falcon
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Liferay Ngp Search",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ngp-search-core",
		"javax.portlet.name=" + NgpSearchCorePortletKeys.NgpSearchCore,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NgpSearchCorePortlet extends MVCPortlet {
	
}