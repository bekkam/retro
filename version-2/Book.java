

import java.util.Properties;

/*
 * Class Book represents book inventory items.
 * Book extends InventoryItem and implements Maintainable method searchItem
 */
public class Book extends InventoryItem{
	
	//constructor taking Properties arg
	public Book (Properties props, String nameOfDatabaseFile, String headerForDatabase){
		super (props, nameOfDatabaseFile, headerForDatabase);
	}
		
	@Override  
	//METHOD TO SEARCH FOR BOOK IN INVENTORY
	public String searchItem(String searchString){
		String searchItemResult; 
		loadProperties();
		Object value = getPropertyTable().getProperty(searchString);
		//check if value is in table
		if (value != null)
			searchItemResult = ("The title, author, price of the book, '" + searchString 
						+ "' is " + value + "\n");
		else 
			searchItemResult = ("The book " + searchString + " is not in inventory\n");
		return searchItemResult;
	}		
}
