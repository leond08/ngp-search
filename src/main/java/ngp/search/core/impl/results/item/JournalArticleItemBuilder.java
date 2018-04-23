package ngp.search.core.impl.results.item;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import ngp.search.core.api.results.item.ResultsItemBuilder;
import ngp.search.core.constants.NgpSearchWebKeys;

/**
 * JournalArticle Item type result builder
 * 
 * @author falcon
 *
 */

@Component(
		immediate = true,
		service = ResultsItemBuilder.class
)
public class JournalArticleItemBuilder extends BaseResultsItemBuilder
	implements ResultsItemBuilder {

	@Override
	public boolean canBuild(String name) {
		// TODO Auto-generated method stub
		return NAME.equals(name);
	}
	
	@Override
	public String getImageSrc()
		throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) _portletRequest.getAttribute(NgpSearchWebKeys.THEME_DISPLAY);
		
		return getJournalArticle().getArticleImageURL(themeDisplay);
	}
	
	@Override
	public String getContentArticle() 
		throws Exception {
		
		return getJournalArticle().getContent();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLink()
		throws Exception {

		String link = null;

		link = getAssetRenderer().getURLViewInContext(
			(LiferayPortletRequest) _portletRequest,
			(LiferayPortletResponse) _portletResponse, null);

		if (Validator.isNull(link)) {
			link = getNotLayoutBoundJournalArticleUrl(getJournalArticle());
		}

		return link;
	}
	
	protected JournalArticle getJournalArticle() 
		throws PortalException {
			
			return _journalArticleService.getLatestArticle(_entryClassPK);
	}
	

	@Reference(unbind = "-")
	protected void setJournalArticleService(
		JournalArticleService journalArticleService) {

		_journalArticleService = journalArticleService;
	}

	private static JournalArticleService _journalArticleService;
	private static final String NAME = JournalArticle.class.getName();

}
