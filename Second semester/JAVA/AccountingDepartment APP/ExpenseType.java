
public abstract class ExpenseType {
	private String code;
	private String description;
	public ExpenseType(String code, String description) {
		this.code = code;
		this.description = description;
	
	}
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	
	public abstract double calculateCost(Expense exp);
	
}
