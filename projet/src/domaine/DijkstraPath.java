package domaine;

import static utils.Util.distance;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DijkstraPath {
	private Map<String, Set<Route>> routes;
	private Map<String, Airport> airports;

	public DijkstraPath(Map<String, Set<Route>> routes, Map<String, Airport> airports) {
		this.routes = routes;
		this.airports = airports;
	}

	public ArrayDeque<Route> findPath(String src, String dst) {
		Map<Airport, Double> etiquettesProvisoires = new HashMap<>();
		Map<Airport, Double> etiquettesDefinitives = new HashMap<>();
		Map<Airport, Route> parents = new HashMap<>();
		// Parent array to store shortest path tree
		Airport source = airports.get(src);
		Airport destination = airports.get(dst);
		Airport aeroportCourant = source;
		etiquettesProvisoires.put(aeroportCourant, 0.0);
		while (aeroportCourant != destination) {
			Set<Route> r = routes.get(aeroportCourant.getIata());
			if (r != null && !r.isEmpty()) { // si pas de routes sortantes de l'aeroport courant
				for (Route route : r) {
					Airport dest = airports.get(route.getDestination());
					double distance = etiquettesProvisoires.get(aeroportCourant)
							+ distance(aeroportCourant.getLatitude(), aeroportCourant.getLongitude(),
									dest.getLatitude(), dest.getLongitude());
					if (etiquettesProvisoires.containsKey(dest)) {
						if (distance < etiquettesProvisoires.get(dest)) {
							etiquettesProvisoires.replace(dest, distance);
							parents.replace(dest, route);
						}
					} else if (!etiquettesProvisoires.containsKey(dest) && !etiquettesDefinitives.containsKey(dest)) {
						etiquettesProvisoires.put(dest, distance);
						parents.put(dest, route);
					}
				}
			}
			etiquettesDefinitives.put(aeroportCourant, etiquettesProvisoires.get(aeroportCourant));
			etiquettesProvisoires.remove(aeroportCourant);

			if (etiquettesProvisoires.isEmpty()) { // pas de chemin
				return null;
			} else {
				Entry<Airport, Double> min = Collections.min(etiquettesProvisoires.entrySet(),
						Comparator.comparing(Entry::getValue));
				aeroportCourant = min.getKey();
			}
		}
		ArrayDeque<Route> routeSrcDest = new ArrayDeque<>();
		Airport a = aeroportCourant;
		while(a!=source) {
			Route parent = parents.get(a);
			routeSrcDest.push(parents.get(airports.get(parent.getDestination())));
			a = airports.get(parent.getSource());
		}
		System.out.println(etiquettesProvisoires.get(aeroportCourant) + "(Attendue :15692.73)");
		return routeSrcDest;
	}
}
