
public class Building {
	private String code;
	private String description;
	private String address;
	private double zoneCost;
	private double squareMeters;
	
	public Building(String code, String description, String address, double zoneCost, double squareMeters) {
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
	public void setCode(String code) {
		this.code = code;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSquareMeters(double squareMeters) {
		this.squareMeters = squareMeters;
	}
	
	public void setZoneCost(double zoneCost) {
		this.zoneCost = zoneCost;
	}
	
	
	
@Override
public String toString() {
	return "code : " + getCode() + "\n"
			+ "description : " + getDescription() + "\n"
					+ "address : " + getAddress() ;
	
}
}
