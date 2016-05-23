package java3HW3folder;
//package java3HW3folder;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class InventoryModel extends AbstractTableModel ///table model object provides the the ResultSet data to the JTable
{
	InventoryModel model;
	// database URL, username and password
	static final String DATABASE_URL = "jdbc:mysql://localhost/books";
	static final String USERNAME = "deitel";
	static final String PASSWORD = "deitel";
   private Connection connection;
   private Statement statement;
   private ResultSet resultSet;
   private ResultSetMetaData metaData;
   private int numberOfRows;
   private int row;

   private String[] fieldsArray;
   // keep track of database connection status
   private boolean connectedToDatabase = false;

   
   public InventoryModel(){
	   
   }
   
   public InventoryModel( String url, String username,
      String password, String query ) throws SQLException, ClassNotFoundException
   {         
      // connect to database
      connection = DriverManager.getConnection( url, username, password );

      // create Statement to query database
      statement = connection.createStatement( 
         ResultSet.TYPE_SCROLL_SENSITIVE,
         ResultSet.CONCUR_UPDATABLE );

      // update database connection status
      connectedToDatabase = true;
      // set query and execute it
      setQuery( query );
      
   } // end constructor ResultSetTableModel  

   // get class that represents column type
   public Class getColumnClass( int column ) throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine Java class of column
      try 
      {
         String className = metaData.getColumnClassName( column + 1 );         
         // return Class object that represents className
         return Class.forName( className );
      } // end try
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } // end catch
      
      return Object.class; // if problems occur above, assume type Object
   } // end method getColumnClass

   // get number of columns in ResultSet
   public int getColumnCount() throws IllegalStateException
   {   
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine number of columns
      try 
      {
         return metaData.getColumnCount(); 
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return 0; // if problems occur above, return 0 for number of columns
   } // end method getColumnCount

   // get name of a particular column in ResultSet
   public String getColumnName( int column ) throws IllegalStateException
   {    
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine column name
      try 
      {
         return metaData.getColumnName( column + 1 );  
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string for column name
   } // end method getColumnName

   // return number of rows in ResultSet
   public int getRowCount() throws IllegalStateException
   {      
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );
 
      return numberOfRows;
   } // end method getRowCount

   // obtain value in particular row and column
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try 
      {
         resultSet.absolute( row + 1 );
         return resultSet.getObject( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch     
      return ""; // if problems, return empty string object
   } // end method getValueAt
   
   // set new database query string
   public void setQuery( String query ) 
      throws SQLException, IllegalStateException 
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      resultSet = statement.executeQuery( query );
      // obtain meta data for ResultSet
      metaData = resultSet.getMetaData();
      // determine number of rows in ResultSet
      resultSet.last();                   // move to last row
      numberOfRows = resultSet.getRow();  // get row number           
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setQuery
   

   
   //method to add row to database 
   public void addItem(String second, String third, String fourth, String fifth)
		   throws SQLException , IllegalStateException
   {
		resultSet.moveToInsertRow();
		resultSet.updateString(2, second);
		resultSet.updateString(3, third);	
		resultSet.updateString(4, fourth);		
		resultSet.updateString(5, fifth);		

		resultSet.insertRow();
		
	      metaData = resultSet.getMetaData();
	      // determine number of rows in ResultSet
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number      
	      // notify JTable that model has changed
	      fireTableStructureChanged();
	}

   public void editItem(int itemID, int column, String string)
	   throws SQLException , IllegalStateException{
	   resultSet.updateString( column+1, string); // updates the
          // column of with new string
	   resultSet.updateRow(); // updates the row in the data source
	      metaData = resultSet.getMetaData();
	      // determine number of rows in ResultSet
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number      
	      // notify JTable that model has changed
	      fireTableStructureChanged();
   }
   
   public void deleteItem()
		   throws SQLException , IllegalStateException{
	   System.out.println("the current model row is " + resultSet.getRow());
	   resultSet.deleteRow();
	      metaData = resultSet.getMetaData();
	      // determine number of rows in ResultSet
	      resultSet.last();                   // move to last row
	      numberOfRows = resultSet.getRow();  // get row number      
	      // notify JTable that model has changed
	      fireTableStructureChanged();
   }
   
   public boolean isCellEditable(int row, int col){
	   if (col < 1) {
           return false;
       } else {
          // System.out.println("cell is editable in model");
           return true;
       }
   }
   
   
   public void setValueAt(Object value, int row, int col )           
		   throws IllegalStateException{
       // ensure database connection is available
       if ( !connectedToDatabase ) 
          throw new IllegalStateException( "Not Connected to Database" );//{
       		try
       		{
    	   resultSet.absolute(row); // moves the cursor to the row of rs
    	   resultSet.updateObject(col, value);
                
                 fireTableCellUpdated(row, col);
              } // end try
              catch ( SQLException sqlException ) 
              {
                 sqlException.printStackTrace();
              } // end catch     
              //return ""; // if problems, return empty string object
            // end method getValueAt
          
   }
   
   //returns column names as string array
   public void setFieldsArray(){
	   String[] fieldsArray = new String[getColumnCount()];
	   String add;
	   for(int i = 1; i< getColumnCount(); i++){
		   add = getColumnName(i);
	   		fieldsArray[i] = add;
	   		System.out.println(add);
   }
   }
   
   public String[] getFieldsArray(){
	   return fieldsArray;
   }
     
	//helper method returns list of fields for a given media item category, as comma delimited string
	public String getFields(){
		String fields = "";
		int length = getColumnCount();
		for(int i =1; i < length; i++ ){
			//insert comma after field name, unless it is the last field	
			String appendField = (i != length-1) ? (getColumnName(i) + ", ") : (getColumnName(i) );
			fields += appendField;
		}
		return fields;
	}
   

   public void setRow(int row){
	   this.row = row;
   }
   
   // close Statement and Connection               
   public void disconnectFromDatabase()            
   {              
      if ( connectedToDatabase )                  
      {
         // close Statement and Connection            
         try                                          
         {                                            
            resultSet.close();                        
            statement.close();                        
            connection.close();                       
         } // end try                                 
         catch ( SQLException sqlException )          
         {                                            
            sqlException.printStackTrace();           
         } // end catch                               
         finally  // update database connection status
         {                                            
            connectedToDatabase = false;              
         } // end finally                             
      } // end if
   } // end method disconnectFromDatabase          
}  // end class InventoryModel

