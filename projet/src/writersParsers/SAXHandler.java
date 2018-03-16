package writersParsers;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import domaine.Airline;
import domaine.Airport;
import domaine.Graph;
import domaine.Route;

public class SAXHandler extends DefaultHandler{
	
	private Airport airport;
	private boolean bLongitude=false;
	private boolean bLatitude=false;
	private Airline airline;
	private boolean bAirline=false;
	private Route route;
	private Map<String,Airport> listeAirport = new HashMap<>();
	private Map<String,Airline> listeAirline = new HashMap<>();
	private Map<String,Set<Route>> sourceDest = new HashMap<>();
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("airport")){
			airport = new Airport(attributes.getValue("iata"),attributes.getValue("name"),attributes.getValue("city"),attributes.getValue("county"));
		}
		else if(qName.equals("longitude")){
			bLongitude=true;
		}
		else if(qName.equals("latitude")){
			bLatitude=true;
		}
		else if(qName.equals("airline")){
			airline=new Airline(attributes.getValue("iata"), attributes.getValue("country"));
			bAirline=true;
		}
		else if(qName.equals("route")){
			route = new Route(attributes.getValue("airline"), attributes.getValue("source"), attributes.getValue("destination"));
			if(!sourceDest.containsKey(route.getSource())){
				sourceDest.put(route.getSource(), new HashSet<>());
			}
			sourceDest.get(route.getSource()).add(route);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(bLongitude){
			String result =new String(ch, start, length);
			airport.setLongitude(result);
			bLongitude=false;
		}
		else if(bLatitude){
			String result =new String(ch, start, length);
			airport.setLatitude(result);
			bLatitude=false;
			listeAirport.put(airport.getIata(), airport);
		}
		else if(bAirline){
			String result =new String(ch, start, length);
			airline.setName(result);
			bAirline=false;
			listeAirline.put(airline.getIata(), airline);
		}
	}
	

	public Graph getGraph() {
		return new Graph(listeAirline,listeAirport,sourceDest);
	}

}
