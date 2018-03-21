package domaine;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.NoRouteBetweenSourceAndDestination;
import utils.Util;
import writersParsers.XMLWriter;

public class Graph {

	private Map<String, Airline> airlines;
	private Map<String, Airport> airports;
	private Map<String, Set<Route>> routes;
	private BFSPath bfs;
	private XMLWriter writer;
	private DijkstraPath dijkstra;

	public Graph(Map<String, Airline> airlines, Map<String, Airport> airports, Map<String, Set<Route>> routes) {
		super();
		this.airlines = airlines;
		this.airports = airports;
		this.routes = routes;
		this.bfs = new BFSPath(routes);
		this.dijkstra = new DijkstraPath(routes, airports);
		this.writer = new XMLWriter(airlines, airports, routes);
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
			writer.writeXMLResultFile(fileToSave, chemin);
		}
	}

	// DIJKSTRA
	public void calculerItineraireMiniminantDistance(String source, String destination, String fileToSave) throws NoRouteBetweenSourceAndDestination {
		Deque<Route> chemin = dijkstra.findPath(source, destination);
		if (chemin == null) {
			// pas de connexion entre les aeroport
			throw new NoRouteBetweenSourceAndDestination();
		} else {
			// ecrire dans le fichier
			writer.writeXMLResultFile(fileToSave, chemin);
		}
	}
}
