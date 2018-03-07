package domaine;

public class Airline {
	
	private String iata;
	private String country;
	
	public Airline(String iata, String country) {
		super();
		this.iata = iata;
		this.country = country;
	}

	public String getIata() {
		return iata;
	}

	public String getCountry() {
		return country;
	}
}
