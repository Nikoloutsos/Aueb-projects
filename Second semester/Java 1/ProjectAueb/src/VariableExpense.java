
public abstract class VariableExpense extends ExpenseType {
	private double costPerUnit;
	private String unit;
	private double monthlyFee;
	public  VariableExpense(String code, String description, double costPerUnit, String unit, double monthlyFee) {
		super(code, description);
		this.costPerUnit = costPerUnit;
		this.unit = unit;
		this.monthlyFee = monthlyFee;
	}
	





	// Setters and Getters.
	public void setUnit(String unit) {
		this.unit = unit;
	}
	 public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	 
	 public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
	 public double getCostPerUnit() {
		return costPerUnit;
	}
	 public double getMonthlyFee() {
		return monthlyFee;
	}
	 public String getUnit() {
		return unit;
	}

}
