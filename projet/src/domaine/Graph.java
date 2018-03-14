package domaine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import exceptions.NoRouteBetweenSourceAndDestination;

public class Graph {

	private Map<String, Airline> airlines;
	private Map<String, Airport> airports;
	private Map<String, Set<Route>> routes;
	private BFSPath bfs;

	public Graph(Map<String, Airline> airlines, Map<String, Airport> airports, Map<String, Set<Route>> routes) {
		super();
		this.airlines = airlines;
		this.airports = airports;
		this.routes = routes;
		this.bfs = new BFSPath(routes);
	}

	// BFS
	public void calculerItineraireMinimisantNombreVol(String source, String destination, String fileToSave)
			throws NoRouteBetweenSourceAndDestination {
		Deque<Route> chemin = bfs.findPath(source, destination);
		if (chemin == null) {
			// pas de connexion entre les aeroport
			throw new NoRouteBetweenSourceAndDestination();
		} else {
			// ecrire dans le fichier
			writeFile(chemin, fileToSave);
		}
	}

	// DIJKSTRA
	public void calculerItineraireMiniminantDistance(String source, String destination, String fileToSave) {
		// TODO Auto-generated method stub

	}

	// Crée un fichier xml et le remplis avec le chemin trouvé
	private void writeFile(Deque<Route> chemin, String file) {
		Route r;
		do {
			r = chemin.pop();
			System.out.println("Vol (iata : " + r.getAirline() + "): " + r.getSource() + "-" + r.getDestination());
		} while (!chemin.isEmpty());
	}
}
