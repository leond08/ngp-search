package ngp.search.core.api.params;

public interface RequestParamValidator {
	
	/**
	 * Keywords validation
	 * 
	 * @param keywords
	 * @return
	 */
	public boolean validateKeywords(String keywords);

}
