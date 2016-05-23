import java.util.Properties;

public class DemoModel {
  
	private DemoView view;
	private String userString;
	private String userString2;
	private int fieldNumber;
	private int maintainableObjectsIndexNumber;
	//Enum type to store strings for name of inventory item and its fields
	private MediaType currentMediaType;
			
	private ActionRequest currentOperation;
	private enum ActionRequest {CREATE, SEARCH, EDIT, DELETE};
	
	//Instantiate book, cd, and dvd objects to process properties, and properties object
	private Properties propTableBook = new Properties();
	private Properties propTableCd = new Properties();
	private Properties propTableDvd = new Properties();	

	private Book book = new Book(propTableBook, "BookPropertiesDemo.dat", "Book Inventory Properties");
	private CD cd = new CD(propTableCd, "CDPropertiesDemo.dat", "CD Inventory Properties");
	private Dvd dvd = new Dvd(propTableDvd, "DVDPropertiesDemo.dat", "DVD Inventory Properties");

	//create 3-element Maintainable array 
	//initialize to objects book, cd, and dvd, which implement interface and inherit from Inventory Item
	private Maintainable[] maintainableObjects = new Maintainable[]{book, cd, dvd};
	
	public DemoModel() {

	}
	public void setView(DemoView view) {
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
	
	//returns a string of the fields of the currently selected inventory item, so view can display the fields in prompts
	public String getFields(){
		return currentMediaType.getMediaTypeFields().toString();
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
		view.notify(operation);
	}
	
	public void setUserStringInput(String string) {
		//System.out.println("setUserStringInput() setting userstring to...  " + string);
		userString = string;
	}
	
	public String getUserStringInput() {
		return userString;
	}
	
	//methods to set and get a second user input string
	public void setSecondUserStringInput(String string2) {
		//System.out.println("setSecondUserStringInput() setting userstring2 to...  " + string2);
		userString2 = string2;
	}
	
	public String getSecondUserStringInput() {
		return userString2;
	}
	
	//setters and getters to access which field in a string user requests to edit
	public void setFieldNumber(int number){
		//System.out.println("setFieldNumber() setting fieldNumber to...  " + number);
		fieldNumber = number;
	}
	
	public int getFieldNumber(){
		return fieldNumber;
	}
	

	//method to load properties
	public void getItems(){
		maintainableObjects[getMaintainableObjectsIndexNumber()].getItems();
	}//end method getItems()

	//method to add item to inventory
	public void addItem(String itemString){
		maintainableObjects[getMaintainableObjectsIndexNumber()].addItem(itemString);
	}//end method addItem()
	
	//method to search for item in inventory 
	public void searchItem(String itemString){
		maintainableObjects[getMaintainableObjectsIndexNumber()].searchItem(itemString);
	}//end method searchItem()

	//method to edit item in inventory
	public void editItem(int num, String key, String newField){
		//System.out.println("editItem() in Model triggered"); - debugging
		//inventoryItemObjects[getCategory()].editItem(num, key, newField); - WORKS
		maintainableObjects[getMaintainableObjectsIndexNumber()].editItem(num, key, newField); //WORKS BY ADDING EDIT METHOD TO INTERFACE 
	}
	
	//method to delete item from inventory
	public void deleteItem(String itemString){
		maintainableObjects[getMaintainableObjectsIndexNumber()].deleteItem(itemString);
	}//end method deleteItem() 
	
	//performAction() method executes CRUD methods based on user's
	//selected operation
	public void performAction(){
		if (currentOperation == ActionRequest.CREATE)
			this.addItem(getUserStringInput());
		else if (currentOperation == ActionRequest.SEARCH)
			this.searchItem(getUserStringInput());
		else if (currentOperation == ActionRequest.EDIT){
			this.editItem(getFieldNumber(), getUserStringInput(), getSecondUserStringInput());
			//System.out.println("editItem() in Model's performAction() triggered"); - debugging
		}
		else if (currentOperation == ActionRequest.DELETE)
			this.deleteItem(getUserStringInput());
	}
}//end Model class
