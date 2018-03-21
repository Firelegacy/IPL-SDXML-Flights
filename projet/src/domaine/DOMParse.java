package domaine;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 
 
public class DOMParse { 
 
  public static void main(String[] args) { 
 
    Map<String,Airport> listeAirport = new HashMap<>(); 
    Map<String,Airline> listeAirline = new HashMap<>(); 
    Map<String,Set<Route>> sourceDest = new HashMap<>(); 
 
    try { 
      File xmlFile = new File("flight.xml"); 
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); 
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
      Document doc = dBuilder.parse(xmlFile); 
       
      NodeList airport = doc.getElementsByTagName("airport"); 
      for(int i = 0; i < airport.getLength();i++){ 
        System.out.println("AIRPORT"); 
        Node nAirport = airport.item(i); 
        Element eAirport = (Element) nAirport; 
         
        String iataAirport=eAirport.getAttribute("iata"); 
        String nameAirport=eAirport.getAttribute("name"); 
        String cityAirport=eAirport.getAttribute("city"); 
        String countryAirport=eAirport.getAttribute("country"); 
        Airport airportTemp = new Airport(iataAirport, nameAirport, cityAirport, countryAirport); 
        Double longitude = Double.parseDouble(eAirport.getElementsByTagName("longitude").item(0).getTextContent()); 
        Double latitude = Double.parseDouble(eAirport.getElementsByTagName("latitude").item(0).getTextContent()); 
        airportTemp.setLongitude(longitude); 
        airportTemp.setLatitude(latitude); 
        System.out.println(airportTemp.getLatitude()); 
        listeAirport.put(iataAirport, airportTemp); 
        System.out.println("Airport - " + iataAirport); 
      } 
 
      NodeList airline = doc.getElementsByTagName("airline"); 
      for(int i = 0; i < airline.getLength();i++){ 
        System.out.println("AIRLINE"); 
        Node nAirline = airline.item(i); 
        Element eAirline = (Element) nAirline; 
         
        String iataAirline=eAirline.getAttribute("iata"); 
        String countryAirline=eAirline.getAttribute("country"); 
        Airline airlineTemp = new Airline(iataAirline, countryAirline); 
        airlineTemp.setName(eAirline.getTextContent()); 
        listeAirline.put(iataAirline, airlineTemp); 
        System.out.println("Airline - " + iataAirline); 
      } 
       
      NodeList route = doc.getElementsByTagName("route"); 
      for(int i = 0; i < route.getLength();i++){ 
        System.out.println("ROUTE"); 
        Node nRoute = route.item(i); 
        Element eRoute = (Element) nRoute; 
       
        String airlineRoute = eRoute.getAttribute("airline"); 
        String sourceRoute = eRoute.getAttribute("source"); 
        String destinationRoute = eRoute.getAttribute("destination"); 
        System.out.println(airlineRoute + "-" + sourceRoute + "-" + destinationRoute); 
        Route routeTemp = new Route(airlineRoute, sourceRoute, destinationRoute); 
        if(!sourceDest.containsKey(routeTemp)){ 
          sourceDest.put(airlineRoute, new HashSet<>()); 
        } 
        sourceDest.get(airlineRoute).add(routeTemp); 
      } 
       
 
 
    } catch (Exception e) { 
      // TODO: handle exception 
    } 
 
  } 
 
} 