
public class Water extends VariableExpense {
	private double secondaryCostPerUnit;
	
	public Water(String code, String description, double monthlyFee, double costPerUnit, double secondaryCostPerUnit) {
		super(code, description, costPerUnit, "m^3", monthlyFee);
		this.secondaryCostPerUnit = secondaryCostPerUnit;
		
		
	}
	
	
	@Override
	public double calculateCost(Expense exp) {
		if (exp.getMonthlyConsumption() <= 100 ) {
			return getMonthlyFee() + getCostPerUnit()*exp.getMonthlyConsumption();
			
		}else return getMonthlyFee() + getSecondaryCostPerUnit()*exp.getMonthlyConsumption();
	}
	
	public void setSecondaryCostPerUnit(double secondaryCostPerUnit) {
		this.secondaryCostPerUnit = secondaryCostPerUnit;
	}
	public double getSecondaryCostPerUnit() {
		return secondaryCostPerUnit;
	}
	
	@Override
	public String toString() {
		return "Code : " + this.getCode() + "\n"
				+ "Description: " + this.getDescription() + "\n"
						+ "Monthly fee: " + this.getMonthlyFee() + "\n"
								+ "Cost Per Unit(m^3): " + this.getCostPerUnit() + "\n"
										+ "Secondary Cost Per Unit: " + this.getSecondaryCostPerUnit();
	}
}
