import java.util.Scanner;

public class AccountingDepartmentApp {

	public static void main(String[] args) {
		//Initiation
		Company adidas = new Company("Adidas"); //Make a company.
		init(adidas); // Add some dummy data.		
		
		//MENU
		Scanner sc = new Scanner(System.in); 
		int answer = 0;
		while (answer!= 7) {
			printMenuOptions();
			String code, description, address;
			int zoneCost, squareMeters, subAnswer;
			System.out.println("Choose one of the following options: \n");
			answer = sc.nextInt();
			sc.nextLine();
			switch (answer) {
			case 1:
				System.out.println("Code: ");
				code = sc.nextLine();
				System.out.println("Description: ");
				description = sc.nextLine();
				System.out.println("Address: ");
				address = sc.nextLine();
				System.out.println("zonecost (euro): ");
				zoneCost = sc.nextInt();
				System.out.println("square meters (m^2): ");
				squareMeters = sc.nextInt();

				adidas.addBuilding(code, description, address, zoneCost, squareMeters); 
				System.out.println("Building has been succesfully added to the company");
				break; //OK
			case 2:
				double monthlyConsumption, monthlyFee, costPerUnit, secondaryCostPerUnit, monthlyCostERT, monthlyCostTelephone,
				costPerSquare;
				String code2;
				adidas.printAllBuildings();
				System.out.println("Please choose one of the following buildings");
				System.out.println("Enter the code of the building : ");
				code = sc.nextLine();
				if(adidas.findBuildingByCode(code) != -1) {
					System.out.println("Please choose one of the following Expense Type : ");
					System.out.println("Press 1 for: Water \n"
							+ "Press 2 for: Energy \n"
							+ "Press 3 for: Telephone \n"
							+ "Press 4 for: Rent \n"
							+ "Press 5 for Cleaning");
					subAnswer = sc.nextInt();
					sc.nextLine();
					switch (subAnswer) {
						case 1:
							System.out.println("Code: ");
							code2 = sc.nextLine();
							System.out.println("Description: ");
							description = sc.nextLine();
							System.out.println("Enter monthly fee : ");
							monthlyFee = sc.nextInt();
							System.out.println("Enter cost per unit (m^3) : ");
							costPerUnit = sc.nextInt();
							System.out.println("Enter secondary cost per unit (m^3) : ");
							secondaryCostPerUnit = sc.nextInt();
							System.out.println("Enter monthly consumption (m^3): ");
							monthlyConsumption = sc.nextInt();
							if (!adidas.codeAlreadyExistsAtExpensesType(code2)) {
								adidas.addExpenseTypeWater(code2, description, monthlyFee, costPerUnit, secondaryCostPerUnit);
								adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode(code)),
								adidas.getExpensesType().get(adidas.findExpenseTypeByCode(code2)), monthlyConsumption);
								System.out.println("New expense has been succesfully created for the building");
							}else {
								System.out.println("Code you have written in already in use");
							}
							break;
						case 2:
							System.out.println("Code: ");
							code2 = sc.nextLine();
							System.out.println("Description: ");
							description = sc.nextLine();
							System.out.println("Enter monthly fee : ");
							monthlyFee = sc.nextInt();
							System.out.println("Enter cost per unit (kwh) : ");
							costPerUnit = sc.nextInt();
							System.out.println("Enter secondary cost per unit (kwh) : ");
							monthlyCostERT = sc.nextInt();
							System.out.println("Enter monthly consumption (kwh): ");
							monthlyConsumption = sc.nextInt();
							if (!adidas.codeAlreadyExistsAtExpensesType(code2)) {
							adidas.addExpenseTypeEnergy(code2, description, monthlyFee, costPerUnit, monthlyCostERT);
							adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode(code)),
									adidas.getExpensesType().get(adidas.findExpenseTypeByCode(code2)), monthlyConsumption);
							System.out.println("New expense has been succesfully created for the building");
							}else {
								System.out.println("Code you have written in already in use");
							}
							break;
						case 3:
							System.out.println("Code: ");
							code2 = sc.nextLine();
							System.out.println("Description: ");
							description = sc.nextLine();
							System.out.println("Enter monthly fee : ");
							monthlyFee = sc.nextInt();
							System.out.println("Enter cost per unit (minutes) : ");
							costPerUnit = sc.nextInt();
							System.out.println("Enter secondary cost per unit (minutes) : ");
							monthlyCostTelephone = sc.nextInt();
							System.out.println("Enter monthly consumption (minutes): ");
							monthlyConsumption = sc.nextInt();
							if (!adidas.codeAlreadyExistsAtExpensesType(code2)) {
								adidas.addExpenseTypeTelephone(code2, description, monthlyFee, costPerUnit, monthlyCostTelephone);
								adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode(code)),
								adidas.getExpensesType().get(adidas.findExpenseTypeByCode(code2)), monthlyConsumption);
								System.out.println("New expense has been succesfully created for the building");
							}else {
								System.out.println("Code you have written in already in use");
							}
							break;
						case 4:
							System.out.println("Code: ");
							code = sc.nextLine();
							System.out.println("Description: ");
							description = sc.nextLine();
							System.out.println("Enter cost per square(m^2) : ");
							costPerSquare = sc.nextInt();
							if (!adidas.codeAlreadyExistsAtExpensesType(code)) {
								adidas.addExpenseTypeRent(code, description, costPerSquare);
								adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode(code)),
								adidas.getExpensesType().get(adidas.findExpenseTypeByCode(code)), 0);
								System.out.println("New expense has been succesfully created for the building");
							}else {
								System.out.println("Code you have written in already in use");
							}
							break;
						case 5:
							System.out.println("Code: ");
							code2 = sc.nextLine();
							System.out.println("Description: ");
							description = sc.nextLine();
							System.out.println("Enter cost per square(m^2) : ");
							costPerSquare = sc.nextInt();
							if (!adidas.codeAlreadyExistsAtExpensesType(code2)) {
								adidas.addExpenseTypeCleaning(code2, description, costPerSquare);
								adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode(code)),
								adidas.getExpensesType().get(adidas.findExpenseTypeByCode(code2)), 0);
							System.out.println("New expense has been succesfully created for the building");
							}else {
								System.out.println("Code you have written in already in use");
							}
							break;
						default: System.out.println("'" + subAnswer +"'" + " is not an option, please try again!");
						}
						break;
				}else {
					System.out.println("No buildings found with this code.Please try again");
				}
				break;
			case 3:
				adidas.printAllBuildings();
				break; //OK
			case 4:
				adidas.printAllBuildings();
				System.out.println("Please choose one of the following buildings");
				System.out.println("Enter the code of the building : ");
				code = sc.nextLine();
				if(adidas.findBuildingByCode(code) != -1) {
					adidas.printExpensesOfBuilding(adidas.getBuildings().get(adidas.findBuildingByCode(code)));
				}else {
					System.out.println("No buildings found with this code.Please try again");
				}
				break; // Needs to modify the printed text.
			case 5: 
				adidas.printAllBuildings();
				System.out.println("Please choose one of the following buildings");
				System.out.println("Enter the code of the building : ");
				code = sc.nextLine();
				if (adidas.findBuildingByCode(code) != -1) {
					System.out.println("Total cost has been calculated :" +
					adidas.calculateBuildingCost(adidas.getBuildings().get(adidas.findBuildingByCode(code))) + " euro");
				}else {
					System.out.println("No buildings found with this code.Please try again");
				}
				
				break; //OK
			case 6:
				System.out.println("Please choose one of the following Expense Type : ");
				System.out.println("Press 1 for: Water \n"
						+ "Press 2 for: Energy \n"
						+ "Press 3 for: Telephone \n"
						+ "Press 4 for: Rent \n"
						+ "Press 5 for Cleaning");
				subAnswer = sc.nextInt();
				sc.nextLine();
				switch (subAnswer) {
				case 1:
					System.out.println("Total cost of water for company is : " + adidas.calculateCompanyExpenseType("water"));
					break;
				case 2:
					System.out.println("Total cost of energy for company is : " + adidas.calculateCompanyExpenseType("energy"));
					break;
				case 3:
					System.out.println("Total cost of telephone for company is : " + adidas.calculateCompanyExpenseType("telephone"));
					break;
				case 4:
					System.out.println("Total cost of rent for company is : " + adidas.calculateCompanyExpenseType("rent"));
					break;
				case 5:
					System.out.println("Total cost of cleaning for company is : " + adidas.calculateCompanyExpenseType("cleaning"));
					break;
				default: System.out.println("This is not an option");
				}
				break; //OK
			case 7:
				sc.close();
				break; //OK
			default:
				System.out.println("'" + answer +"'" + " is not an option, please try again!");
			}
		}
		

	}
	private static void printMenuOptions() {
		System.out.println("--------------------------------------------------------------");
		System.out.println("\tPress 1 for: adding a new building");
		System.out.println("\tPress 2 for: adding a new expense");
		System.out.println("\tPress 3 for: displaying the buildings");
		System.out.println("\tPress 4 for: displaying building's expenses");
		System.out.println("\tPress 5 for: calculate a building's total cost");
		System.out.println("\tPress 6 for: calculate the cost of a certain expenseType for the company");
		System.out.println("\tPress 7 for: exiting the Application");
		System.out.println("--------------------------------------------------------------");

	}
	
	private static void init(Company adidas) {
		adidas.addBuilding("001", "this is the description of building 001", 
				"Alexandras Avenue", 7, 170);
		adidas.addBuilding("002", "this is the description of building 002",
				"Axarnwn 51", 5, 100);
		adidas.addBuilding("003", "this is the description of building 003",
				"Megalonhswn 11", 2, 155);
		adidas.addBuilding("004", "this is the description of building 004",
				"kyparissou 66", 12, 224);
		adidas.addBuilding("005", "this is the description of building 005",
				"gyzi 13", 7, 120); //Add buildings to the company.
		
		adidas.addExpenseTypeRent("R 001", "description of code R 001", 20); 
		adidas.addExpenseTypeRent("R 002", "description of code R 002", 12);
		adidas.addExpenseTypeRent("R 003", "description of code R 003", 20);
		adidas.addExpenseTypeRent("R 004", "description of code R 004", 7);
		adidas.addExpenseTypeRent("R 005", "description of code R 005", 18); 
		
		adidas.addExpenseTypeCleaning("C 001", "description of code C 001", 12);
		adidas.addExpenseTypeCleaning("C 002", "description of code C 002", 15);
		adidas.addExpenseTypeCleaning("C 003", "description of code C 003", 17);
		adidas.addExpenseTypeCleaning("C 004", "description of code C 004", 22);
		adidas.addExpenseTypeCleaning("C 005", "description of code C 005", 8);
		
		adidas.addExpenseTypeWater("W 001", "description of code W 001", 40, 0.5, 0.4);
		adidas.addExpenseTypeWater("W 002", "description of code W 002", 50, 0.5, 0.4);
		adidas.addExpenseTypeWater("W 003", "description of code W 003", 65, 1, 0.4);
		adidas.addExpenseTypeWater("W 004", "description of code W 004", 25, 0.5, 0.4);
		adidas.addExpenseTypeWater("W 005", "description of code W 005", 88, 0.5, 0.4);
		
		adidas.addExpenseTypeEnergy("E 001", "description of code E 001", 20, 0.8, 7);
		adidas.addExpenseTypeEnergy("E 002", "description of code E 002", 50, 0.8, 7);
		adidas.addExpenseTypeEnergy("E 003", "description of code E 003", 33, 0.8, 7);
		adidas.addExpenseTypeEnergy("E 004", "description of code E 004", 40, 0.8, 7);
		adidas.addExpenseTypeEnergy("E 005", "description of code E 005", 15, 0.8, 7); // Make expenseType objects
		
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("001")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("R 001")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("002")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("R 002")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("003")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("R 003")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("004")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("R 004")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("005")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("R 005")), 0);
		
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("001")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("C 001")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("002")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("C 002")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("003")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("C 003")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("004")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("C 004")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("005")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("C 005")), 0);
		
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("001")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("W 001")), 300);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("002")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("W 002")), 311);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("003")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("W 003")), 444);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("004")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("W 004")), 346);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("005")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("W 005")), 100);
		
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("001")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("E 001")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("002")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("E 002")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("003")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("E 003")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("004")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("E 004")), 0);
		adidas.addBuildingExpense(adidas.getBuildings().get(adidas.findBuildingByCode("005")), 
				adidas.getExpensesType().get(adidas.findExpenseTypeByCode("E 005")), 0); //Make Expense objects
		
	}
	

}
