package domaine;

public class Airport {
	
	private String iata;
	private String name;
	private String city;
	private String country;
	private Double longitude;
	private Double latitude;
	
	public Airport(String iata, String name, String city, String country) {
		super();
		this.iata = iata;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
