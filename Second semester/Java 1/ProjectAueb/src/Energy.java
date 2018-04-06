
public class Energy extends VariableExpense {
	private double monthlyCostERT;
	public Energy(String code, String description, double monthlyfee, double costperUnit, double monthlyCostERT) {
		// TODO Auto-generated constructor stub
		super(code, description, costperUnit, "kwh", monthlyfee);
		this.monthlyCostERT = monthlyCostERT;
		
	}
	
	@Override
	public double calculateCost(Expense exp) {
		return getMonthlyFee() + getMonthlyCostERT() + 
				exp.getBuilding().getZoneCost()*exp.getBuilding().getSquareMeters() +
				getCostPerUnit()*exp.getMonthlyConsumption();
	}
	
	public double getMonthlyCostERT() {
		return monthlyCostERT;
	}
}
