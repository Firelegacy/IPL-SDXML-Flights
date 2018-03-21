package domaine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.*;

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
		System.out.println("ET ON EST REPARTI");
		Airport airportDestination = airports.get(destination); 					//Destination
		List<String> listeAirport = new ArrayList<>();								//Liste de tout les aeroports
		List<Double> etiquetteDefinitive = new ArrayList<>();						//Etiquette définitive
		List<Double> etiquetteProvisoire = new ArrayList<>();						//Etiquette provisoire 
		boolean cheminCourtTrouver = false;						
		Set<Airport> airportValider = new HashSet<>();								//Liste des aeroports auquel ont a déjà trouver le chemin dont la distance est le plus court
		double poidCourant=0.0;									
		for( String a : airports.keySet()){
			listeAirport.add(a);													//Ajout des aeroport dans la list 'listeAirport'
			etiquetteDefinitive.add(-1.0);											//initialisation des valeur '-1' dans l'etiquette définitive
			etiquetteProvisoire.add(-1.0);											//initialisation des valeur '-1' dans l'etiquette provisoire
		}
		Airport airportCourant=airports.get(source);								//Aeroport courant
		int positionAirportCourtant = listeAirport.indexOf(source);					//Position dans la liste;

		etiquetteDefinitive.set(positionAirportCourtant, poidCourant);				//MAJ de l'etiquette définitive pour la source
		etiquetteProvisoire.set(positionAirportCourtant, Double.MAX_VALUE);			//MAJ de l'etiquette prov pour la source
		airportValider.add(airportCourant);											//Ajout de la source dans la list 'airportValidé'

		while(!cheminCourtTrouver){
			System.out.println(airportCourant.getIata());
			List<Airport> listeDestination=new ArrayList<>();						//Initialisation de la liste des destination en partant de la source
			if(routes.get(airportCourant.getIata())!=null){
				for(Route r : routes.get(airportCourant.getIata())){
					Airport airportTemp = airports.get(r.getDestination());
					if(!airportValider.contains(airportTemp)){						//Si on n'a pas encore trouver le chemin le plus cours pour la destination
						listeDestination.add(airports.get(r.getDestination()));			//On l'ajoute a la liste
					}
				}
			}
			for(Airport a : listeDestination){
				int indexA = listeAirport.indexOf(a.getIata());
				double result;
				if(etiquetteProvisoire.get(indexA)==-1)
					result = poidCourant + Util.distance(a.getLatitude(), a.getLongitude(), airportCourant.getLatitude(), airportCourant.getLongitude());	//On prend la distance entre les aerports 
				else
					result = Math.min(etiquetteProvisoire.get(indexA), poidCourant+Util.distance(a.getLatitude(), a.getLongitude(), airportCourant.getLatitude(), airportCourant.getLongitude())); //On prend le min des poids
				etiquetteProvisoire.set(indexA, result);	//Maj de l'etiquette provisoire
			}
			int i=0;
			int indexMin=-1;
			double distMin=Double.MAX_VALUE;
			for(Double dist : etiquetteProvisoire){
				if(dist<=distMin && dist!=-1){	//Je recherche l'aeroport le plus proche
					indexMin=i;
					distMin=dist;
					i++;
				}
			}
			airportCourant = airports.get(listeAirport.get(indexMin));	//MAJ de l'aerport courant;
			poidCourant = etiquetteProvisoire.get(indexMin);			//MAJ du poids
			etiquetteDefinitive.set(indexMin, distMin);					//MAJ de l'etiquette def				
			etiquetteProvisoire.set(indexMin, Double.MAX_VALUE);		//MAJ de l'etiquette prov
			airportValider.add(airportCourant);							//on ajoute l'airport courant dans airportValider
			if(airportValider.contains(airportDestination))				//check si l'airport courant est la destination;
				cheminCourtTrouver=true;
		}
		System.out.println("trouver");
	}
}
