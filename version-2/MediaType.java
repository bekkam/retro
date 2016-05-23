
/*
 * Enum MediaType contains the name of the media type and its corresponding fields,
 * so DemoModel can communicate such data to DemoView, for use in the latter's 
 * message prompts.
 */
public enum  MediaType {
	//declare constants of enum type
	BOOK("Book", "title", "author", "price", " "),
	CD("CD", "album title", "artist", "year", "price"),
	DVD("DVD", "title", "year", "rating", "price");

	
	//private instance variables
	private final String mediaTypeName;

	private final String fieldOne;  
	private  final String fieldTwo;
	private final String fieldThree;
	private final String fieldFour;
	
	MediaType(String mediaTypeName, String fieldOne, String fieldTwo, String fieldThree, String fieldFour){
		this.mediaTypeName = mediaTypeName;
		this.fieldOne = fieldOne;
		this.fieldTwo = fieldTwo;
		this.fieldThree = fieldThree;
		this.fieldFour = fieldFour;
	}
	
	//accessors for fields
	public String getMediaTypeName() {
		return mediaTypeName;
	}
	
	public String[] getFieldArray(){
		String[] fieldArray  = {fieldOne, fieldTwo, fieldThree, fieldFour}; 
		return fieldArray;
	}
	
/*	public String getField(int i){
		return fieldArray[i];
	}
	public String getFieldOne(){
		return fieldOne;
	}
	public String getFieldTwo(){
		return fieldTwo;
	}
	public String getFieldThree(){
		return fieldThree;
	}
	public String getFieldFour(){
		return fieldFour;
	}*/
}
