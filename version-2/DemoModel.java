
import java.util.Properties;

public class DemoModel {
  
	private DemoView2 view;
	private String userString;
	private String userString2;
	private int fieldNumber;
	private int maintainableObjectsIndexNumber;
	//Enum type to store strings for name of inventory item and its fields
	private MediaType currentMediaType;
			
	private ActionRequest currentOperation;
	private enum ActionRequest {CREATE, SEARCH, EDIT, DELETE, DISPLAY};
	
	//string to store next view prompt
	private String nextDialog;

	//Instantiate book, cd, and dvd objects to process properties, and properties object
	private Properties propTableBook = new Properties();
	private Properties propTableCd = new Properties();
	private Properties propTableDvd = new Properties();	

	private Book book = new Book(propTableBook, "BookPropertiesHW2.dat", "Book Inventory Properties");
	private CD cd = new CD(propTableCd, "CDPropertiesHW2.dat", "CD Inventory Properties");
	private Dvd dvd = new Dvd(propTableDvd, "DVDPropertiesHW2.dat", "DVD Inventory Properties");

	//create 3-element Maintainable array 
	//initialize to objects book, cd, and dvd, which implement interface and inherit from Inventory Item
	private Maintainable[] maintainableObjects = new Maintainable[]{book, cd, dvd};
	
	public DemoModel() {

	}
	public void setView(DemoView2 view) {
		this.view = view;
	}
	
	//model sets inventory type and selects the corresponding InventoryItem from the array
	//then notifies view with string
	public void setCategory(int number) {
		setMaintainableObjectsIndexNumber(number-1);
		if (number == 1){
			currentMediaType = MediaType.BOOK;
		}
		else if (number == 2){
			currentMediaType = MediaType.CD;
		}
		else if (number == 3){
			currentMediaType = MediaType.DVD;
		}
		view.notify(" " + currentMediaType);
	}
	
	//returns the name of the currently selected inventory item, so view can display the category name in prompts
	public String getCategory(){
		return currentMediaType.getMediaTypeName().toString();
	}

	public String[] getMediaFieldArray(){
		return currentMediaType.getFieldArray();
	}
	
	//method to set the interface array to the appropriate object(book, cd, or dvd)
	public void setMaintainableObjectsIndexNumber(int number){
		maintainableObjectsIndexNumber = number;
	}
	
	//method to return the index number in the maintainableObjects array, that
	//corresponds to the the selected object/category 
	public int getMaintainableObjectsIndexNumber() {
		return maintainableObjectsIndexNumber;
	}
	
	//method sets operation in model, then notifies view with integer 
	public void setSelectedOperation(int operation) {
		if (operation == 1)
			currentOperation = ActionRequest.CREATE;
		else if (operation == 2)
			currentOperation = ActionRequest.SEARCH;
		else if (operation == 3)
			currentOperation = ActionRequest.EDIT;
		else if (operation == 4)
			currentOperation = ActionRequest.DELETE;
		else if (operation == 5)
			currentOperation = ActionRequest.DISPLAY;
		view.notify(operation);
	}
	
	public void setUserStringInput(String string) {
		userString = string;
	}
	
	public String getUserStringInput() {
		return userString;
	}
	
	//methods to set and get a second user input string
	public void setSecondUserStringInput(String string2) {
		userString2 = string2;
	}
	
	public String getSecondUserStringInput() {
		return userString2;
	}
	
	//setters and getters to access which field in a string user requests to edit
	public void setFieldNumber(int number){
		fieldNumber = number;
	}
	
	public int getFieldNumber(){
		return fieldNumber;
	}

	public void listItems(){
		String result =maintainableObjects[getMaintainableObjectsIndexNumber()].listItems();
		setNextDialog(result);
	}

	//method to load properties
	public void getItems(){
		maintainableObjects[getMaintainableObjectsIndexNumber()].getItems();
	}//end method getItems()

	//method to add item to inventory
	public void addItem(String itemString){
		String result =	maintainableObjects[getMaintainableObjectsIndexNumber()].addItem(itemString);
		setNextDialog(result);
	}//end method addItem()
	
	//method to search for item in inventory 
	public void searchItem(String itemString){
		maintainableObjects[getMaintainableObjectsIndexNumber()].searchItem(itemString);
		String result = maintainableObjects[getMaintainableObjectsIndexNumber()].searchItem(itemString);
		setNextDialog(result);
	}//end method searchItem()

	//method to edit item in inventory
	public void editItem(int num, String key, String newField){
		String result = maintainableObjects[getMaintainableObjectsIndexNumber()].editItem(num, key, newField);  
		setNextDialog(result);
	}
	
	//method to delete item from inventory
	public void deleteItem(String itemString){
		String result =	maintainableObjects[getMaintainableObjectsIndexNumber()].deleteItem(itemString);
		setNextDialog(result);
	}//end method deleteItem() 
	
	//performAction() method executes CRUD methods based on user's
	//selected operation
	public void performAction(){
		if (currentOperation == ActionRequest.CREATE)
			this.addItem(getUserStringInput());
		else if (currentOperation == ActionRequest.SEARCH)
			this.searchItem(getUserStringInput());
		else if (currentOperation == ActionRequest.EDIT)
			this.editItem(getFieldNumber(), getUserStringInput(), getSecondUserStringInput());
		else if (currentOperation == ActionRequest.DELETE)
			this.deleteItem(getUserStringInput());
		else if (currentOperation == ActionRequest.DISPLAY)
			this.listItems();
	} 
	
	public void setNextDialog(String string){
		nextDialog = string;
	}
	public String getNextDialog(){
		return nextDialog;
	}
}//end Model class
