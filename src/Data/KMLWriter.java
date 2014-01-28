package Data;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KMLWriter 
{
	public void WriteKML (String filePath, ArrayList<PayloadDataPoint> dataPoints)
	{		
		filePath = filePath.replace("\\", "/");
		filePath += ".kml";
		System.out.println(filePath);
		try 
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();					
			Element rootElement = doc.createElement("kml");
			rootElement.setAttribute("xmlns","http://www.opengis.net/kml/2.2");
			doc.appendChild(rootElement);
			Element document = doc.createElement("Document");
			rootElement.appendChild(document);
	 
			for(int i=0; i<dataPoints.size(); i++)
			{
				Element placemark = doc.createElement("Placemark");
				document.appendChild(placemark);
				
				Element style = doc.createElement("Style");
				placemark.appendChild(style);
				
				Element iconStyle = doc.createElement("IconStyle");
				style.appendChild(iconStyle);
				
				Element color= doc.createElement("color");
				color.appendChild(doc.createTextNode("ffff3000"));
				iconStyle.appendChild(color);
				
				Element colorMode= doc.createElement("colorMode");
				colorMode.appendChild(doc.createTextNode("normal"));
				iconStyle.appendChild(colorMode);
				
				Element scale= doc.createElement("scale");
				scale.appendChild(doc.createTextNode("0.8"));
				iconStyle.appendChild(scale);
				
				Element icon = doc.createElement("Icon");
				iconStyle.appendChild(icon);
				
				Element href= doc.createElement("href");
				href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/shapes/shaded_dot.png"));
				icon.appendChild(href);
				
				Element point = doc.createElement("Point");
				placemark.appendChild(point);
				
				Element coords = doc.createElement("coordinates");
				PayloadDataPoint dataPoint = dataPoints.get(i);
				coords.appendChild(doc.createTextNode(dataPoint.GPS_Lon+", "+dataPoint.GPS_Lat+", "+dataPoint.GPS_Alt));
				point.appendChild(coords);
				
				Element altitude = doc.createElement("altitudeMode");				
				altitude.appendChild(doc.createTextNode("absolute"));
				point.appendChild(altitude);
			}
	 
			// write the content into file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filePath));	 				 
			transformer.transform(source, result);	 
			
		} 
		catch (ParserConfigurationException e) {e.printStackTrace();}
		catch (TransformerException e) {e.printStackTrace();}
	}
}
