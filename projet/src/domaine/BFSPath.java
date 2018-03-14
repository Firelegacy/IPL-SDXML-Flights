package domaine;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class BFSPath {

	Map<String, Set<Route>> routes;

	public BFSPath(Map<String, Set<Route>> routes) {
		this.routes = routes;
	}

	public ArrayDeque<Route> findPath(String source, String destination) {
		// Cle est le iata de l'aeroport, valeur est le iata precedent
		Map<String, Route> precedents = new HashMap<>();
		// Deque et Set d'iata des aeroports
		Deque<String> file = new LinkedList<>();
		Set<String> registeredAirports = new HashSet<>();
		// Sert a annoncer qu'on a atteind la destination
		boolean found = false;

		// Traiter l'aeroport source
		file.push(source);
		registeredAirports.add(source);
		precedents.put(source, null);

		String currentAirport;

		do {
			currentAirport = file.pop();
			if (currentAirport.equals(destination)) {
				found = true;
				break;
			}
			registeredAirports.add(currentAirport);
			Set<Route> chemins = routes.get(currentAirport);
			if (chemins != null) {
				for (Route route : chemins) {
					String airportDest = route.getDestination();
					if (!registeredAirports.contains(airportDest) && !precedents.containsKey(airportDest)) {
						file.add(airportDest);
						precedents.put(airportDest, route);
					}
				}
			}
		} while (!file.isEmpty() && !found);

		// traitement du retour
		if (found) {
			ArrayDeque<Route> chemin = new ArrayDeque<>();
			String aeroportCourant = destination;
			Route routeCourante = precedents.get(destination);
			do{
				chemin.push(routeCourante);
				aeroportCourant = routeCourante.getSource();
				routeCourante = precedents.get(aeroportCourant);
			}while(!aeroportCourant.equals(source));
			return chemin;
		} else {
			return null;
		}
	}
}
