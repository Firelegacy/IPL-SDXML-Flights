import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import domaine.Graph;

public class Main {
	public static void main(String[] args) {
		try {
			File inputFile = new File("flight.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			SAXHandler userhandler = new SAXHandler();
			saxParser.parse(inputFile, userhandler);
			System.out.println("nbAirport: " + userhandler.getNbListeAirport());
			System.out.println("nbAirline: " + userhandler.getNbListeAirline());
			System.out.println("nbRoute: " + userhandler.getNbSourceDest());

			Graph g = userhandler.getGraph();
			g.calculerItineraireMiniminantDistance("BRU", "PPT", "output.xml");
			g.calculerItineraireMinimisantNombreVol("BRU", "PPT", "output2.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
