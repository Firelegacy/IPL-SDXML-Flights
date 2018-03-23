package writersParsers;

import java.io.File;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import domaine.Airline;
import domaine.Airport;
import domaine.Route;
import utils.Util;

public class XMLWriter {
	
	private Map<String, Airline> airlines;
	private Map<String, Airport> airports;
	private Map<String, Set<Route>> routes;
	
	public XMLWriter(Map<String, Airline> airlines, Map<String, Airport> airports, Map<String, Set<Route>> routes) {
		super();
		this.airlines = airlines;
		this.airports = airports;
		this.routes = routes;
	}

	public void writeXMLResultFile(String filename, Deque<Route> chemin,String source, String destination) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			double distanceTotal = 0;
			for(Route route : chemin){
				Airport depart = airports.get(route.getSource());
				Airport arriver = airports.get(route.getDestination());
				distanceTotal+= Util.distance(depart.getLatitude(), depart.getLongitude(), arriver.getLatitude(), arriver.getLongitude());
			}
			Airport src = airports.get(source);
			Airport dest = airports.get(destination);

			// Element root - trajet
			Element trajet = doc.createElement("trajet");
			doc.appendChild(trajet);
			
			Attr attr = doc.createAttribute("depart");
			attr.setValue(src.getName());
			trajet.setAttributeNode(attr);
			
			attr = doc.createAttribute("destination");
			attr.setValue(dest.getName());
			trajet.setAttributeNode(attr);
			
			attr = doc.createAttribute("distance");
			attr.setValue(Double.toString(distanceTotal));
			trajet.setAttributeNode(attr);
			

			int nbVol = 1;
			for(Route route : chemin){
				
				Airport depart = airports.get(route.getSource());
				Airport arriver = airports.get(route.getDestination());
				double distance = Util.distance(depart.getLatitude(), depart.getLongitude(), arriver.getLatitude(), arriver.getLongitude());
				
				// Vol element
				Element vol = doc.createElement("vol");
				trajet.appendChild(vol);

				// setting attribute to element
				attr = doc.createAttribute("compagnie");
				attr.setValue(airlines.get(route.getAirline()).getName());
				vol.setAttributeNode(attr);
				
				attr = doc.createAttribute("nombrekm");
				attr.setValue(Double.toString(distance));
				vol.setAttributeNode(attr);
				
				attr = doc.createAttribute("numero");
				attr.setValue(Integer.toString(nbVol));
				vol.setAttributeNode(attr);
				nbVol++;

				Element sources = doc.createElement("source");
				
				attr = doc.createAttribute("iata");
				attr.setValue(depart.getIata());
				sources.setAttributeNode(attr);
				
				attr = doc.createAttribute("pays");
				attr.setValue(depart.getCountry());
				sources.setAttributeNode(attr);
				
				attr = doc.createAttribute("ville");
				attr.setValue(depart.getCity());
				sources.setAttributeNode(attr);
	
				sources.appendChild(doc.createTextNode(depart.getName()));
				vol.appendChild(sources);
	
				Element destinations = doc.createElement("destination");
				
				attr = doc.createAttribute("iata");
				attr.setValue(arriver.getIata());
				destinations.setAttributeNode(attr);
				
				attr = doc.createAttribute("pays");
				attr.setValue(arriver.getCountry());
				destinations.setAttributeNode(attr);
				
				attr = doc.createAttribute("ville");
				attr.setValue(arriver.getCity());
				destinations.setAttributeNode(attr);
	
				destinations.appendChild(doc.createTextNode(arriver.getName()));
				vol.appendChild(destinations);
			
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",null,
			    "result.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			DOMSource sources = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(sources, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(sources, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
