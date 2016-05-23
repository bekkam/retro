
/* 
 * Maintainable interface contains methods to add, search, edit, and delete strings representing
 * items in a database. 
 */
public interface Maintainable {
	void getItems();
	String listItems();
	String addItem(String string);
	String editItem(int num, String key, String newField );
	String searchItem(String searchString);
	String deleteItem(String searchString);
}
