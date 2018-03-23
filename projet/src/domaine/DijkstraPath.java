package domaine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static utils.Util.distance;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import utils.Util;

public class DijkstraPath {
	Map<String, Set<Route>> routes;
	Map<String, Airport> airports;

	public DijkstraPath(Map<String, Set<Route>> routes, Map<String, Airport> airports) {
		this.routes = routes;
		this.airports = airports;
	}

	public ArrayDeque<Route> findPath(String source, String destination) {
		// Map des indices pour les etiquettes provisoires et definitives (les indices
		// commencent à 0)
		Map<String, Integer> indicesEtiquettesParAirport = new HashMap<>();
		Map<Integer, String> airportEtiquettesParIndice = new HashMap<>();
		int indice = 0;
		for (String airport : airports.keySet()) {
			indicesEtiquettesParAirport.put(airport, indice);
			airportEtiquettesParIndice.put(indice, airport);
			indice++;
		}
		/* Structures Dijkstra (les indices correspondent à la map*/
		// indicesEtiquettesParAirport
		List<Double> etiquettesDefinitives = new ArrayList<>();
		List<Double> etiquettesProvisoires = new ArrayList<>();
		// Sera passé à true quand la destination sera devenue le noeud courant
		boolean cheminTrouve = false;
		// Init à MAX des deux listes d'etiquettes
		for (int i = 0; i < airports.size(); i++) {
			etiquettesDefinitives.add(Double.MAX_VALUE);
			etiquettesProvisoires.add(Double.MAX_VALUE);
		}

		/* Variables pour algorithme + enregistrement de la source*/
		//Cle = indice dans les etiquettes, valeur = chemin le plus court correspondant au poids des etiquettes
		Map<Integer, ArrayDeque<Route>> cheminsPlusCourts = new HashMap<>();
		for (int i = 0; i < airports.size(); i++) {
			cheminsPlusCourts.put(i, null);
		}
		ArrayDeque<Route> cheminCourant = new ArrayDeque<>();
		Double distancePrecedente = 0.0;
		String airportCourant = source;
		int positionAirportCourant = indicesEtiquettesParAirport.get(airportCourant);
		etiquettesDefinitives.set(positionAirportCourant, distancePrecedente);
		//On passe les provisoires à -1.0 quand on valide le chemin vers l'aeroport concerne (quand l'aeroport dest devient courant)
		etiquettesProvisoires.set(positionAirportCourant, -1.0);

		while (airportCourant != destination) {	
			Set<Route> r = routes.get(airportCourant);
			Airport src = airports.get(airportCourant);
			if(r != null){
				for (Route route : r) {
					Airport dest = airports.get(route.getDestination());
					//on recupère la distance minimum pour l'aeroport dest dans les provisoires
					int indiceDest = indicesEtiquettesParAirport.get(dest.getIata());
					double distanceRecuperee = etiquettesProvisoires.get(indiceDest);
					double distanceSourceDest = distance(src.getLatitude(), src.getLongitude(),
							dest.getLatitude(), dest.getLongitude());
					//si on trouve un meilleur chemin
					if(distanceRecuperee>distancePrecedente+distanceSourceDest){
						etiquettesProvisoires.set(indiceDest, distancePrecedente+distanceSourceDest);
					}
				}
			}
			
			//On cherche le prochain courant
			Double distanceMin = Double.MAX_VALUE;
			String airportDistanceMin = null;
			for(int i = 0; i < etiquettesProvisoires.size();i++){
				if(etiquettesProvisoires.get(i)<distanceMin && etiquettesProvisoires.get(i)!=-1){
					System.out.println(airportEtiquettesParIndice.get(i) + "   DISTANCE COMPARER ----> " + etiquettesProvisoires.get(i) + " < " + distanceMin);
					distanceMin=etiquettesProvisoires.get(i);
					airportDistanceMin=airportEtiquettesParIndice.get(i);
				}
			}
			System.out.println("AIRPORT DISTMIN ---> " + airportDistanceMin);
			/*if(r != null){
				for (Route route : r) {
					String airp = route.getDestination();
					System.out.println(airp);
					int indiceAirp = indicesEtiquettesParAirport.get(airp);
					if (etiquettesProvisoires.get(indiceAirp)<distanceMin && etiquettesProvisoires.get(indiceAirp)!=-1.0) {
						distanceMin = etiquettesProvisoires.get(indiceAirp);
						airportDistanceMin = airp;
						System.out.println("CHANGER ----> "  + airportDistanceMin);
					}
				}
			}*/
			//on change toutes les variables concernees
			cheminCourant = new ArrayDeque<>();
			distancePrecedente = distanceMin;
			airportCourant = airportDistanceMin;
			System.out.println(airportCourant);
			System.out.println(indicesEtiquettesParAirport.get(airportCourant));
			System.out.println(etiquettesProvisoires.size());
			System.out.println(etiquettesProvisoires.stream().filter(e -> e.doubleValue()==-1).count());
			positionAirportCourant = indicesEtiquettesParAirport.get(airportCourant);
			etiquettesDefinitives.set(positionAirportCourant, distancePrecedente);
			etiquettesProvisoires.set(positionAirportCourant, -1.0);
		}
		
		return cheminsPlusCourts.get(indicesEtiquettesParAirport.get(destination));
	}

}
