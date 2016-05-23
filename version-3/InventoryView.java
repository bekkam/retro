package java3HW3folder;
//package java3HW3folder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class InventoryView extends JFrame //VIEW
{
	private InventoryModel resultSetTableModel;
	
   // database URL, username and password
   static final String DATABASE_URL = "jdbc:mysql://localhost/inventorydb";
   static final String USERNAME = "deitel";
   static final String PASSWORD = "deitel";
   
   // default query retrieves all data from a table
   static final String BOOK_DEFAULT_QUERY = "SELECT * FROM book";
   static final String CD_DEFAULT_QUERY = "SELECT * FROM cd";
   static final String DVD_DEFAULT_QUERY = "SELECT * FROM dvd";

   private JTable resultTable;
   private InventoryModel tableModel;
   private JList categoryJList;
   private static final String[] categoryNames = {"Book", "CD", "DVD"};
   private static final String[] queries = {BOOK_DEFAULT_QUERY, CD_DEFAULT_QUERY, DVD_DEFAULT_QUERY};
   
   private String[] fieldArray; 
   private int currentRow;
   private String viewString;
   private String[] addFieldsArray;
   private String fieldOne;
   // create ResultSetTableModel and GUI
   public InventoryView(InventoryModel resultSetTableModel) 
   {   
      super( "Welcome to Border's Inventory Application" );
      
        this.resultSetTableModel =  resultSetTableModel;
        
      // create ResultSetTableModel and display database table
        
      try 
      {
         // create TableModel for results
         tableModel = new InventoryModel( DATABASE_URL,
            USERNAME, PASSWORD, BOOK_DEFAULT_QUERY );
         
 		//Category Panel: Prompt and buttons
        JPanel categoryPanel = new JPanel();
 		categoryPanel.setLayout(new GridLayout (2, 1));
 		
 		JTextArea textAreaCategoryPrompt = new JTextArea("\nPlease select a category from the list below:");
 		textAreaCategoryPrompt.setOpaque(false);
 		textAreaCategoryPrompt.setEditable(false); 		
 
		
        // set up JList for categories
 		categoryJList = new JList(categoryNames);
 		categoryJList.setVisibleRowCount(3);
 		categoryJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		add(new JScrollPane(categoryJList));
 		
		categoryPanel.add(textAreaCategoryPrompt);
		categoryPanel.add(categoryJList);
		add(categoryPanel, BorderLayout.NORTH );
		
		JPanel operationTextPanel = new JPanel();
		operationTextPanel.setLayout(new FlowLayout());
 		JTextArea textAreaOperationPrompt = new JTextArea("\nEnter a term to filter results, or select an operation from the buttons below:");
 		textAreaOperationPrompt.setOpaque(false);
 		textAreaOperationPrompt.setEditable(false);
 		operationTextPanel.add(textAreaOperationPrompt, FlowLayout.LEFT);
 		
		//operation Panel
		JPanel operationButtonPanel = new JPanel();
		
         // set up JButton for operations
         JButton addButton = new JButton( "Add Item" );
         JButton updateButton = new JButton( "Update Item" );
         JButton deleteButton = new JButton( "Delete Item" );
         operationButtonPanel.add(addButton);
         operationButtonPanel.add(deleteButton);
         operationButtonPanel.add(updateButton);
         
         JPanel operationPanel = new JPanel();
         operationPanel.setLayout(new BorderLayout());
         operationPanel.add(operationTextPanel, BorderLayout.NORTH);
         
         operationPanel.add(operationButtonPanel, BorderLayout.SOUTH);
         add(operationPanel, BorderLayout.SOUTH);

         //components for central panel
         // create JTable based on the tableModel
         final JTable resultTable = new JTable( tableModel );
         resultTable.setCellSelectionEnabled(true);
         add( new JScrollPane( resultTable ));

         
         JLabel filterLabel = new JLabel( "Filter:" );
         final JTextField filterText = new JTextField();
         JButton filterButton = new JButton( "Apply Filter" );
         Box boxSouth = Box.createHorizontalBox(); 
         boxSouth.add( filterLabel );
         boxSouth.add( filterText );
         boxSouth.add( filterButton );
         
         operationPanel.add(boxSouth, BorderLayout.CENTER);

         //event listener for category selections
         categoryJList.addListSelectionListener(
    		 new ListSelectionListener()
    		 {
    			 public void valueChanged(ListSelectionEvent event)
    			 {
    				 //get new table to display 
    				 try {
    					 tableModel.setQuery(queries[categoryJList.getSelectedIndex()]);
					} catch (SQLException e) {
						e.printStackTrace();
					}
    			 }
    		 }
    		 );
         
         // create event listener for update Button
         updateButton.addActionListener(     
            new ActionListener() 
            {
               public void actionPerformed( ActionEvent event )
               {   
              	  int row = resultTable.convertRowIndexToModel(resultTable.getSelectedRow());
              	  int col = resultTable.convertColumnIndexToModel(resultTable.getSelectedColumn());
             	  if (tableModel.isCellEditable(row, col) == false){
             		   JOptionPane.showMessageDialog(null, "To edit an entry, first select an editable table cell. " +
             				   tableModel.getColumnName(0) + " is not an editable cell.");
             		   return;
             	   }
                  try 
                  {             	  
                	  //System.out.println("row in model is " + row + "; column in model is " + col);
                	  String newField = JOptionPane.showInputDialog("Entry the new data ");
                	  tableModel.editItem(row, col, newField);                	   
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery( BOOK_DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
         
         //listen for row selections
         resultTable.getSelectionModel().addListSelectionListener( 
        	new ListSelectionListener()
        		 {
					public void valueChanged(ListSelectionEvent e) {
						//System.out.println("changed row");						
						//if (resultTable.getCellSelectionEnabled() )
						currentRow =  resultTable.getSelectedRow();
						//System.out.println("row is now " + currentRow);
					}
        		 }
        	);
         
         // create event listener for Add Button
         addButton.addActionListener(     
            new ActionListener() 
            {        	
               public void actionPerformed( ActionEvent event )
               {
           		viewString = JOptionPane.showInputDialog("Create Menu \nEnter the " + tableModel.getFields() + " for the new " //+ categoryName 
    					+ "item, using a backslash ('\\') to separate fields.");
           		
           		System.out.println(viewString);
    		 	int fieldNum = tableModel.getColumnCount() -1;//number of columns, excluding auto-incremented itemID column
           		System.out.println("fieldNum = " + fieldNum);
           		
           		StringBuilder builder = new StringBuilder(viewString.toString());           		
           		//get values for the fields user entered
           		String slash = "\\";
           		int slashIndex = builder.indexOf(slash);
           		System.out.println("slashIndex is " + slashIndex);
           		
           		String fieldOne = builder.substring(0, builder.indexOf(slash));
           		builder.delete(0, (fieldOne.length()+1));
           		
           		String fieldTwo = builder.substring(0, builder.indexOf(slash));
           		builder.delete(0, fieldTwo.length()+1);
           		
           		String fieldThree = builder.substring(0, builder.indexOf(slash));
           		builder.delete(0, fieldThree.length()+1);
           		
           		String fieldFour = builder.substring(0, builder.length()); 	
           		//pass values to model's insert row method
            	  try
            	  {
            		  tableModel.addItem(fieldOne, fieldTwo, fieldThree, fieldFour);
            	  }
    		 	
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery( BOOK_DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );        
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();        
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }// end ActionListener inner class          
         ); // end call to addActionListener
         
         // create event listener for Delete Button
         deleteButton.addActionListener(     
            new ActionListener() 
            {        	
               public void actionPerformed( ActionEvent event )
               {
               try 
                  {
            	   //if user has selected a row, delete it upon pressing delete button
            		  if (resultTable.getSelectedRow() > -1) 
            		  tableModel.deleteItem();
            		  else{
            			  JOptionPane.showMessageDialog(null, "Select a row to delete an entry.");
            			  return;
            		  }
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery( BOOK_DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();      
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }// end ActionListener inner class          
         ); // end call to addActionListener
         
         
         final TableRowSorter< TableModel > sorter = 
            new TableRowSorter< TableModel >( tableModel );
         resultTable.setRowSorter( sorter );
         setSize( 500, 250 ); // set window size
         setVisible( true ); // display window  
         
         // create listener for filterButton
         filterButton.addActionListener(            
            new ActionListener() 
            {
               // pass filter text to listener
               public void actionPerformed( ActionEvent e ) 
               {
                  String text = filterText.getText();
                  if ( text.length() == 0 )
                     sorter.setRowFilter( null );
                  else
                  {
                     try
                     {
                        sorter.setRowFilter( 
                           RowFilter.regexFilter( text ) );
                     } // end try
                     catch ( PatternSyntaxException pse ) 
                     {
                        JOptionPane.showMessageDialog( null,
                           "Bad regex pattern", "Bad regex pattern",
                           JOptionPane.ERROR_MESSAGE );
                     } // end catch
                  } // end else
               } // end method actionPerfomed
            } // end annonymous inner class
         ); // end call to addActionLister
      } // end try
      catch (ClassNotFoundException classNotFount)
      {
    	  JOptionPane.showMessageDialog( null, "Unable to find Connector/J Driver class"); 
      }
      catch ( SQLException sqlException ) 
      {
         JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
            "Database error", JOptionPane.ERROR_MESSAGE );              
         // ensure database connection is closed
         tableModel.disconnectFromDatabase();        
         System.exit( 1 ); // terminate application
      } // end catches
      
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      // ensure database connection is closed when user quits application
      addWindowListener(     
         new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end InventoryView constructor

   public void start(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 450);
		setLocation(350, 50);
		setVisible(true);
   }
} // end class InventoryView

