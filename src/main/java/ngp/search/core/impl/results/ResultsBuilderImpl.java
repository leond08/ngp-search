package ngp.search.core.impl.results;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelper;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.results.ResultsBuilder;
import ngp.search.core.api.results.item.ResultsItemBuilder;
import ngp.search.core.api.results.item.ResultsItemBuilderFactory;
import ngp.search.core.impl.configuration.ModuleConfiguration;

@Component(
		immediate = true, 
		service = ResultsBuilder.class
)
public class ResultsBuilderImpl implements ResultsBuilder {

	@Override
	public JSONObject buildResult(PortletRequest portletRequest, PortletResponse portletResponse,
			QueryParams queryParams, SearchContext searchContext, Hits hits) {
		// TODO Auto-generated method stub
		
		_hits = hits;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
		_queryParams = queryParams;
		
		_resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", _queryParams.getLocale(),
			ResultsBuilderImpl.class);
		
		JSONObject resultsObject = JSONFactoryUtil.createJSONObject();
		
		long startTime = System.currentTimeMillis();
		
		// Create items array
		
		resultsObject.put("items", createItemsArray());
		
			_log.info(
				"Results processing took: " +
					(System.currentTimeMillis() - startTime));

		return resultsObject;
	}
	
	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {

		_moduleConfiguration = ConfigurableUtil.createConfigurable(
			ModuleConfiguration.class, properties);
	}
	
	
	/**
	 * Create array of result items as JSON array.
	 * 
	 * @return JSON array of result items
	 */
	protected JSONArray createItemsArray() {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Document[] docs = _hits.getDocs();

		if (_hits == null || docs.length == 0) {
			_log.info("No hits found");
			return jsonArray;
		}

		// Loop through search result documents and create the
		// JSON array of items to be delivered for UI

		for (int i = 0; i < docs.length; i++) {

			Document document = docs[i];
			_log.info(docs.length);

			try {

					_log.info(
						"#####################################################");

					_log.info("Score: " + _hits.getScores()[i]);


				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				// Get item type specific item result builder

				ResultsItemBuilder resultItemBuilder =
					_resultsBuilderFactory.getResultBuilder(
						_portletRequest, _portletResponse, document,
						_moduleConfiguration.assetPublisherPage());

				// Title

				jsonObject.put("title", resultItemBuilder.getTitle());
				_log.info("Title: " + resultItemBuilder.getTitle());

				// Date

				jsonObject.put("date", resultItemBuilder.getDate());
				_log.info("Date: " + resultItemBuilder.getDate());

				// Description

				jsonObject.put(
					"description", resultItemBuilder.getDescription());
				_log.info("Description: " + resultItemBuilder.getDescription());
				
				// Content Article
				
				jsonObject.put("content", resultItemBuilder.getContentArticle());
				_log.info("Content: " + resultItemBuilder.getContentArticle());

//				// Image src
//
//				if (_queryParams.getResultsLayout().equals(
//					GSearchResultsLayouts.THUMBNAIL_LIST) ||
//					_queryParams.getResultsLayout().equals(
//						GSearchResultsLayouts.IMAGE) &&
//						document.get(Field.ENTRY_CLASS_NAME).equals(
//							DLFileEntry.class.getName())) {
//
//					jsonObject.put("imageSrc", resultItemBuilder.getImageSrc());
//				}
//
//				// Type
//
//				jsonObject.put(
//					"type",
//					getLocalization(resultItemBuilder.getType().toLowerCase()));
//
//				// Link
//
				jsonObject.put("link", resultItemBuilder.getLink());
//
//				// Tags
//
				String[] tags = resultItemBuilder.getTags();

				if (tags != null && tags.length > 0 && tags[0].length() > 0) {

					jsonObject.put("tags", tags);
				}
//
//				// Additional metadata
//
//				jsonObject.put("metadata", resultItemBuilder.getMetadata());


				// Put single item to result array

				jsonArray.put(jsonObject);

			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return jsonArray;
	}
	
	@Reference(unbind = "-")
	protected void setResultsItemBuilderFactory(
			ResultsItemBuilderFactory resultsItemBuilderFactory) {

		_resultsBuilderFactory = resultsItemBuilderFactory;
	}
	
	protected Hits _hits;

	protected PortletRequest _portletRequest;

	protected PortletResponse _portletResponse;

	protected QueryParams _queryParams;

	protected ResourceBundle _resourceBundle;

	private volatile ModuleConfiguration _moduleConfiguration;

	private ResultsItemBuilderFactory _resultsBuilderFactory;


	private static final Log _log =
		LogFactoryUtil.getLog(ResultsBuilderImpl.class);
}
