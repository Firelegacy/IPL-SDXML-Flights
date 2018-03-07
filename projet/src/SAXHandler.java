import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import domaine.Graph;

public class SAXHandler extends DefaultHandler{
	
	private Airport airport=null;
	private boolean bLongitude=false;
	private boolean bLatitude=false;
	private Airline airline=null;
	
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
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("airport")){
		}
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
		}
	}

	public Graph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
