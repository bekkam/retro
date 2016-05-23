/* 
 * Maintainable interface contains methods to add, search, edit, and delete strings representing
 * items in a database. 
 */
public interface Maintainable {
	void getItems();
	void addItem(String string);
	void editItem(int num, String key, String newField );
	void searchItem(String searchString);
	void deleteItem(String searchString);
}
