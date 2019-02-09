
public class Telephone extends VariableExpense{
	private double monthlyCostTelephone;
	public Telephone(String code, String description, double monthlyfee, double costPerUnit, double monthlyCostTelephone) {
		super(code, description, costPerUnit, "min", monthlyfee);
		this.monthlyCostTelephone = monthlyCostTelephone;
		
	}
	
	@Override
	public double calculateCost(Expense exp) {
		return getMonthlyCostTelephone() + getMonthlyFee() + getCostPerUnit()*exp.getMonthlyConsumption();
		
	}
	public double getMonthlyCostTelephone() {
		return monthlyCostTelephone;
	}
	
	@Override
	public String toString() {
		return "Code : " + this.getCode() + "\n"
				+ "Description: " + this.getDescription() + "\n"
						+ "Monthly fee: " + this.getMonthlyFee() + "\n"
								+ "Cost Per Unit(minutes): " + this.getCostPerUnit() + "\n"
										+ "Monthly Cost Telephone: " + this.getMonthlyCostTelephone();
	}

}
