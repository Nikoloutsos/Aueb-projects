
public class Telephone extends VariableExpense{
	private double monthlyCostTelephone;
	public Telephone(String code, String description, double monthlyfee, double costPerUnit, double monthlyCostTelephone) {
		// TODO Auto-generated constructor stub
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

}
