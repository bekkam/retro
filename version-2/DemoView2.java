import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class DemoView2 extends JFrame{
	private JPanel welcomePanel;
	private JPanel categoryPanel;
	private JPanel operationPanel;
	private JPanel displayPanel;
	
	private JTextArea textAreaWelcomePrompt;
	private JTextArea textAreaCategoryPrompt;
	private JTextArea textAreaOperationPrompt;
	private JTextArea displayTextArea;

	private JPanel buttonJPanel;
	private JPanel radioButtonJpanel;
	
	private JRadioButton[] radioButtons;
	private static String[] radioButtonNames = {"Book", "CD", "DVD"};
	private ButtonGroup radioGroup;
	
	private JButton[] buttons;
	private static String[] buttonNames = {"Add new item to inventory", "Search inventory", "Edit inventory item", "Delete inventory item"};
	
	private JPanel ButtonJPanel2;
	private DemoModel model;
	private DemoController controller;
	
	//variables to store user inputs	
	private int operationNumber;
	private String viewString;
	private String viewString2;
	private String fieldToEditString;
	
	//variables in message prompts
	private String categoryName;
	private String nextDialog;
	private String fieldArray[];
	
	public DemoView2(DemoController controller)
	{
		super("Border's Inventory Management Application");
		this.controller = controller;
		setLayout(new GridBagLayout());
		
		//create font objects to pass to JTextArea format method
		Font arialFont = new Font("Arial", Font.BOLD, 12);
		Font lucidaFont = new Font("Lucida Bright", Font.BOLD, 18);
			
		//Welcome panel 
		welcomePanel = new JPanel();
		GridBagConstraints welcomePanelGbc = new GridBagConstraints();		
		welcomePanelGbc.gridwidth = GridBagConstraints.REMAINDER; 
		welcomePanelGbc.weighty = .2;
		add(welcomePanel, welcomePanelGbc);

		textAreaWelcomePrompt = new JTextArea("Welcome to Borders Main Menu!");
		addFormattedTextArea(welcomePanel, textAreaWelcomePrompt, lucidaFont, false, false);

		//Category Panel
		categoryPanel = new JPanel();
		categoryPanel.setLayout(new GridLayout (2, 1));
		setBorders(categoryPanel, "Step One");

		GridBagConstraints categoryPanelGbc = new GridBagConstraints();
		setGridBagConstraints(categoryPanel, categoryPanelGbc);

		textAreaCategoryPrompt = new JTextArea("\nPlease select an item from the list below:");
		addFormattedTextArea(categoryPanel, textAreaCategoryPrompt, arialFont, false, false);		
		
		radioButtons = new JRadioButton[radioButtonNames.length];
		radioGroup = new ButtonGroup();
		radioButtonJpanel = new JPanel();
		radioButtonJpanel.setLayout(new GridLayout (1, 3));
		RadioButtonHandler radioButtonHandler = new RadioButtonHandler();
		for (int i = 0; i < radioButtonNames.length; i++){
			radioButtons[i] = new JRadioButton(radioButtonNames[i], false);
			radioGroup.add(radioButtons[i]);
			radioButtonJpanel.add(radioButtons[i]);
			radioButtons[i].addItemListener(radioButtonHandler);
		}
		categoryPanel.add(radioButtonJpanel);
		
		
		//Operation Panel
		operationPanel = new JPanel();
		operationPanel.setLayout(new GridLayout (3, 1, 5, 5));
		setBorders(operationPanel, "Step Two");
		operationPanel.setVisible(true);
		
		GridBagConstraints operationPanelGbc = new GridBagConstraints();
		setGridBagConstraints(operationPanel, operationPanelGbc);

		textAreaOperationPrompt = new JTextArea("\nPlease enter an operation from the list below, or click to view "
				+ "current inventory:\n");
		addFormattedTextArea(operationPanel, textAreaOperationPrompt, arialFont, false, false);
		
		//buttons for operation methods
		buttons = new JButton[buttonNames.length];
		ButtonHandler buttonHandler = new ButtonHandler();
		buttonJPanel = new JPanel();
		buttonJPanel.setLayout(new GridLayout(2, 1));
		for (int i = 0; i < buttonNames.length; i++){
			buttons[i] = new JButton(buttonNames[i]);
			buttons[i].addActionListener(buttonHandler);
			buttonJPanel.add(buttons[i]);
		}
		operationPanel.add(buttonJPanel);
		
		ButtonJPanel2 = new JPanel();
		ButtonJPanel2.setLayout(new BorderLayout());
		JButton currentList = new JButton("Current Inventory List");
		currentList.setFont(new Font ("Arial", Font.BOLD + Font.ITALIC, 12));
		
		ListButtonHandler listButtonHandler = new ListButtonHandler();
		currentList.addActionListener(listButtonHandler);
		
		ButtonJPanel2.add(currentList, BorderLayout.SOUTH);
		operationPanel.add(ButtonJPanel2);
		
		
		
		//Display Panel displays inventory items
		displayPanel = new JPanel();
		setBorders(displayPanel, "Display Panel");
		displayPanel.setBackground(new Color(255,255, 255,255));
		displayPanel.setLayout(new GridLayout(1,2));
		displayPanel.setVisible(false);

		GridBagConstraints displayPanelGbc = new GridBagConstraints();
		displayPanelGbc.gridwidth = GridBagConstraints.REMAINDER;
		displayPanelGbc.weighty = .7;
		displayPanelGbc.weightx = 1.0;
		displayPanelGbc.fill = GridBagConstraints.BOTH;
		add(displayPanel, displayPanelGbc);	
		
		
		displayTextArea = new JTextArea();
		addFormattedTextArea(displayPanel, displayTextArea, arialFont, false, true);
		JScrollPane displayScrollPane = new JScrollPane(displayTextArea);
		displayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		displayPanel.add(displayScrollPane);
	}
	
	//inner class to handle category radio button events - set Category number to corresponding radioButton array index +1
	//(because model counts category number beginning at "1", not "0"
	private class RadioButtonHandler implements ItemListener{
		public void itemStateChanged(ItemEvent event){
			for (int i = 0; i < radioButtons.length; i++){		
				if (radioButtons[i].isSelected())
				controller.setModelCategory(i +1);
			}
			operationPanel.setVisible(true);
		}
	}
	
	//inner class to handle operation selection
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			Object source = event.getSource();
			displayPanel.setVisible(true);

			//user prompts
			String createPrompt = ("Create Menu \nEnter the" + getFields() + "for the new " + categoryName 
					+ " item using a backslash ('\\') to separate fields.");
			String searchPrompt = ("Search Menu \nEnter the title of the " + categoryName + " you would like to search for");	
			String editPrompt1 = ("Edit Menu \nEnter the title of the " + categoryName + " you would like to edit");
			String editPrompt2 = ("Enter the number of the field you would like to edit (\'1\' for first field, "
					+ "\'2\' for second field...)");
			String deletePrompt = ("Delete Menu \nEnter the title of the " + categoryName 
					+ " you would like to remove from inventory");
			
			if (source == buttons[0]){
				processOperation(1, createPrompt);
			}else if (source == buttons[1]){
				processOperation(2, searchPrompt);
			}else if (source == buttons[2]){
					processOperation(3, editPrompt1, editPrompt2);
			}else if (source == buttons[3]){
				processOperation(4, deletePrompt);
			}
		}
	}
	
	private class ListButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){
			processOperation(5);
			displayPanel.setVisible(true);
		}
	}

	public void start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 450);
		setLocation(350, 50);
		setVisible(true);
	}
	
	public void setModel(DemoModel model){
		this.model = model;
	}

	
	public void notify(String string) {
		model.getItems();
		categoryName = model.getCategory();
		fieldArray = model.getMediaFieldArray();
	}


	public void notify(int operationSelected) {
		model.performAction();
		nextDialog = model.getNextDialog();
	}	
	
	//helper method returns list of all fields for a given media item category, as comma delimited string
	public String getFields(){
		String fields = " ";
		int length;
		if (categoryName == "CD" || categoryName == "DVD")
			length = 4;
		else length = 3;
		for(int i =0; i < length; i++ ){
				fields = fields + fieldArray[i] + ", ";
		}
		return fields;
	}
	
	//helper method formats and adds JTextArea to JPanel
	private void addFormattedTextArea(JPanel jPanel, JTextArea text, Font font, boolean value, boolean value2){
		text.setFont(font);
		text.setEditable(value);
		text.setOpaque(value2);
		jPanel.add(text);
	}
	
	//sets the constraints for operation and category panels
	private void setGridBagConstraints(JPanel panel, GridBagConstraints c) {
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = .2;
		add(panel, c);
	}
	//sets panel borders 
	private void setBorders(JPanel panel, String string){
		TitledBorder title = BorderFactory.createTitledBorder(string);
		panel.setBorder(title);
	}
	//method to process list button 
	private void processOperation(int num){
		operationNumber = num;
		controller.setOperation(operationNumber);
    	displayTextArea.setText(nextDialog);
	}
	//method to process buttonhandler 
	private void processOperation(int num, String prompt){
		operationNumber = num;
		viewString = JOptionPane.showInputDialog(prompt);
		controller.setOperation(viewString, operationNumber);
		JOptionPane.showMessageDialog(null, nextDialog);
	}
	//overloaded method to process buttonhandler
	private void processOperation(int num, String prompt1, String prompt2){
		operationNumber = num;
		viewString = JOptionPane.showInputDialog(prompt1); 
		fieldToEditString = JOptionPane.showInputDialog(prompt2);
		int fieldToEdit = Integer.parseInt(fieldToEditString);
		viewString2 = JOptionPane.showInputDialog("Edit Menu \nEnter the new data for field number " + fieldToEdit);
    	controller.setOperation(viewString, viewString2, num, fieldToEdit);
    	displayTextArea.setText(nextDialog);
	}
}
