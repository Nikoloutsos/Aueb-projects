
public class Rent extends FixedExpense {
	public Rent(String code, String description, double costPerSquare) {
		super(code, description, costPerSquare );
	}
	
	@Override
	public double calculateCost(Expense exp) {
		return exp.getBuilding().getSquareMeters()*getCostPerSquare();
	}
}
