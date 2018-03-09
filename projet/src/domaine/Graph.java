package domaine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
	
	private Map<String, Airline> airlines;
	private Map<String, Airport> airports;
	private Map<String, Set<Route>> routes;

	public Graph(Map<String, Airline> airlines, Map<String, Airport> airports, Map<String, Set<Route>> routes) {
		super();
		this.airlines = airlines;
		this.airports = airports;
		this.routes = routes;
	}
	
	//BFS
	public void calculerItineraireMinimisantNombreVol(String source, String destination, String string3) {
		List<Airport> file = new LinkedList<>();
		Set<Airport> airportsToAdd = new HashSet<>();
		
		
	}

	//DIJKSTRA
	public void calculerItineraireMiniminantDistance(String source, String destination, String string3) {
		// TODO Auto-generated method stub
		
	}
}
