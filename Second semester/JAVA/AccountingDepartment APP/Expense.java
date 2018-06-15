
public class Expense {
	private Building building;
	private ExpenseType type;
	private double monthlyConsumption;

	public Expense(Building building, ExpenseType type, double monthlyConsumption) {
		this.building = building;
		this.type = type;
		this.monthlyConsumption = monthlyConsumption;
	}
	
	public double calculateExpense() {
		return type.calculateCost(this);
		
		
	}
	public ExpenseType getType() {
		return type;
	}
	public Building getBuilding() {
		return building;
	}
	public double getMonthlyConsumption() {
		return monthlyConsumption;
	}
	
	public void setMonthlyConsumption(double monthlyConsumption) {
		this.monthlyConsumption = monthlyConsumption;
	}
}
