import java.util.ArrayList;

public class Company {
	private String name;
	private ArrayList<Building> buildings;
	private ArrayList<Expense> expenses;
	private ArrayList<ExpenseType> expensesType;
	
	//Constructor
	public Company(String name) {
		this.name = name;
		this.buildings = new ArrayList<Building>();
		this.expenses = new ArrayList<Expense>();
		this.expensesType = new ArrayList<ExpenseType>();
	}
	//Add to the company
	public void addBuilding(String code, String description, String address, double zoneCost, double squareMeters) {
		if (!codeAlreadyExists(code)) {
			Building newbuilding =  new Building(this, code, description, address, zoneCost, squareMeters );
			buildings.add(newbuilding);
		}else {
			System.out.println("We are sorry but this code is already in use,try another one!");
			}
	}
	public void addExpenseTypeWater(String code, String description, double monthlyFee, double costPerUnit, double extra) {
		if (!codeAlreadyExistsAtExpensesType(code)) {
		expensesType.add(new Water(code, description, monthlyFee, costPerUnit, extra ));}
		else {
			System.out.println("We are sorry but this code is already in use,try another one!");
		}
		}
	
	public void addExpenseTypeTelephone(String code, String description, double monthlyFee, double costPerUnit, double extra) {
		if (!codeAlreadyExistsAtExpensesType(code)) {
		expensesType.add(new Telephone(code, description, monthlyFee, costPerUnit, extra ));
		}else {
			System.out.println("We are sorry but this code is already in use,try another one!");
		}
	}
	
	public void addExpenseTypeEnergy(String code, String description, double monthlyFee, double costPerUnit, double extra) {
		if (!codeAlreadyExistsAtExpensesType(code)) {
		expensesType.add(new Energy(code, description, monthlyFee, costPerUnit, extra ));
		}else {
			System.out.println("We are sorry but this code is already in use,try another one!");
		}
	}
	public void addExpenseTypeRent(String code, String description, double costPerSquare) {
		if (!codeAlreadyExistsAtExpensesType(code)) {
		expensesType.add(new Rent(code, description, costPerSquare));
		}else {
			System.out.println("We are sorry but this code is already in use,try another one!");
		}
	}
	public void addExpenseTypeCleaning(String code, String description, double costPerSquare) {
		if (!codeAlreadyExistsAtExpensesType(code)) {
		expensesType.add(new Cleaning(code, description, costPerSquare));
		}else {
			System.out.println("We are sorry but this code is already in use,try another one!");
		}
	}
	
	public void addBuildingExpense(Building building, ExpenseType type, double monthlyConsumption) {
		expenses.add(new Expense(building, type, monthlyConsumption));
	}
	//Print methods.
	public void printAllBuildings() {
		if (buildings.size() == 0) System.out.println("Company named" + this.getName() + "has not any buildings yet!");
		System.out.println("Company named " + this.getName() + " has " + this.getBuildings().size() + " buildings:");
		System.out.println("--------------------------------------------------------------");
		for (Building currentBuilding : buildings) {
			System.out.println(currentBuilding);
			System.out.println("");
		}
		System.out.println("--------------------------------------------------------------");

	}
	public void printExpensesOfBuilding(Building building) {
		System.out.println("The building " + building + "Has the following expenses:");
		System.out.println("");
		for(Expense currentExpense : expenses) {
			if (currentExpense.getBuilding() == building) {
				System.out.println(currentExpense.getType().getClass());
			}
			
		}
	}
	
	// Methods about checking
	public boolean codeAlreadyExistsAtExpensesType(String code) {
		if (expensesType.size() == 0) return false;
		for (ExpenseType currentExpenseType : expensesType) {
			if (currentExpenseType.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}
	public boolean codeAlreadyExists(String code) {

		if (buildings.size() == 0) return false;
		for (Building currentBuilding : buildings) {
			if (currentBuilding.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}
	// Calculation methods
	public double calculateBuildingCost(Building building) {
		double sum = 0;
		for (Expense currentExpense : expenses) {
			if (currentExpense.getBuilding() == building) {
				sum += currentExpense.getType().calculateCost(currentExpense);			}
		}
		return sum;
	}
	public double calculateCompanyExpenseType(String type) {
		double sum = 0;
		for (Expense currentExpense : expenses) {
			
			
			ExpenseType currentExpenseType = currentExpense.getType();
			if (currentExpenseType instanceof Water &&  type.equals("water") ) {
				sum += currentExpenseType.calculateCost(currentExpense);
				
			}else if (currentExpenseType instanceof Energy &&  type.equals("energy")) {
				sum += currentExpenseType.calculateCost(currentExpense);
				
			}else if (currentExpenseType instanceof Telephone &&  type.equals("telephone")) {
				sum += currentExpenseType.calculateCost(currentExpense);
				
			}else if(currentExpenseType instanceof Rent &&  type.equals("rent")) {
				sum += currentExpenseType.calculateCost(currentExpense);
			}else if (currentExpenseType instanceof Cleaning &&  type.equals("cleaning")) {
				sum += currentExpenseType.calculateCost(currentExpense);
			}			
		}
		return sum;
		
	
	}
	//Methods for finding
	public int findBuildingByCode(String code) {
		for (Building currentBuilding : buildings ) {
			if (currentBuilding.getCode().equals(code)) {
				return buildings.indexOf(currentBuilding);
			}
		}
		return -1;
	}
	public int findExpenseTypeByCode(String code) {
		for (ExpenseType currentExpenseType : expensesType ) {
			if (currentExpenseType.getCode().equals(code)) {
				return expensesType.indexOf(currentExpenseType);
			}
		}
		return -1;
	}

	//Getters	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}
	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
	public ArrayList<ExpenseType> getExpensesType() {
		return expensesType;
	}
	public String getName() {
		return name;
	}
}

