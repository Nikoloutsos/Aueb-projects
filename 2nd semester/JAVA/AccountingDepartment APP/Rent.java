
public class Rent extends FixedExpense {
	public Rent(String code, String description, double costPerSquare) {
		super(code, description, costPerSquare );
	}
	@Override
	public double calculateCost(Expense exp) {
		return exp.getBuilding().getSquareMeters()*getCostPerSquare();
	}
	
	@Override
	public String toString() {
		return "Code : " + this.getCode() + "\n"
				+ "Description: " + this.getDescription() + "\n"
						+ "Cost Per Sqare (m^2): " + this.getCostPerSquare();
								
	}
}
