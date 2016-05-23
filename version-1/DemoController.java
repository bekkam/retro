/*
 * DemoController class allows DemoView to pass user input to DemoModel
 * Contains methods to set category, operation, and process string input
 */
public class DemoController {

	private DemoModel model;

	public DemoController(DemoModel model){
		this.model = model;
	}

	public void setModelCategory(int number){
		model.setCategory(number);
	}

	public void setOperation(int number) {
		model.setSelectedOperation(number);
	}

	public void setUserStringInput(String string) {
		if (model.getUserStringInput() != string){
		model.setUserStringInput(string);
		}
	}
	
	public void setOperation(String viewString, int operationNumber) {
		model.setUserStringInput(viewString);
		model.setSelectedOperation(operationNumber);
	}
	
	public void setOperation(String viewString, String viewString2, int operationNumber, int fieldToEdit) {
		model.setUserStringInput(viewString);
		model.setSecondUserStringInput(viewString2);
		model.setFieldNumber(fieldToEdit);
		model.setSelectedOperation(operationNumber);
	}
}//end DemoController class