
public class InventoryMain {

	public static void main(String[] args) {
		DemoModel model = new DemoModel();
		DemoController controller = new DemoController(model);
		DemoView2 view = new DemoView2(controller);
		
		model.setView(view);
		view.setModel(model);
		
		view.start();
	}//end main
}
