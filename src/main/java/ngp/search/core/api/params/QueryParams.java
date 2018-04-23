package ngp.search.core.api.params;

import java.util.Date;
import java.util.Locale;

/**
 * QueryParams POJO
 * @author falcon
 *
 */
public class QueryParams {
	
	private long companyId;
	private long[] groupIds;
	private Locale locale;
	private int start;
	private int end;
	private Date timeFrom = null;
	private Date timeTo = null;
	long userId;
	
	private String keywords;
	
	public long getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(long companyId){
		this.companyId = companyId;
	}
	
	public long[] getGroupIds(){
		return groupIds;
	}
	
	public void setGroupIds(long[] groupIds){
		this.groupIds = groupIds;
	}
	
	public Locale getLocale(){
		return locale;
	}
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	public int getStart(){
		return start;
	}
	
	public void setStart(int start){
		this.start = start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public String getKeywords(){
		return keywords;
	}
	
	public void setKeywords(String keywords){
		this.keywords = keywords;
	}
	
	public Date getTimeFrom() {
		return timeFrom;
	}
	
	public void setTimeFrom(Date timeFrom) {
		this.timeFrom = timeFrom;
	}
	
	public Date getTimeTo() {

		return timeTo;
	}

	public void setTimeTo(Date timeTo) {

		this.timeTo = timeTo;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
