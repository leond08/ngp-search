package ngp.search.core.impl.params;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import ngp.search.core.api.params.QueryParams;
import ngp.search.core.api.params.QueryParamsBuilder;
import ngp.search.core.api.params.RequestParamValidator;
import ngp.search.core.constants.NgpSearchWebKeys;
import ngp.search.core.impl.exception.KeywordsException;

/**
 * 
 * @author falcon
 *
 */
@Component(
	immediate = true,
	service = QueryParamsBuilder.class
)
public class QueryParamsBuilderImpl implements QueryParamsBuilder {
	
	/**
	 * Build Parameter
	 */
	@Override
	public QueryParams buildQueryParams(PortletRequest portletRequest) 
			throws Exception {
		// TODO Auto-generated method stub
		_portletRequest = portletRequest;
		_queryParams = new QueryParams();
		
		setCompanyParam();
		setGroupsParam();
		setLocaleParam();
		setUserParam();
		
		setKeywordsParam();
		setStartEndParams();
//		setTimeParam();
		
		return _queryParams;
	}
	
	/**
	 * Set companyId Param
	 */
	protected void setCompanyParam() {
		
		ThemeDisplay themeDisplay = 
				(ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		_queryParams.setCompanyId(themeDisplay.getCompanyId());
	}
	
	/**
	 * Set groupdIds Param
	 */
	protected void setGroupsParam(){
		
		ThemeDisplay themeDisplay = 
				(ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		String scopeFilter = 
				ParamUtil.getString(_portletRequest, NgpSearchWebKeys.FILTER_SCOPE);
		
		long[] groupIds;
		
		if("this-site".equals(scopeFilter)){
			groupIds = new long[] {
					themeDisplay.getScopeGroupId()
			};
		}
		else {
			groupIds = new long[] {};
		}
		
		_log.info("GroupIds: " + groupIds);
		_queryParams.setGroupIds(groupIds);
	}
	
	/**
	 * Set Locale param
	 */
	protected void setLocaleParam(){
		
		ThemeDisplay themeDisplay = 
				(ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		_queryParams.setLocale(themeDisplay.getLocale());
		
	}
	
	/**
	 * Set Keywords
	 * 
	 * @throws KeywordsException
	 */
	protected void setKeywordsParam() 
			throws KeywordsException{
		
		String keywords = 
				ParamUtil.getString(_portletRequest, NgpSearchWebKeys.KEYWORDS);
		
		_log.info(_portletRequest);
		_log.info("Keywords: " + keywords);
		
		// Validate Keywords
		

		
		if(!_requestParamValidator.validateKeywords(keywords)){
			throw new KeywordsException();
		}
		
		_queryParams.setKeywords(keywords);

	}
	  
	/**
	 * Set start and end param
	 */
	protected void setStartEndParams(){
		
		int start =
			ParamUtil.getInteger(_portletRequest, NgpSearchWebKeys.START, 0);
		_queryParams.setStart(start);
		_queryParams.setEnd(start + PAGE_SIZE);
	}
	
	/**
	 * Set time parameter (modification date between).
	 */
	protected void setTimeParam(){
		
		String timeFilter = 
			ParamUtil.getString(_portletRequest, NgpSearchWebKeys.FILTER_TIME);
		
		Date timeFrom = null;
		
		if ("last-day".equals(timeFilter)) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			timeFrom = calendar.getTime();
		}
		else if ("last-week".equals(timeFilter)) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.WEEK_OF_MONTH, -7);
			timeFrom= calendar.getTime();
		}
		else if ("last-month".equals(timeFilter)) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			timeFrom = calendar.getTime();
		}
		
		if (timeFrom != null) {
			_queryParams.setTimeFrom(timeFrom);
		}
	}
	
	/**
	 * Set user parameter
	 */
	protected void setUserParam(){
		
		ThemeDisplay themeDisplay = 
			(ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		_queryParams.setUserId(themeDisplay.getUserId());
	}
	
	@Reference(unbind = "-")
	protected void setRequestParamValidator(
		RequestParamValidator requestParamValidator) {

		_requestParamValidator = requestParamValidator;
	}
	
	
	private PortletRequest _portletRequest;
	private QueryParams _queryParams;
	private RequestParamValidator _requestParamValidator;
	public static final int PAGE_SIZE = 10;
	private static final Log _log = LogFactoryUtil.getLog(QueryParamsBuilderImpl.class);
}
