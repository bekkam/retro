import java.util.Properties;
/*
 * Class Dvd represents CD items in inventory
 * Dvd extends InventoryItem and implements Maintainable method searchItem
 */
public class Dvd extends InventoryItem{

	//constructor taking Properties arg
	public Dvd (Properties props, String nameOfDatabaseFile, String headerForDatabase){
		super (props, nameOfDatabaseFile, headerForDatabase);
	}
	
	@Override 
	//implement search method of Maintainable interface
	//METHOD TO SEARCH FOR DVD IN INVENTORY
	public void searchItem(String searchString){
		loadProperties();
		Object value = getPropertyTable().getProperty(searchString);
		//check if value is in table
		if (value != null)
			System.out.printf("The title, year, rating, and price of the dvd, '" + searchString 
					+ "' is %s\n", value + "\n");
		else
			System.out.println("The dvd " + searchString + " is not in inventory\n");
	}
}
