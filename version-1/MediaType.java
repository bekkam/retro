/*
 * Enum MediaType contains the name of the media type and its corresponding fields,
 * so DemoModel can communicate such data to DemoView, for use in the latter's 
 * message prompts.
 */
public enum  MediaType {

	BOOK("Book", "title, author, price"),
	CD("CD", "album title, artist, year, price"),
	DVD("DVD", "title, year, rating, price");
	
	//private instance variables
	private String mediaTypeName;
	private String mediaTypeFields;
	
	MediaType(String mediaTypeName, String mediaTypeFields){
		this.mediaTypeName = mediaTypeName;
		this.mediaTypeFields = mediaTypeFields;
	}
	
	//accessors for fields
	public String getMediaTypeName() {
		return mediaTypeName;
	}

	public String getMediaTypeFields() {
		return mediaTypeFields;
	}
}
