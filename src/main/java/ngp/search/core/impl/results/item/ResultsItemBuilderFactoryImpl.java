package ngp.search.core.impl.results.item;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;

import aQute.bnd.differ.Baseline.Info;
import ngp.search.core.api.results.item.ResultsItemBuilder;
import ngp.search.core.api.results.item.ResultsItemBuilderFactory;

/**
 * Result item builder factory implementation
 * 
 * @author falcon
 *
 */
@Component(
		immediate = true, 
		service = ResultsItemBuilderFactory.class
)
public class ResultsItemBuilderFactoryImpl implements ResultsItemBuilderFactory {

	
	@Override
	public ResultsItemBuilder getResultBuilder(PortletRequest portletRequest, PortletResponse portletResponse,
			Document document, String assetPublisherPageFriendlyURL) {
		
		String entryClassName = document.get(Field.ENTRY_CLASS_NAME);
		
		_log.info("Entry_class_name: " + entryClassName);
		
		ResultsItemBuilder resultsItemBuilder = null;
		

		for (ResultsItemBuilder r : _resultsItemBuilders) {
			if (r.canBuild(entryClassName)) {
				resultsItemBuilder = r;

				break;
			}
		}
		
		if (resultsItemBuilder == null) {
			_log.info("No result item builder found for " + entryClassName);
		}
		
		resultsItemBuilder.setProperties(
				portletRequest, portletResponse, 
				document, assetPublisherPageFriendlyURL);
		
		return resultsItemBuilder;
	}
	
	/**
	 * Add result item builder to the list.
	 * 
	 * @param clauseBuilder
	 */
	protected void addResultItemBuilder(ResultsItemBuilder resultItemBuilder) {

		if (_resultsItemBuilders == null) {
			_resultsItemBuilders = new ArrayList<ResultsItemBuilder>();
		}
		_resultsItemBuilders.add(resultItemBuilder);
	}

	/**
	 * Remove a clause builder from list.
	 * 
	 * @param clauseBuilder
	 */
	protected void removeResultItemBuilder(
		ResultsItemBuilder resultItemBuilder) {

		_resultsItemBuilders.remove(resultItemBuilder);
	}

	@Reference(
		bind = "addResultItemBuilder", 
		cardinality = ReferenceCardinality.MULTIPLE, 
		policy = ReferencePolicy.DYNAMIC, 
		service = ResultsItemBuilder.class,
		unbind = "removeResultItemBuilder"
	)
	private volatile List<ResultsItemBuilder> _resultsItemBuilders;
	private static final Log _log =
			LogFactoryUtil.getLog(ResultsItemBuilderFactoryImpl.class);
}
