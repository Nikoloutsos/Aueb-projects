

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SingleSelectionModel;

public class Gui extends JFrame {
	static Company company;
	JMenuBar menuBar;
	JMenu loadMenu;
	JMenu saveMenu;
	static int expenseTypeState;
	JMenuItem loadFromExpTypeItem = new JMenuItem("Load Expenses Type");
	JMenuItem loadFromBuildingItem = new JMenuItem("Load Building and Expenses");
	JMenuItem saveDataLocallyItem = new JMenuItem("Save data locally");
	JPanel buildingandExpenseTab = null;
	JPanel expenseTypeTab = null;
	
	static DefaultListModel<String> buildingListModel = new DefaultListModel<>();
	static DefaultListModel<String> expenseTypeCategoryListModel = new DefaultListModel<>();
	static DefaultListModel<String> expenseTypeListModel = new DefaultListModel<>();
	public Gui(Company company) {
		super("Accounting Department GUI");
		
		this.company = company;
		
		
		setSize(1500,500);
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/TitleIcon.png"));
		setLayout(new FlowLayout());
		this.setSize( 700 , 500 );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		loadMenu = new JMenu("Load Data");
		
		
		loadMenu.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/loadIcon.png"));
		loadMenu.add(loadFromExpTypeItem);
		loadMenu.add(loadFromBuildingItem);
		
		loadFromBuildingItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser(".");
				file.showOpenDialog(null);
				try {
					String path = file.getSelectedFile().toString();
				LoadAndExportUtil.loadBuildingAndExpenses(company, path);
				if (company.getBuildings().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Cannot parse text file", "Error while parsing",
					        JOptionPane.INFORMATION_MESSAGE);
				}else {
					buildingListModel.removeAllElements();
					for (Building currentBuilding : company.getBuildings()) {
						
						buildingListModel.addElement(currentBuilding.getCode() + " | " + currentBuilding.getDescription()+
							"                                                                               ");
					
					}
				}
				}catch (Exception e) {
					//User closed window without selecting something.
				}
				
				
				
			}
			
		});
		loadFromExpTypeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser file = new JFileChooser(".");
				file.showOpenDialog(null);
				try {
					String path = file.getSelectedFile().toString();
				List<ExpenseType> expensesType = LoadAndExportUtil.loadExpensesTypeFromTxt(path);
				if (expensesType.isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Cannot parse text file", "Error while parsing",
					        JOptionPane.INFORMATION_MESSAGE);
				}else {
					company.getExpensesType().removeAll(company.getExpensesType());
					company.getExpensesType().addAll(expensesType);
					
				}
				}catch (Exception e) {
					//User closed window without selecting something.
				}
				
			}
			
		});
		
		saveMenu = new JMenu("Save Data");
		saveMenu.add(saveDataLocallyItem);
		saveMenu.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/SaveIcon.png"));
		saveDataLocallyItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Save data and display a user-friendly message to the user.
				LoadAndExportUtil.ExportDataInTxt(company.getBuildings(), company.getExpenses(), company.getExpensesType());
				JOptionPane.showMessageDialog(new JFrame(), "Data have been succesfully saved!", "Notification",
				        JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		});
		menuBar.add(loadMenu); menuBar.add(saveMenu);
		setJMenuBar(menuBar);
		getContentPane().setLayout(new GridLayout(1, 1));
		
		buildingandExpenseTab = makePanelBuildingAndExpenses(); 
		expenseTypeTab = makePanelExpensesType();
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		tabs.addTab("Buildings", buildingandExpenseTab);
		tabs.addTab("ExpensesType", expenseTypeTab);
		getContentPane().add(tabs);
		tabs.setVisible(true);
		
		
		addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 //TODO Ask user if he wants to save files before exiting. If yes then save them!!
				 int dialogResult = JOptionPane.showConfirmDialog (null, "Save files before exiting?","Warning",JOptionPane.YES_NO_OPTION);
				 if(dialogResult == JOptionPane.YES_OPTION){
					 LoadAndExportUtil.ExportDataInTxt(company.getBuildings(), company.getExpenses(), company.getExpensesType());
					 JOptionPane.showMessageDialog(new JFrame(), "Data have been succesfully saved! \n"
					 		+ "Check your desktop ;)", "Notification",
						        JOptionPane.INFORMATION_MESSAGE);
				 }
			      }
		});

		
		
		
		setVisible(true);
	}
	
	
	
	
	
	 private static JPanel makePanelBuildingAndExpenses() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JList<String> list = new JList(buildingListModel);
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent evt) {
				 
			        if (evt.getClickCount() == 2) {
			        	Building buildingSelected = null;
			        	int position = list.getSelectedIndex();
						if (position != -1) {
							buildingSelected = company.getBuildings().get(position);
						}
						editBuildingGui(buildingSelected);
			        	
			            
			        } 
			 }
		 });
		
		
		p.add(list);
		JToolBar tb = new JToolBar();
		JButton addBtn = new JButton();
		addBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Add.png"));
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddBuildingGui();
			}
			
		});
		JButton editBuildingBtn = new JButton();
		
		editBuildingBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Edit.png"));
		editBuildingBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Building buildingSelected;
				int position = list.getSelectedIndex();
				if (position != -1) {
					buildingSelected = company.getBuildings().get(position);
				}else {
					buildingSelected = null;
				}
				
				editBuildingGui(buildingSelected);
			}
			
		});
		JButton deleteBtn = new JButton();
		
		deleteBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Delete.png"));
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				
				int position = list.getSelectedIndex();
				if (position != -1) {
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Delete Building",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						ArrayList<Expense> toDelete = new ArrayList();
						for(Expense currentExpense : company.getExpenses()) {
							if (currentExpense.getBuilding() == company.getBuildings().get(position)) {
								toDelete.add(currentExpense);
							}
						}
						company.getExpenses().removeAll(toDelete);
						company.getBuildings().remove(position); //Don't forget to delete expenses for this building.
						updateListModel(company, "buildings");
					}
				}else {
					JOptionPane.showMessageDialog(null, "No building is selected.", "Delete Building",
					        JOptionPane.INFORMATION_MESSAGE);
				}
				 
				
			}
			
		});
		
		JButton editExpensesBtn = new JButton();
	
		editExpensesBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/EditExpensesIcon.png"));
		editExpensesBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Open a new window with expenses. It should contain 3 buttons. 1.add a new expense 2.modify 3.delete
				int position = list.getSelectedIndex();
				if (position != -1) {
						buildingExpenseTypeGUI(company.getBuildings().get(list.getSelectedIndex()));
				}else {
					JOptionPane.showMessageDialog(null, "No building is selected.", "EditExpenses",
					        JOptionPane.INFORMATION_MESSAGE);
				}
			
				
			}
			
		});
		
		JButton calcBuildingTotalCostBtn = new JButton();
		calcBuildingTotalCostBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/CalculateTotalCostIcon.png"));
		calcBuildingTotalCostBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int position = list.getSelectedIndex();
				if (position != -1) {
					JOptionPane.showMessageDialog(new JFrame(), "Total cost of building " +  company.getBuildings().get(position).getCode() + " \n is :  " + 
							company.calculateBuildingCost(company.getBuildings().get(position)), "Total Cost",
					        JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "No building is selected.", "Notification",
					        JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			
		});
		
		
		
		tb.add(addBtn); tb.add(editBuildingBtn); tb.add(deleteBtn);
		tb.add(editExpensesBtn); tb.add(calcBuildingTotalCostBtn); p.add(tb);
		return p; 	 
	}
	 
	 
	 
	private static JPanel makePanelExpensesType() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		expenseTypeCategoryListModel.addElement("Energy                         ");
		expenseTypeCategoryListModel.addElement("Water");
		expenseTypeCategoryListModel.addElement("Telephone");
		expenseTypeCategoryListModel.addElement("Cleaning");
		expenseTypeCategoryListModel.addElement("Rent");
		
		JList<String> expenseTypeCategoryList = new JList(expenseTypeCategoryListModel);
		expenseTypeCategoryList.setVisibleRowCount(10);
		expenseTypeCategoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		JList<String> expenseTypeList = new JList(expenseTypeListModel);
		expenseTypeList.setVisibleRowCount(10);
		expenseTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		expenseTypeList.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent evt) {
							 
			if (evt.getClickCount() == 2) {
				int index = expenseTypeList.locationToIndex(evt.getPoint());
				ExpenseType expenseTypeSelected = null;
				int counter = 0;
				int counter2 = 0;
				
				//TODO find the expense type and send it to the static method.
				if (expenseTypeState == 0) {
					for (ExpenseType currentExpenseType : company.getExpensesType()) {
						if(currentExpenseType instanceof Energy) {
							if (counter == index) {
								expenseTypeSelected = company.getExpensesType().get(counter2);
								break;
							}else {
								counter++;
							}
						}
						counter2++;
					}
					
				}else if (expenseTypeState == 1) {
					for (ExpenseType currentExpenseType : company.getExpensesType()) {
						if(currentExpenseType instanceof Water) {
							if (counter == index) {
								expenseTypeSelected = company.getExpensesType().get(counter2);
								break;
							}else {
								counter++;
							}	
						}
						counter2++;
					}
				}else if (expenseTypeState == 2) {
					for (ExpenseType currentExpenseType : company.getExpensesType()) {
						if(currentExpenseType instanceof Telephone) {
							if (counter == index) {
								expenseTypeSelected = company.getExpensesType().get(counter2);
								break;
							}else {
								counter++;
							}		
						}
						counter2++;
					}
				}else if (expenseTypeState == 3) {
					for (ExpenseType currentExpenseType : company.getExpensesType()) {
						if(currentExpenseType instanceof Cleaning) {
							if (counter == index) {
								expenseTypeSelected = company.getExpensesType().get(counter2);
								break;
							}else {
								counter++;
							}
						}
						counter2++;
					}
				}else if (expenseTypeState == 4) {
					for (ExpenseType currentExpenseType : company.getExpensesType()) {
						if(currentExpenseType instanceof Rent) {
							if (counter == index) {
								expenseTypeSelected = company.getExpensesType().get(counter2);
								break;
							}else {
								counter++;
							}
						}
						counter2++;
					}
				}
				showExpenseTypeGUI(expenseTypeSelected);
						        	
						            
						        } 
						 }
					 });
					
		
		JButton showExpensesTypeBtn = new JButton(">");
		showExpensesTypeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int position = expenseTypeCategoryList.getSelectedIndex();
				expenseTypeState = position;
				if( position != -1 ) {
					
					expenseTypeListModel.removeAllElements();
					switch (expenseTypeState) {
						case 0: 
							for(ExpenseType currentExpenseType : company.getExpensesType()) {
								if (currentExpenseType instanceof Energy) {
									expenseTypeListModel.addElement(currentExpenseType.getCode() + "|" + currentExpenseType.getDescription() + "                              ");
									
							
								}
							
							}
							break;
						case 1:
							for(ExpenseType currentExpenseType : company.getExpensesType()) {
								if (currentExpenseType instanceof Water) {
									expenseTypeListModel.addElement(currentExpenseType.getCode() + "|" + currentExpenseType.getDescription() + "                              ");
							
								}
							
							}
							break;
						case 2:
							for(ExpenseType currentExpenseType : company.getExpensesType()) {
								if (currentExpenseType instanceof Telephone) {
									expenseTypeListModel.addElement(currentExpenseType.getCode() + "|" + currentExpenseType.getDescription() + "                              ");
							
								}
							
							}
							break;
						case 3:
							for(ExpenseType currentExpenseType : company.getExpensesType()) {
								if (currentExpenseType instanceof Cleaning) {
									expenseTypeListModel.addElement(currentExpenseType.getCode() + "|" + currentExpenseType.getDescription() + "                              ");
							
								}
							
							}
							break;
						case 4:
							for(ExpenseType currentExpenseType : company.getExpensesType()) {
								if (currentExpenseType instanceof Rent) {
									expenseTypeListModel.addElement(currentExpenseType.getCode() + "|" + currentExpenseType.getDescription() + "                              ");
							
								}
							
							}
							break;
					}
					
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a Category for ExpenseType", "Notification",
					        JOptionPane.INFORMATION_MESSAGE);
		        	
				}
			}
			
		});
		
		
		
		p.add(expenseTypeCategoryList);
		p.add(showExpensesTypeBtn);
		p.add(expenseTypeList);
		
		return p;
		 
	}
	private  static void AddBuildingGui() {
		 	
			JFrame addBuildingGUI = new JFrame("Add a new building");	
			addBuildingGUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addBuildingGUI.setLayout(new GridLayout(6,2));
			addBuildingGUI.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Add.png"));
			
			JLabel codeLabel = new JLabel("*code: ");
			JLabel descriptionLabel = new JLabel("*description: ");
			JLabel adressLabel = new JLabel("*address: ");
			JLabel zoneCostLabel = new JLabel("zoneCost: ");
			JLabel squareMetersLabel = new JLabel("squareMeters: ");
			
			JTextField codeTextField = new JTextField(30);
			JTextField descriptionTextField = new JTextField(30);
			JTextField adressTextField = new JTextField(30);
			JTextField zoneCostTextField = new JTextField(30);
			JTextField squareMetersTextField = new JTextField(30);
			
			addBuildingGUI.add(codeLabel); addBuildingGUI.add(codeTextField);
			addBuildingGUI.add(descriptionLabel); addBuildingGUI.add(descriptionTextField);
			addBuildingGUI.add(adressLabel); addBuildingGUI.add(adressTextField);
			addBuildingGUI.add(zoneCostLabel); addBuildingGUI.add(zoneCostTextField);
			addBuildingGUI.add(squareMetersLabel); addBuildingGUI.add(squareMetersTextField);
			
			JButton addBtn = new JButton("ADD");
			addBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String code = codeTextField.getText();
					String description = descriptionTextField.getText();
					String address = adressTextField.getText();
					double zoneCost	;
					double squareMeters;
					try {
						zoneCost	= Double.parseDouble(zoneCostTextField.getText());
						
					}catch (NumberFormatException e) {
						zoneCost = 15;
					}
					try {
						squareMeters = Double.parseDouble(squareMetersTextField.getText());
						
					}catch (NumberFormatException e) {
						squareMeters = 150;
					}
					
					if (code.isEmpty() || description.isEmpty() || address.isEmpty()) {
						JOptionPane.showMessageDialog(new JFrame(), "Please, type code, description and address", "Not enough information",
						        JOptionPane.INFORMATION_MESSAGE);
						 
					}else {
					
						if (!company.codeAlreadyExists(code)) {
							JOptionPane.showMessageDialog(new JFrame(), "Building has been succesfully added.", "Notification",
						        JOptionPane.INFORMATION_MESSAGE);
							
							company.addBuilding(code, description, address, zoneCost, squareMeters);
							updateListModel(company, "buildings");
							
							addBuildingGUI.dispose();
						}else {
							JOptionPane.showMessageDialog(new JFrame(), "Code already in use by another building\n "
									+ "Try another one.", "Notification",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						 
						
					}
					
				}
				
			});
			JButton cancelButton = new JButton("CANCEL");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Add a Building",JOptionPane.YES_NO_OPTION);
					 if(dialogResult == JOptionPane.YES_OPTION){
						 addBuildingGUI.dispose();
						 }
					
					
				}
				
			});
			addBuildingGUI.add(addBtn); addBuildingGUI.add(cancelButton);
			
			
			addBuildingGUI.addWindowListener(new WindowAdapter(){
				 public void windowClosing(WindowEvent e) {
					 int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Add a Building",JOptionPane.YES_NO_OPTION);
					 if(dialogResult == JOptionPane.YES_OPTION){
						 addBuildingGUI.dispose();
					 }
				 }
				
			});
			addBuildingGUI.pack();
			addBuildingGUI.setVisible(true);
			
		
	}
	
	
	
	private static void editBuildingGui(Building buildingSelected) {
		if (buildingSelected == null ) {
			JOptionPane.showMessageDialog(new JFrame(), "No building is selected", "Edit|View a building",
			        JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		JFrame editBuildingGUI = new JFrame("Edit|View a building");	
		editBuildingGUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		editBuildingGUI.setLayout(new GridLayout(6,2,5,0));
		editBuildingGUI.setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Edit.png"));
		
		JLabel codeLabel = new JLabel("*code: ");
		JLabel descriptionLabel = new JLabel("*description: ");
		JLabel adressLabel = new JLabel("*address: ");
		JLabel zoneCostLabel = new JLabel("zoneCost: ");
		JLabel squareMetersLabel = new JLabel("squareMeters: ");
		
		JTextField codeTextField = new JTextField(30);
		JTextField descriptionTextField = new JTextField(30);
		JTextField adressTextField = new JTextField(30);
		JTextField zoneCostTextField = new JTextField(30);
		JTextField squareMetersTextField = new JTextField(30);
		
		codeTextField.setText(buildingSelected.getCode());
		descriptionTextField.setText(buildingSelected.getDescription());
		adressTextField.setText(buildingSelected.getAddress());
		zoneCostTextField.setText(String.valueOf(buildingSelected.getZoneCost()));
		squareMetersTextField.setText(String.valueOf(buildingSelected.getSquareMeters()));
		
		editBuildingGUI.add(codeLabel); editBuildingGUI.add(codeTextField);
		editBuildingGUI.add(descriptionLabel); editBuildingGUI.add(descriptionTextField);
		editBuildingGUI.add(adressLabel); editBuildingGUI.add(adressTextField);
		editBuildingGUI.add(zoneCostLabel); editBuildingGUI.add(zoneCostTextField);
		editBuildingGUI.add(squareMetersLabel); editBuildingGUI.add(squareMetersTextField);
		
		String previousBuildingCode = buildingSelected.getCode();
		JButton addBtn = new JButton("UPDATE");
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String code = codeTextField.getText();
				String description = descriptionTextField.getText();
				String address = adressTextField.getText();
				double zoneCost;
				double squareMeters;
				try {
					zoneCost = Double.parseDouble(zoneCostTextField.getText());
					
				}catch (NumberFormatException e) {
					zoneCost = 15;
				}
				try {
					squareMeters = Double.parseDouble(squareMetersTextField.getText());
					
				}catch (NumberFormatException e) {
					squareMeters = 150;
				}
				
				if (code.isEmpty() || description.isEmpty() || address.isEmpty()) {
					
					JOptionPane.showMessageDialog(new JFrame(), "Please, type code, description and address", "Not enough information",
					        JOptionPane.INFORMATION_MESSAGE);
					 
				}else {
					
					if (!company.codeAlreadyExists(code) || previousBuildingCode.equals(code) ) {
						JOptionPane.showMessageDialog(new JFrame(), "Building has been succesfully added.", "Notification",
						        JOptionPane.INFORMATION_MESSAGE);
						buildingSelected.setCode(code);
						buildingSelected.setDescription(description);
						buildingSelected.setAddress(address);
						buildingSelected.setZoneCost(zoneCost);
						buildingSelected.setSquareMeters(squareMeters);
						updateListModel(company, "buildings");
						
						
						
						
						editBuildingGUI.dispose(); //closes the window
					}else {
						JOptionPane.showMessageDialog(new JFrame(), "Code already in use by another building\n "
								+ "Try another one.", "Notification",
						        JOptionPane.INFORMATION_MESSAGE);
						
					}
					
					
				}
				
			}
			
		});
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Edit|View a building",JOptionPane.YES_NO_OPTION);
				 if(dialogResult == JOptionPane.YES_OPTION){
					 editBuildingGUI.dispose();
					 }
				
				
			}
			
		});
		editBuildingGUI.add(addBtn); editBuildingGUI.add(cancelButton);
		
		editBuildingGUI.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Edit|View a building",JOptionPane.YES_NO_OPTION);
				 if(dialogResult == JOptionPane.YES_OPTION){
					 editBuildingGUI.dispose();
					 }
			}
		});
		editBuildingGUI.pack();
		editBuildingGUI.setVisible(true);
	}
	
	
	
	private static void showExpenseTypeGUI (ExpenseType expenseType) {
		JFrame showExpenseTypeGUI = new JFrame("Expense Type");	
		showExpenseTypeGUI.setLayout(new GridLayout(6,2));
		
		JLabel codeLabel = new JLabel("Code: ");
		JLabel descriptionLabel = new JLabel("Description: "); 
		JLabel monthlyfeeLabel = new JLabel("monthlyfee: ");
		JLabel costPerUnitLabel = new JLabel("costPerUnit: ");
		JLabel monthlyCostLabel = new JLabel("monthlyCost: ");
		//TODO UPDATE data.
		
		
		
		JTextField codeTextField = new JTextField(30);  codeTextField.setText(expenseType.getCode());
		codeTextField.setEditable(false);
		codeTextField.setBackground(Color.WHITE);
		JTextField descriptionTextField = new JTextField(30); descriptionTextField.setText(expenseType.getDescription());
		descriptionTextField.setEditable(false);
		descriptionTextField.setBackground(Color.WHITE);
		JTextField monthlyfeeTextField = new JTextField(30); 
		monthlyfeeTextField.setEditable(false);
		monthlyfeeTextField.setBackground(Color.WHITE);
		JTextField costPerUnitTextField = new JTextField(30);
		costPerUnitTextField.setEditable(false);
		costPerUnitTextField.setBackground(Color.WHITE);
		JTextField monthlyCostTextField = new JTextField(30);
		monthlyCostTextField.setEditable(false);
		monthlyCostTextField.setBackground(Color.WHITE);
		
		
		if ( expenseType instanceof Water) {
			monthlyfeeTextField.setText(String.valueOf(((Water) expenseType).getMonthlyFee()));
			costPerUnitTextField.setText(String.valueOf(((Water) expenseType).getCostPerUnit()));
			monthlyCostLabel.setText("SecondaryCostPerUnit");
			monthlyCostTextField.setText(String.valueOf(((Water) expenseType).getSecondaryCostPerUnit()));
			
			showExpenseTypeGUI.add(codeLabel); showExpenseTypeGUI.add(codeTextField);
			showExpenseTypeGUI.add(descriptionLabel); showExpenseTypeGUI.add(descriptionTextField);
			showExpenseTypeGUI.add(monthlyfeeLabel); showExpenseTypeGUI.add(monthlyfeeTextField);
			showExpenseTypeGUI.add(costPerUnitLabel); showExpenseTypeGUI.add(costPerUnitTextField);
			showExpenseTypeGUI.add(monthlyCostLabel); showExpenseTypeGUI.add(monthlyCostTextField);
			
		}else if (expenseType instanceof Energy) {
			monthlyfeeTextField.setText(String.valueOf(((Energy) expenseType).getMonthlyFee()));
			costPerUnitTextField.setText(String.valueOf(((Energy) expenseType).getCostPerUnit()));
			monthlyCostLabel.setText("Monthly cost ERT: ");
			monthlyCostTextField.setText(String.valueOf(((Energy) expenseType).getMonthlyCostERT()));
			
			showExpenseTypeGUI.add(codeLabel); showExpenseTypeGUI.add(codeTextField);
			showExpenseTypeGUI.add(descriptionLabel); showExpenseTypeGUI.add(descriptionTextField);
			showExpenseTypeGUI.add(monthlyfeeLabel); showExpenseTypeGUI.add(monthlyfeeTextField);
			showExpenseTypeGUI.add(costPerUnitLabel); showExpenseTypeGUI.add(costPerUnitTextField);
			showExpenseTypeGUI.add(monthlyCostLabel); showExpenseTypeGUI.add(monthlyCostTextField);
		}else if (expenseType instanceof Telephone) {
			monthlyfeeTextField.setText(String.valueOf(((Telephone) expenseType).getMonthlyFee()));
			costPerUnitTextField.setText(String.valueOf(((Telephone) expenseType).getCostPerUnit()));
			monthlyCostLabel.setText("Monthly cost Telephone: ");
			monthlyCostTextField.setText(String.valueOf(((Telephone) expenseType).getMonthlyCostTelephone()));
			
			showExpenseTypeGUI.add(codeLabel); showExpenseTypeGUI.add(codeTextField);
			showExpenseTypeGUI.add(descriptionLabel); showExpenseTypeGUI.add(descriptionTextField);
			showExpenseTypeGUI.add(monthlyfeeLabel); showExpenseTypeGUI.add(monthlyfeeTextField);
			showExpenseTypeGUI.add(costPerUnitLabel); showExpenseTypeGUI.add(costPerUnitTextField);
			showExpenseTypeGUI.add(monthlyCostLabel); showExpenseTypeGUI.add(monthlyCostTextField);
		}else if (expenseType instanceof Cleaning) {
			costPerUnitLabel.setText("Cost per square: ");
			costPerUnitTextField.setText(String.valueOf(((Cleaning) expenseType).getCostPerSquare()));
			
			showExpenseTypeGUI.add(codeLabel); showExpenseTypeGUI.add(codeTextField);
			showExpenseTypeGUI.add(descriptionLabel); showExpenseTypeGUI.add(descriptionTextField);
			
			showExpenseTypeGUI.add(costPerUnitLabel); showExpenseTypeGUI.add(costPerUnitTextField);
			
			
		}else {
			costPerUnitLabel.setText("Cost per square: ");
			costPerUnitTextField.setText(String.valueOf(((Rent) expenseType).getCostPerSquare()));
			showExpenseTypeGUI.add(codeLabel); showExpenseTypeGUI.add(codeTextField);
			
			showExpenseTypeGUI.add(descriptionLabel); showExpenseTypeGUI.add(descriptionTextField);
			showExpenseTypeGUI.add(costPerUnitLabel); showExpenseTypeGUI.add(costPerUnitTextField);
		}
		
		
		JButton calculateCostForCertainExpenseTypeBtn = new JButton("Calculate cost");
		calculateCostForCertainExpenseTypeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(new JFrame(), "Cost is: "  + company.calculateCostOfSpecificExpenseType(expenseType) + "euro", "Calculate cost for certain expense type",
				        JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		});
		showExpenseTypeGUI.add(calculateCostForCertainExpenseTypeBtn);
		showExpenseTypeGUI.pack();
		showExpenseTypeGUI.setVisible(true);
		
	}
	
	
	
	private static void buildingExpenseTypeGUI (Building buildingSelected) {
		JFrame buildingExpenseTypeGUI = new JFrame("Expense Type");	
		buildingExpenseTypeGUI.setLayout(new FlowLayout());
		
		JLabel buildingCode = new JLabel("Expenses for building "  + buildingSelected.getCode() + " : ");
		buildingCode.setFont(new Font("Courier", Font.BOLD,12));
		buildingExpenseTypeGUI.add(buildingCode);
		
		DefaultListModel<String> expenseListModel = new DefaultListModel<>();
		for(Expense currentExpense : company.getExpenses()) {
			if (currentExpense.getBuilding() == buildingSelected) {
				expenseListModel.addElement(currentExpense.getType().getCode() + "|" +
						currentExpense.getMonthlyConsumption());
			}
		}
		//TODO Make it prettier.
		JList<String> expenseList = new JList(expenseListModel);
		expenseList.setVisibleRowCount(10);
		expenseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		expenseList.addMouseListener(new MouseAdapter() {
			 public void mouseClicked(MouseEvent evt) {
				 
			        if (evt.getClickCount() == 2) {
			        	int counter = 0;
			        	for (Expense currentExpense : company.getExpenses()) {
			        		if (currentExpense.getBuilding() == buildingSelected) {
			        			if (counter == expenseList.getSelectedIndex()) {
			        				editExpenseGUI(currentExpense, expenseListModel);
			        				break;
			        			}else {
			        				counter++;
			        			}
			        		}
			        	}
			        	
			        	
			            // TODO display extra information for expense.
			        } 
			 }
		 });
		
		JToolBar tb = new JToolBar();
		JButton addBtn = new JButton();
		addBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Add.png"));
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addExpenseGUI(buildingSelected, expenseListModel);
				
			}
			
		});
		JButton editExpenseBtn = new JButton();
		editExpenseBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Edit.png"));
		editExpenseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(expenseList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No expense is selected", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
					
				}else {
					int counter = 0;
		        	for (Expense currentExpense : company.getExpenses()) {
		        		if (currentExpense.getBuilding() == buildingSelected) {
		        			if (counter == expenseList.getSelectedIndex()) {
		        				editExpenseGUI(currentExpense, expenseListModel);
		        				break;
		        			}else {
		        				counter++;
		        			}
		        		}
		        	}
				
				}
			}
				
			
		});
		JButton deleteBtn = new JButton();
		deleteBtn.setIcon(new ImageIcon(System.getProperty("user.home") + "/Desktop/IconsForJavaGui/Delete.png"));
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				if(expenseList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "No expense is selected", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
				}else {
					int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Delete Expense",JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						int counter = 0;
						Expense expenseSelected = null;
			        	for (Expense currentExpense : company.getExpenses()) {
			        		if (currentExpense.getBuilding() == buildingSelected) {
			        			if (counter == expenseList.getSelectedIndex()) {
			        				expenseSelected = currentExpense;
			        				break;
			        			}else {
			        				counter++;
			        			}
			        		}
			        	}
			        	company.getExpenses().remove(expenseSelected);
			        	expenseListModel.removeAllElements();
						for(Expense currentExpense : company.getExpenses()) {
							if (currentExpense.getBuilding() == buildingSelected) {
								expenseListModel.addElement(currentExpense.getType().getCode() + "|" +
										currentExpense.getMonthlyConsumption());
							}
						}
			        	
			        	
						 
						
					}
				}
						
			}
		});
		
		buildingExpenseTypeGUI.add(expenseList);
		tb.add(addBtn);
		tb.add(editExpenseBtn);
		tb.add(deleteBtn);
		buildingExpenseTypeGUI.add(tb);
		buildingExpenseTypeGUI.pack();
		buildingExpenseTypeGUI.setVisible(true);
		
	}
	
	
	//Show extra information for the Expense.
	private static void editExpenseGUI (Expense expense, DefaultListModel<String> listmodel) {
		JFrame editExpenseGUI = new JFrame("Expense Type");	
		editExpenseGUI.setLayout(new GridLayout(2,2));
		//TODO Depend on the item show the right labels
		JLabel consumptionLabel = new JLabel("Consumption : ");
		
		//TODO UPDATE data.
		
		JTextField consumptionTextField = new JTextField(30); 
		if(expense.getType() instanceof FixedExpense ) {
			consumptionTextField.setEditable(false);
		}else {
			consumptionTextField.setText(String.valueOf(expense.getMonthlyConsumption()));
		}
		
		consumptionTextField.setBackground(Color.WHITE);
		
		
		editExpenseGUI.add(consumptionLabel); editExpenseGUI.add(consumptionTextField);
		
		
		JButton updateBtn = new JButton("UPDATE");
		updateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Double consumption = Double.parseDouble(consumptionTextField.getText());
					expense.setMonthlyConsumption(consumption);
					//TODO Refresh the list.
					listmodel.removeAllElements();
					for(Expense currentExpense : company.getExpenses()) {
						if (currentExpense.getBuilding() == expense.getBuilding()) {
							listmodel.addElement(currentExpense.getType().getCode() + "|" +
									currentExpense.getMonthlyConsumption());
						}
					}
					JOptionPane.showMessageDialog(new JFrame(),  "Consumption has been changed", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
					
					editExpenseGUI.dispatchEvent(new WindowEvent(editExpenseGUI, WindowEvent.WINDOW_CLOSING));
					
				}catch (Exception ee) {
					JOptionPane.showMessageDialog(new JFrame(),  "Consumption must be a number", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				
			}
			
		});
		
		JButton cancelBtn = new JButton("CANCEL");
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog (null,"Are you sure?","Edit EXPENSE",JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					editExpenseGUI.dispatchEvent(new WindowEvent(editExpenseGUI, WindowEvent.WINDOW_CLOSING));
				}
				
			}
			
		});
		
		
		editExpenseGUI.add(updateBtn);
		editExpenseGUI.add(cancelBtn);
		editExpenseGUI.pack();
		editExpenseGUI.setVisible(true);
		
	}
	
	
	
	private static void addExpenseGUI (Building building, DefaultListModel<String> listModel ) {
		JFrame addExpenseGUI = new JFrame("Expense Type");	
		addExpenseGUI.setLayout(new GridLayout(3,2));
		JLabel selectAItemLabel = new JLabel("Please select an expense :");
		selectAItemLabel.setFont(new Font("Courier", Font.BOLD,12));
		
		JComboBox<String> comboBoxExpenses = new JComboBox();
		
		ArrayList<ExpenseType> allExpenseTypeList = new ArrayList();
		for(ExpenseType currentExpenseType : company.getExpensesType()) {
			allExpenseTypeList.add(currentExpenseType);
		}
		
		ArrayList<ExpenseType> inUseEpenseTypeList = new ArrayList();
		for(Expense currentExpenseType : company.getExpenses()) {
			if(currentExpenseType.getBuilding() == building)
			inUseEpenseTypeList.add(currentExpenseType.getType());
		}
		
		
		for (ExpenseType currentInUseExpense : inUseEpenseTypeList) {
			try {
				allExpenseTypeList.remove(currentInUseExpense);
			}catch(Exception e) {
				
			}
		}
		
		for(ExpenseType exp : allExpenseTypeList) {
			comboBoxExpenses.addItem(exp.getCode() + " | " + exp.getDescription());
		}
		
		
		
		
		
		
		JLabel consumptionLabel = new JLabel("Consumption : ");
		JTextField consumptionTextField = new JTextField(30);
		consumptionTextField.setBackground(Color.WHITE);
		
		
		
		JButton addBtn = new JButton("ADD");
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Convert consumption into a number. add it.
				try {
					Double consumption = Double.parseDouble(consumptionTextField.getText());
					if (allExpenseTypeList.get(comboBoxExpenses.getSelectedIndex()) instanceof VariableExpense) {
						company.addBuildingExpense(building, 
						allExpenseTypeList.get(comboBoxExpenses.getSelectedIndex()), consumption);
					JOptionPane.showMessageDialog(null,  "Expense has been added", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
					
					
					Expense expenseAdded = company.getExpenses().get(company.getExpenses().size() - 1);
					listModel.addElement(expenseAdded.getType().getCode() + " | " + expenseAdded.getMonthlyConsumption());
					addExpenseGUI.dispatchEvent(new WindowEvent(addExpenseGUI, WindowEvent.WINDOW_CLOSING));
					}else {
						throw new NumberFormatException();
					}
					
					
				}catch(NumberFormatException er) {
					if (allExpenseTypeList.get(comboBoxExpenses.getSelectedIndex()) instanceof FixedExpense) {
						company.addBuildingExpense(building, 
								allExpenseTypeList.get(comboBoxExpenses.getSelectedIndex()), 0);
						JOptionPane.showMessageDialog(null,  "Expense has been added", "Expense",
						        JOptionPane.INFORMATION_MESSAGE);

						Expense expenseAdded = company.getExpenses().get(company.getExpenses().size() - 1);
						listModel.addElement(expenseAdded.getType().getCode() + " | " + 0);
						addExpenseGUI.dispatchEvent(new WindowEvent(addExpenseGUI, WindowEvent.WINDOW_CLOSING));
					}else {
						JOptionPane.showMessageDialog(null,  "Please give a valid consumption", "Expense",
					        JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
				
			}
			
		});
		
		JButton cancelBtn = new JButton("CANCEL");
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		
		addExpenseGUI.add(selectAItemLabel); addExpenseGUI.add(comboBoxExpenses);
		addExpenseGUI.add(consumptionLabel); addExpenseGUI.add(consumptionTextField);
		addExpenseGUI.add(addBtn); addExpenseGUI.add(cancelBtn);
		addExpenseGUI.pack();
		addExpenseGUI.setVisible(true);
		
	}
	
	private static void updateListModel(Company company, String type) {
		if ( type == "buildings") {
			buildingListModel.removeAllElements();
			for (Building currentBuilding : company.getBuildings()) {
				
				buildingListModel.addElement(currentBuilding.getCode() + " | " + currentBuilding.getDescription()+
					"                                                                               ");
			
			}
		}
	}
	
	
}
