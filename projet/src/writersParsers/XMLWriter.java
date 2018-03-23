package writersParsers;

import java.io.File;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import domaine.Airline;
import domaine.Airport;
import domaine.Route;

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

	public void writeXMLResultFile(String filename, Deque<Route> chemin) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// Element root - trajet
			Element trajet = doc.createElement("trajet");
			doc.appendChild(trajet);

			// Vol element
			Element vol = doc.createElement("vol");
			trajet.appendChild(vol);

			// setting attribute to element
			Attr attr = doc.createAttribute("company");
			attr.setValue("Ferrari");
			vol.setAttributeNode(attr);

			// carname element
			Element carname = doc.createElement("carname");
			Attr attrType = doc.createAttribute("type");
			attrType.setValue("formula one");
			carname.setAttributeNode(attrType);
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			vol.appendChild(carname);

			Element carname1 = doc.createElement("carname");
			Attr attrType1 = doc.createAttribute("type");
			attrType1.setValue("sports");
			carname1.setAttributeNode(attrType1);
			carname1.appendChild(doc.createTextNode("Ferrari 202"));
			vol.appendChild(carname1);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filename));
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
