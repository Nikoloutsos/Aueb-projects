
public abstract class FixedExpense extends ExpenseType {
	private double costPerSquare;
	public FixedExpense(String code, String description, double costPerSquare) {
		super(code, description);
		this.costPerSquare = costPerSquare;
		
	}
	public double getCostPerSquare() {
		return costPerSquare;
	}
	
	public void setCostPerSquare(double costPerSquare) {
		this.costPerSquare = costPerSquare;
	}
	
}
