import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
/*
 * Abstract class InventoryItem implements interface Maintainable methods
 * addItem, editItem, and deleteItem
 * Includes helper methods for manipulating strings and loading properties
 */

public abstract class InventoryItem implements Maintainable{
	
	//database will be maintained with Properties class
	private Properties props;
	//private variables to manipulate strings
	private int numberOfFields;
	private int endIndexNum;
	private int startIndexNum;
	private int fieldOneDelimiter;
	
	//private variables for store and load methods
	private String nameOfDatabaseFile;
	private String headerForDatabase;
	
	//constructor
	public InventoryItem(Properties props, String nameOfDatabaseFile, String headerForDatabase){
		this.props = props;
		this.nameOfDatabaseFile = nameOfDatabaseFile;
		this.headerForDatabase = headerForDatabase;
	}
	//setters and getters
	public void setPropertyTable(Properties props){
		this.props = props;
	}
	
	public Properties getPropertyTable(){
		return props;
	}

	public void setNameOfDatabaseFile(String nameOfDatabaseFile){
		this.nameOfDatabaseFile = nameOfDatabaseFile;
	}
	
	public String getNameOfDatabaseFile(){
		return nameOfDatabaseFile;
	}
	
	public void setHeaderForDatabase(String headerForDatabase){
		this.headerForDatabase = headerForDatabase;
	}
	
	public String getHeaderForDatabase(){
		return headerForDatabase;
	}
	

	/*
	 * Methods inherited by subclasses
	 */
	public void listProperties( )
	{
		System.out.println("* Current List *");
		Set< Object > keys = props.keySet(); // get property names
	    // output name/value pairs
	    for ( Object key : keys )
	       System.out.printf( 
	          "%s\t%s\n", key, props.getProperty( ( String ) key ) );
	      System.out.println();
	   } // end method listProperties
	   
	   public void saveProperties()
	   {
	      // save contents of table
	      try
	      {
	    	 FileOutputStream output = new FileOutputStream( getNameOfDatabaseFile() );
	    	 getPropertyTable().store( output, getHeaderForDatabase() ); // save properties
	         output.close();
	         //System.out.println( "After saving Book Inventory" );
	         //listProperties( props ); // display property values
	      } // end try
	      catch ( IOException ioException )
	      {
	         ioException.printStackTrace();
	      } // end catch
	   } // end method saveProperties
	
	   public void loadProperties( )
	   {
	      // load contents of table
	      try
	      {
	         FileInputStream input = new FileInputStream( getNameOfDatabaseFile() );
	         getPropertyTable().load( input ); // load properties
	         input.close();
	         //System.out.println( "After loading CD Inventory");
	         //listProperties( props ); // display property values
	      } // end try
	      catch ( IOException ioException )
	      {
	         ioException.printStackTrace();
	      } // end catch
	   } // end method loadProperties
	
	   @Override 
		//implements interface Maintainable method getItems to load the current objects properties
	   public void getItems(){
			loadProperties();
			listProperties();
	   }

		@Override
		//implements interface Maintainable method addItem to add itemString to inventory 
		   public void addItem(String itemString){
		  		loadProperties();
				 String stringWithSlashes = "\\" + itemString + "\\";
				//append slashes to beginning and end in order to manipulate fields with stringbuilder
				 getPropertyTable().setProperty(getFirstField(stringWithSlashes), stringWithSlashes);
		  		//System.out.println("getValue is " + getValue());
		  		//getPropertyTable().setProperty(getFirstField(bookString), bookString);
		  		saveProperties();
		  		System.out.println("Item added to inventory\n");
		  		//System.out.println("After adding entry: " + itemString);
		  		//listProperties(); // display property values
		   }
		
	/*
	 * helper method to parse first field of user string input, using "\" as delimiter; 
	 * returns a string that will be used as key for properties 
	 */
	public String getFirstField(String someString){
	    StringBuilder builder = new StringBuilder(someString); 
	 	fieldOneDelimiter = builder.indexOf("\\", 1);//get index of first occurrence of "\", starting at 
	 	//1, to avoid taking delimiter of 0 
	 	String fieldOne = builder.substring(1, fieldOneDelimiter);
	 	return fieldOne;
	   }
	
	//method to count how many fields a given string contains, for help in manipulating
	//string builders
	   public void setNumberOfFields(String stringEntry){
		   int lastIndex = 0;
		   int count = 0;
		   String findDelimiter = "\\";
		   while (lastIndex != -1){
		       lastIndex = stringEntry.indexOf(findDelimiter,lastIndex);

		       if( lastIndex != -1){
		             count ++;
		             lastIndex+=findDelimiter.length();
		      }
		}
		   numberOfFields = count -1; 
	   }
	   //returns the number of fields, delimited by "\", of a given string
	   public int getNumberOfFields(){
		   return numberOfFields;
	   }
	    
	   //method to set the ending index number for a given field in a given string
	   public void setEndIndex(int fieldNumberToEdit, String stringEntry){
		   int previousIndex = 0;
		   String findDelimiter = "\\";
		   int fieldLength;
		   setNumberOfFields(stringEntry);
		   //if the field to be edited is the last field, end index is length of string
		   //less one (to maintain last character = "\")
		   if (fieldNumberToEdit == getNumberOfFields()){
			   endIndexNum = stringEntry.length() -1;
		   }
		   else{
			   for (int index = 0; index <fieldNumberToEdit; index++ ){
				   {
					   fieldLength = stringEntry.indexOf(findDelimiter, previousIndex+1 );
					   previousIndex = fieldLength+1;
				   } 
			   endIndexNum= fieldLength;
			   //System.out.println("endIndexNum in Books setEndIndex() = " + endIndexNum +"\n"); - debugging
			   }//end for loop
		   }//end else
	   }//end method setEndIndex
	   
	   //method to get the ending index number for a given field, in a given string
	   public int getEndIndex(){
		   return endIndexNum;
	   }
	
	   //method to set the starting index number for a given field, in a given string
	   //for use in manipulating stringbuilders
	   public void setStartIndex(int fieldNumberToEdit, String stringEntry){
		   int previousStartIndex = 0;
		   String findDelimiter = "\\";
		   int fieldLength;
		   setNumberOfFields(stringEntry);
		   //if the field to be edited is the first field, start index is 1(to maintain "/" 
		   //as the first character of the string
		   if (fieldNumberToEdit ==1 ){
			   startIndexNum = 1;
		   }
		   else{
			   for (int index = 0; index <fieldNumberToEdit; index++ ){
				   {
					   fieldLength = stringEntry.indexOf(findDelimiter, previousStartIndex );
					   previousStartIndex = fieldLength+1;
				   } 
				   startIndexNum= fieldLength +1;
			   //System.out.println("startIndexNum in Books setStartIndex() = " + startIndexNum +"\n"); - debugging
			   }//end for loop
		   }//end else
	   }//setStartIndex () method
	
	   //method to get the starting index number for a given field, in a given string
	   //for use in manipulating stringbuilders
	   public int getStartIndex(){
		   return startIndexNum;
	   }
	   
	   @Override
	   //method implements interface method editItem
		//which updates a given field of a string, with a given value,
	   //and saves the updated string to inventory
	   public void editItem(int num, String key, String newField ){
	       loadProperties();
	      // get value of property title entered
	      String originalEntry = getPropertyTable().getProperty(key);

	      //System.out.println("\nThe original entry was " + originalEntry); - debug
	      //create stringbuilder object to manipulate entry
	      StringBuilder builder = new StringBuilder(originalEntry); 
	      //StringBuilder field = new StringBuilder(originalEntry); 

	      //set beginning and ending indexes of field for stringbuilder
	      setStartIndex(num, originalEntry);
	      setEndIndex(num, originalEntry);

	      //delete old field 
	      builder.delete(getStartIndex(), getEndIndex());
	      String afterDeletingAtStartAndEnd = builder.toString(); //- debugging
	      //System.out.println("afterDeletingAtStartAndEnd = " + afterDeletingAtStartAndEnd); - debugging

	      //insert new field at appropriate index
	      builder.insert(getStartIndex(), newField);
	      String afterInsertOffset = builder.toString();
	      //System.out.println("\nAfter updating the field, the entry is  " + afterInsertOffset); - debug

	      //delete old entry, and add new entry
	      getPropertyTable().remove(key);
	      getPropertyTable().setProperty(getFirstField(afterInsertOffset), afterInsertOffset);
	      saveProperties();
	      System.out.println("\nAfter saving update to inventory...");
	      listProperties();
	   }
		@Override
		//implement deleteItem() method of interface Maintainable
		//method takes string key parameter, displays the current value of the key, 
		//then deletes the key/value from inventory
		public void deleteItem(String deleteString){
			searchItem(deleteString);
			getPropertyTable().remove(deleteString);
			saveProperties();
	  		System.out.println(deleteString + " was deleted from inventory\n");
	  		//System.out.println("After deleting entry: " + deleteString);
	  		//listProperties( ); // display property values
		}

		//class does not implement Maintainable methods searchItem, getInventoryFieldNames, 
		//or setInventoryFieldNames; therefore it is declared abstract
}
