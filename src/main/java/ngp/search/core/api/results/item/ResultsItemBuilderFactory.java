package ngp.search.core.api.results.item;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.search.Document;

/**
 * Result item builder factory interface. Implementations of this service
 * returns an asset type specific result item builder.
 * 
 * @author falcon
 *
 */
public interface ResultsItemBuilderFactory {
	
	/**
	 * Get result builder
	 * 
	 * @param portletRequest
	 * @param portletResponse
	 * @param document
	 * @param assetPublisherPageFriendlyURL
	 * @return
	 */
	public ResultsItemBuilder getResultBuilder(
		PortletRequest portletRequest,
		PortletResponse portletResponse, Document document, String assetPublisherPageFriendlyURL
	);
}
