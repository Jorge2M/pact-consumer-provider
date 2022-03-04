package au.com.dius.pactworkshop.consumer;

public class FiltersProduct {

	private final String countryId;
	private final String languageId;
	private final String channelId;
	
	public FiltersProduct(String countryId, String languageId, String channelId) {
		this.countryId = countryId;
		this.languageId = languageId;
		this.channelId = channelId;
	}
	
	public String getCountryId() {
		return countryId;
	}
	public String getLanguageId() {
		return languageId;
	}
	public String getChannelId() {
		return channelId;
	}
	
	public String getQueryParams() {
		return "countryId=" + countryId + "&languageId=" + languageId + "&channelId=" + channelId;
	}
}
