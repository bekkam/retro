public class DemoInventoryMain {

	public static void main(String[] args) {
		DemoModel model = new DemoModel();
		DemoController controller = new DemoController(model);
		DemoView view = new DemoView(controller);
		
		model.setView(view);
		view.setModel(model);
		
		view.start();
	}//end main
}
