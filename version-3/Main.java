package java3HW3folder;
//package java3HW3folder;

public class Main {

	public static void main(String[] args) {

		InventoryModel model = new InventoryModel();
		InventoryView view = new InventoryView(model);			
		view.start();
	}//end main  
}//end class

