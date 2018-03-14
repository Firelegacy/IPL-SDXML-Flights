package domaine;

public class Route {

	private String airline;
	private String source;
	private String destination;
	
	public Route(String airline,String source,String destination){
		this.airline=airline;
		this.source=source;
		this.destination=destination;
	}

	public String getAirline() {
		return airline;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}	
}
