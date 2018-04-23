package ngp.search.core.impl.params;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.util.Validator;

import ngp.search.core.api.params.RequestParamValidator;


@Component(
	immediate = true,
	service = RequestParamValidator.class
)
public class RequestParamValidatorImpl implements RequestParamValidator {
	
	/**
	 * 
	 */
	@Override
	public boolean validateKeywords(String keywords) {
		// TODO Auto-generated method stub
		if (Validator.isNotNull(keywords) && 
			keywords.length() <= KEYWORDS_MAX_LENGTH) {
			return true;
		}
		return false;
	}
	
	// Maximum length of keywords.
	
	public static final int KEYWORDS_MAX_LENGTH = 100;

}
