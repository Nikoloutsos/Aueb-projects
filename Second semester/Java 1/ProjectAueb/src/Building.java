
public class Building {
	private Company company;
	private String code;
	private String description;
	private String address;
	private double zoneCost;
	private double squareMeters;
	
	public Building(Company company, String code, String description, String address, double zoneCost, double squareMeters) {
		this.company = company;
		this.code = code;
		this.description = description;
		this.address = address;
		this.zoneCost = zoneCost;
		this.squareMeters = squareMeters;
	}
	
	
	
	public double getSquareMeters() {
		return squareMeters;
	}
	public String getAddress() {
		return address;
	}
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	public double getZoneCost() {
		return zoneCost;
	}
	public Company getCompany() {
		return company;
	}
	
	public void setCode(String code) {
		// Make sure that every code is unique.
		if (company.codeAlreadyExists(code)) {
			System.out.println("Sorry but another building has the same code! \n"
					+ " Please choose another code..");
			
		}else {
			this.code = code;
		}
	}
	
	
@Override
public String toString() {
	return "code : " + getCode() + "\n"
			+ "description : " + getDescription() + "\n"
					+ "address : " + getAddress() ;
	
}
}
