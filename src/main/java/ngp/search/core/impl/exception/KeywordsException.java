package ngp.search.core.impl.exception;

import javax.portlet.PortletException;

public class KeywordsException extends PortletException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public KeywordsException() {
	}
	
	public KeywordsException(String msg) {
		super(msg);
	}
	
	public KeywordsException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public KeywordsException(Throwable cause) {
		super(cause);
	}

}
