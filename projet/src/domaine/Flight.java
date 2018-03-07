package domaine;

public class Flight {
	
	private Airline airline_id;
	private Airport airportSource_id;
	private Airport airportDestination_id;
	
	public Flight(Airline airline_id, Airport airportSource_id, Airport airportDestination_id) {
		super();
		this.airline_id = airline_id;
		this.airportSource_id = airportSource_id;
		this.airportDestination_id = airportDestination_id;
	}

	public Airline getAirline_id() {
		return airline_id;
	}

	public Airport getAirportSource_id() {
		return airportSource_id;
	}

	public Airport getAirportDestination_id() {
		return airportDestination_id;
	}
}
