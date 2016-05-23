
import java.util.Properties;
/*
 * Class CD represents CD items in inventory
 * CD extends InventoryItem and implements Maintainable method searchItem
 */

public class CD extends InventoryItem {//implements Maintainable{

	//constructor taking Properties arg
	public CD (Properties props, String nameOfDatabaseFile, String headerForDatabase){
		super (props, nameOfDatabaseFile, headerForDatabase);
	}

		@Override
		//METHOD TO SEARCH FOR CD IN INVENTORY
		public void searchItem(String searchString){
			loadProperties();
			Object value = getPropertyTable().getProperty(searchString);
			//check if value is in table
			if (value != null)
				System.out.printf("The album title, artist, year and price of the CD, '" + searchString 
						+ "' is %s\n", value + "\n");
			else
				System.out.println("The cd " + searchString + " is not in inventory\n");
		}
}
