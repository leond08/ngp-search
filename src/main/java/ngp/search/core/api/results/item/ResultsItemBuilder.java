package ngp.search.core.api.results.item;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.portal.kernel.search.Document;

/**
 * Asset type specific result item builder. Implementations of this class build
 * a single result item.
 * 
 * @author falcon
 *
 */
public interface ResultsItemBuilder {
	
	/**
	 * Check if this builder can build the requested type.
	 * 
	 * @param name
	 * @return
	 */
	public boolean canBuild(String name);
	
	/**
	 * Get item hit date
	 * 
	 * @return string representation of item date
	 * @throws Exception
	 */
	public String getDate()
		throws  Exception;
	
	/**
	 * Get item description
	 * 
	 * @return item desc
	 * @throws Exception
	 */
	public String getDescription()
		throws Exception;
	
	public String getContentArticle()
		throws Exception;
	
	/**
	 * Get item image src i.e. src attribute for img tag.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getImageSrc()
		throws Exception;
	
	/**
	 * Get item link
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getLink()
		throws Exception;
	
	/**
	 * Get item additional metadata
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getMetadata()
		throws Exception;
	
	/**
	 * Get item tags
	 * 
	 * @return
	 * @throws Exception
	 */
	public String[] getTags()
		throws Exception;
	
	/**
	 * Get item title
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTitle()
		throws Exception;
	
	/**
	 * Get item type
	 * 
	 * @return name of the item asset type
	 */
	public String getType()
		throws Exception;

	/**
	 * Set item builder properties.
	 * 
	 * @param portletRequest
	 * @param portletResponse
	 * @param document
	 *            search document
	 * @param assetPublisherFriendlyURL
	 *            friendly url of the page where there is an assetpublisher for
	 *            showing contents without any bound layout
	 */
	public void setProperties(
		PortletRequest portletRequest, PortletResponse portletResponse,
		Document document, String assetPublisherFriendlyURL);
}
