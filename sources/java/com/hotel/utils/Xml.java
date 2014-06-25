package com.hotel.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * Funzioni di utilita' per la gestione degli XML.
 * 
 */

public class Xml {
	
	private static void printNode(Node node) {
		/* [<nome> <attributi> <contenuto testuale>] */
		String attrStr = "";
		NamedNodeMap attr = node.getAttributes();
		if (null != attr) 
			for (int i=0; i<attr.getLength(); i++) 
				attrStr += attr.item(i).getNodeName() + "=" 
				+ attr.item(i).getTextContent() +" ";

		System.out.print("[" + node.getNodeName());
		if (attrStr.equals("") == false) System.out.print(" " + attrStr);
		if (node.getNodeValue() != null) System.out.print(" " + node.getNodeValue());
		System.out.println("]");
	}
	private static void printNode(Node node, int l) {
		if (null == node) return ;

		for (int i=0; i<l; i++) System.out.print("    ");
		printNode(node);

		NodeList n = node.getChildNodes();

		for (int i=0; i<n.getLength(); i++)
			printNode(n.item(i), l+1);

	}
	public static void printDocument(Node doc) {
		printNode(doc, 0);
	}
	
	public static SOAPBodyElement getFirstSoapBodyElement(SOAPBody body) {
		@SuppressWarnings("rawtypes")
		Iterator i = body.getChildElements();
		Node n;
		
		while(i.hasNext()) 
			if ((n=(Node)i.next()) instanceof SOAPBodyElement)
				return (SOAPBodyElement) n;
		
		return null;
	}
	
	/**
	 * il metodo d'istanza getChildElements di SOAPElement ritorna un iteratore 
	 * @param e
	 * @return
	 */
	public static Iterator<SOAPElement> getSOAPElements(SOAPElement e) {
		@SuppressWarnings("rawtypes")
		Iterator i = e.getChildElements();
		LinkedList<SOAPElement> list = new LinkedList<SOAPElement>();
		
		while (i.hasNext()) {
			javax.xml.soap.Node node = (javax.xml.soap.Node) i.next();
			if (node instanceof SOAPElement) {
				list.add((SOAPElement)node);
			} else {
				// do nothing
				/* debug
				try { 
					System.out.println("[begin][getSOAPElement]");
					printDocument(node); 
					System.out.println("[end][getSOAPElement]");
				} catch (Exception e1) { ; }
				*/
			}
			if (node instanceof SOAPBodyElement) {
				System.out.println("[getSOAPElement] " + node.getLocalName());
			}
		}
		return list.iterator();
	}
	
	/**
	 * Ritorna la posizione del primo node di tipo Element nella lista di nodi 
	 * a partire da una certa posizione
	 *
	 * @param nl
	 * @param from
	 * @return
	 */
	public static int getPosOfFirstElementFrom(NodeList nl, int from) {
		for (; from<nl.getLength() && 
		Node.ELEMENT_NODE != nl.item(from).getNodeType(); from++) ;
		return from;
	}
	
	/**
	 * Trasforma un Node in String
	 * 
	 * @param doc Il Docuemnt da trasformare
	 * @return la rappresentazione testuale del Document
	 * @throws TransformerException
	 */
	public static String toString(Node n) throws TransformerException {
		Source source = new DOMSource(n);
	    Transformer xformer = TransformerFactory.newInstance().newTransformer();
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    xformer.transform(source, result);  
	    return writer.toString();
	}
	
	/**
	 * Trasforma un Document in String
	 * 
	 * @param doc Il Docuemnt da trasformare
	 * @return la rappresentazione testuale del Document
	 * @throws TransformerException
	 * @author Lorenzo Nardi (nardi80@gmail.com)
	 */
	public static String toString(Document doc) throws TransformerException{
		Source source = new DOMSource(doc);
	    Transformer xformer = TransformerFactory.newInstance().newTransformer();
	    StringWriter writer = new StringWriter();
	    StreamResult result = new StreamResult(writer);
	    xformer.transform(source, result);  
	    return writer.toString();
	}
	
    static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 
    static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	
    /**
     * Esegue il parsing e validazione di un documento XML
     * 
     * @param xsd XML Schema da usare per la validazione
     * @param xml XML da validare e parsare
     * @param errorHandler Implementazione di ErrorHandler per la gestione degli errori di validazione
     * @return Il Dom Document del documento validato.
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @author Lorenzo Nardi (nardi80@gmail.com)
     */
	public static Document validate(Object xsd, InputStream xml, ErrorHandler errorHandler) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setIgnoringElementContentWhitespace(true);
	    factory.setNamespaceAware(true);
	    factory.setValidating(true);
	    factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	    factory.setAttribute(JAXP_SCHEMA_SOURCE, xsd);
	    
	    // Prendo il parser, valido l'xml e ne ottengo il DOM
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    builder.setErrorHandler(errorHandler);
	    Document document = builder.parse( xml );
	    return document;
	}
	
	private static SchemaFactory schemaFactory = 
		SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	
	private static DocumentBuilder getDocumentBuilder() 
	throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = 
			DocumentBuilderFactory.newInstance();
		docBuilderFactory.setIgnoringComments(true);
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		docBuilderFactory.setNamespaceAware(true);
		
		return docBuilderFactory.newDocumentBuilder();
	}
	
	public static Document getDocument(InputStream input) 
	throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilder docBuilder = getDocumentBuilder();
		return docBuilder.parse(input);
	}
	
	public static boolean validateNode(Node doc, File schemaLocation) {
		boolean result = true;
		try {
			Schema schema =	schemaFactory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(doc)); 
		} catch(Exception e) { result = false; }
		return result;
	}
	
}
