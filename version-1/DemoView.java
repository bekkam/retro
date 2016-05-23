/*
 * DemoView Class stores user input from the console using the scanner class, and 
 *  relays the input to DemoController
 */
import java.util.Scanner;

public class DemoView {

	private DemoModel model;
	private DemoController controller;
	//variables to store user inputs
	private int categoryNumber;
	private int operationNumber;
	private String viewString;
	private String viewString2;
	private int fieldToEdit;
	//variables in message prompts
	private String categoryName;
	private String fields;
	

	public DemoView (DemoController controller){
			this.controller = controller;
	} // end constructor

	public void start() {
		Scanner scan = new Scanner(System.in);
		
	    //Display Welcome screen		
		System.out.println("Welcome!");
		do{
	    	//display Main Menu and store user input as categoryNumber
			System.out.println("Borders Main Menu \nPlease select an item from the list below"
	    		+ "\n\t1 - Books\n" + "\t2 - CDs\n" + "\t3 - DVDs\n" + "\t0 - Exit");
			categoryNumber = scan.nextInt();
			
	    	//if user does not 'exit'
		    if (categoryNumber !=0){
		    	//set model category via controller method
		    	controller.setModelCategory(categoryNumber);

		    	//display Operation menu and store user input as operation
		        System.out.println("Please select an operation from the list below:\n" +
					"\t1 - Create new inventory item\n" + "\t2 - Search inventory\n" + "\t3 - Edit inventory item\n" + 
		   			"\t4 - Delete inventory item");
		        operationNumber = scan.nextInt(); 
		        //ensure scan object finishes the line after the integer, so that subsequent scan.nextLine() works
		        scan.nextLine();
 	
		        //IF USER SELECTS CREATE
 				if (operationNumber == 1){		        	
 					System.out.println("Create Menu \nEnter the " + fields + " for the new " + categoryName
		        			+ " item using a backslash ('\\') to separate fields.");
		        	viewString = scan.nextLine();
		        	controller.setOperation(viewString, operationNumber);//set string to user input and notify model
 				}
		      
		        //IF USER SELECTS SEARCH 
		        else if (operationNumber == 2){
		        	System.out.println("Search Menu \nEnter the title of the " + categoryName + " you would like to search for");
		        	viewString = scan.nextLine();
		        	controller.setUserStringInput(viewString);//set string to user input and notify model
		        	controller.setOperation(viewString, operationNumber);
		        }
 				
		        //IF USER SELECTS EDIT
		        else if (operationNumber == 3){
		        	System.out.println("Edit Menu \nEnter the title of the " + categoryName + " you would like to edit");
		        	viewString = scan.nextLine();
		        	System.out.println("Enter the number of the field you would like to edit (\'1\' for first field, "
		        			+ "\'2\' for second field...)");
		        	fieldToEdit = scan.nextInt();
			        scan.nextLine();
		        	System.out.println("Edit Menu \nEnter the new data for field number " + fieldToEdit);
		        	viewString2 = scan.nextLine();
		        	controller.setOperation(viewString, viewString2, operationNumber, fieldToEdit);
		        }
		        //IF USER SELECTS DELETE
		        else if (operationNumber == 4){
		        	System.out.println("Delete Menu \nEnter the title of the " + categoryName + " you would like to remove from inventory");
		        	viewString = scan.nextLine();
		        	controller.setOperation(viewString, operationNumber);//set string to user input and notify model of string and operation
		        }  
		    }else break; 
		}while (categoryNumber !=0);//end if user does not exit
		    	System.out.println("Thank you for using Border's Inventory Program!");
        scan.close(); 
	}//end start()


	public void setModel(DemoModel model) {
		this.model = model;
	}
	
	public void notify(String string) {
		//load properties for selected category
		model.getItems();
		//get the name of the selected category from model
		categoryName = model.getCategory();
		//System.out.println("categoryName is " + categoryName); - debugging
		//get the fields for selected category from model
		fields = model.getFields();
		//System.out.println("fields are " + fields ); - debugging
	}

	public void notify(int operationSelected) {
		model.performAction();
	}		
}//end DemoView class
