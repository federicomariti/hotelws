package com.hotel.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hotel.StatoPrenotazione;
import com.hotel.utils.MySaxErrorHandler;
import com.hotel.utils.Xml;

public class XmlTest {
	
	private static final int VERBOSE_PRINT_NONE = 0;
	private static final int VERBOSE_PRINT_ONE_LINES = 1;
	private static final int VERBOSE_PRINT_LINES = 2;
	private static final int VERBOSE_PRINT_SOME_XML = 3;
	private static final int VERBOSE_PRINT_ALL_XML = 4;
	
	private static int verbose = VERBOSE_PRINT_LINES;
	
	
//==============================================================================	
//    Testing
//==============================================================================		
	
	
	private static int test_validateXmlDocument_chiamante() throws Exception {
		int r=0, rl;
		String baseDir = "xml_testFiles/";
		String schemaBaseDir = "WebContent/";
		String fileName[] = {
				baseDir+"ricerca1.xml",
				baseDir+"prenotazione1.xml",
				baseDir+"risposta-ricerca1.xml",
				baseDir+"risposta1-prenotazione1.xml",
				baseDir+"risposta2-prenotazione1.xml"
		};

		String schemaFileName[] = {
				schemaBaseDir+"ricerca.xsd",
				schemaBaseDir+"prenotazione.xsd",
				schemaBaseDir+"ricerca.xsd",
				schemaBaseDir+"prenotazione.xsd",
				schemaBaseDir+"prenotazione.xsd"
		};
		
		int [] oracolo = { 
				0, 0, 0, 0, 0
		};
		
		for(int i=0; i<fileName.length; i++) {
			System.out.println("test_validateXmlDocument("+fileName[i]+", "+schemaFileName[i]+")");
			if ((rl = test_validateXmlDocument(fileName[i], schemaFileName[i]))
					!= oracolo[i]) {
				r = 1;
				System.out.println("[FAIL]["+rl+"]");
			} else 
				System.out.println("[OK]");
		}
		return r;
	}
	
	private static int test_validateXmlDocument(String xmlFileName, 
			String xsdFileName) {
		int returnValue = 0;
		File file = new File(xmlFileName);
		if (!file.canRead())  {
			System.out.println("Can't read file " + xmlFileName); 
			return 1;
		}
		
		File schemaFile = new File(xsdFileName);
		if (!file.canRead()) {
			System.out.println("Can't read file " + xsdFileName);
			return 1;
		}
		try {
			Xml.validate(xsdFileName, new FileInputStream(xmlFileName), 
					new MySaxErrorHandler());
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
			returnValue = 2;
		}
		
		return returnValue;
	}
	
//==============================================================================
	
	private static final String soapNamespace = "http://schemas.xmlsoap.org/soap/envelope/";
	private static final String soapSchemaLocation = "xml_testFiles/envelope.xml";
	
	private static final int VALIDATESOAPDOCUMENT_GENERIC_ERROR = 1;
	private static final int VALIDATESOAPDOCUMENT_SOAP_NOT_VALID = 2;
 	private static final int VALIDATESOAPDOCUMENT_CONTENT_NOT_VALID = 3;
 	
	private static int test_validateSoapDocument_chiamante() throws Exception {
		int r=0, rl;
		String baseDir = "xml_testFiles_shop/";
		String baseDir2 = "xml_testFiles/";
		String fileName[] = {
				baseDir+"soap.xml",
				baseDir+"soap_contentNotValid.xml",
				baseDir+"soap_notValid.xml",
				baseDir+"soap_signature.xml"
				//baseDir2+"ricerca1.xml",
				//baseDir2+"prenotazione1.xml"
		};

		String schemaFileName[] = {
				baseDir+"schema.xsd",
				baseDir+"schema.xsd",
				baseDir+"schema.xsd",
				baseDir+"schema.xsd"
				//"WebContent/ricerca.xsd",
				//"WebContent/prenotazione.xsd"
		};
		
		int [] oracolo = { 
				0,
				VALIDATESOAPDOCUMENT_CONTENT_NOT_VALID,
				VALIDATESOAPDOCUMENT_SOAP_NOT_VALID,
				0,
				0,
				0
		};
		
		for(int i=0; i<fileName.length; i++) {
			System.out.println("test_validateSoapDocument("+fileName[i]+", "+schemaFileName[i]+")");
			if ((rl = test_validateSoapDocument(fileName[i], schemaFileName[i]))
					!= oracolo[i]) {
				r = 1;
				System.out.println("[FAIL]["+rl+"]");
			} else 
				System.out.println("[OK]");
		}
		return r;
	}
	/**
	 * Il test segue il modo di procedere per estrapolare il contenuto del Body 
	 * di una busta SOAP. Il contenuto della busta deve essere validato con lo
	 * schema di SOAP, il contenuto in Body deve essere validato con il relativo 
	 * schema, tale schema puo' essere fornito o estrapolato.
	 * @param fileName
	 * @param contentXsdFileName
	 * @return
	 * @throws Exception
	 */
	private static int test_validateSoapDocument(
			String fileName, String contentXsdFileName) throws Exception {
		File file = new File(fileName);
		if (!file.canRead())  {
			System.err.println("Can't read file " + fileName); 
			return VALIDATESOAPDOCUMENT_GENERIC_ERROR; 
		}
		
		File schemaFile = new File(contentXsdFileName);
		if (!file.canRead()) {
			System.err.println("Can't read file " + contentXsdFileName);
			return VALIDATESOAPDOCUMENT_GENERIC_ERROR;
		}
		
		// *** crea e valida il Document con lo schema di SOAP ***
		Document doc = null;
		try {
			doc = Xml.validate(soapSchemaLocation, new FileInputStream(file), 
					new MySaxErrorHandler());
			
		} catch(Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
			return VALIDATESOAPDOCUMENT_SOAP_NOT_VALID;
		}
		if (verbose >= VERBOSE_PRINT_ALL_XML) 
			System.out.println(Xml.toString(doc));
		
		// *** trova il wrapper element contenuto nel body della busta soap ***
		NodeList nl = doc.getElementsByTagNameNS(soapNamespace, "Body");
		
		if (verbose >= VERBOSE_PRINT_LINES) {
			System.out.println("[DBG] contenuto di nl dopo getElementsByTagNameNS(soapNamespace, \"Body\")");
			for (int i = 0; i<nl.getLength(); i++)
				System.out.println(i + ". " + nl.item(i).getNodeName());
		}
		
		// figli di Body
		nl = nl.item(0).getChildNodes(); 
		
		//posizione di wrapper element
		int i = Xml.getPosOfFirstElementFrom(nl, 0); 
		
		if (verbose >= VERBOSE_PRINT_ONE_LINES) 
			System.out.println("[DBG] wrapper element name = " + nl.item(i).getNodeName());
		
		if (verbose >= VERBOSE_PRINT_SOME_XML) {
			System.out.println("[DBG] wrapper element\n");
			Xml.toString(nl.item(i)); }
		
		
		// *** validazione del wrapper Element ***
		if (!Xml.validateNode(nl.item(i), schemaFile)) {
			return VALIDATESOAPDOCUMENT_CONTENT_NOT_VALID;
		}
		return 0;
	}
	
//==============================================================================

	private static int test_getSOAPElements() throws Exception {
		int returnValue = 0;
		String xmlDir = "xml_testFiles/";
		String[] soapFiles = { "soap_ricerca1.xml", "soapMsg.xml" };
		
		DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();
		docbf.setNamespaceAware(true);
		DocumentBuilder docBuilder = docbf.newDocumentBuilder();
		
		MessageFactory msgFactory = MessageFactory.newInstance();
		
		for (int i=0; i<soapFiles.length; i++) {
			// costruisci il Document di un file contenente una busta SOAP
			org.w3c.dom.Document doc  =
				docBuilder.parse("file:" + xmlDir + soapFiles[i]);
			javax.xml.transform.dom.DOMSource domSource = new DOMSource(doc);
			
			// crea il SOAPMessage dal contenuto del file letto
			SOAPMessage soapMsg = msgFactory.createMessage();
			SOAPPart soapPart = soapMsg.getSOAPPart();
			soapPart.setContent(domSource);
			soapMsg.saveChanges();
			
			// esegui il test
			_test_getSOAPElements(soapMsg.getSOAPBody());
		}
		
		return returnValue;
	}
	
	private static int _test_getSOAPElements(SOAPElement e) throws Exception {
		int returnValue = 0;
		Iterator<SOAPElement> i = Xml.getSOAPElements(e);
		while (i.hasNext()) {
			Xml.printDocument(i.next());
			//System.out.println(Xml.toString(i.next()));
		}
		return returnValue;
	}
	
//==============================================================================
//    Utils
//==============================================================================

	private static void validateXml(String xmlPath, String xsdPath, boolean print) {
		int r = 0;
		
		File xmlf = new File(xmlPath), xsdf = new File(xsdPath);
		Document doc = null;
		
		if (!xmlf.canRead()) { 
			System.out.println("Can't read " + xmlPath);
			System.exit(1);
		} else if (!xsdf.canRead()) {
			System.out.println("Can't read " + xsdPath);
			System.exit(1);
		}
		
		try {
			doc = Xml.validate(xsdPath, new FileInputStream(xmlf), 
					new MySaxErrorHandler());
		} catch(Exception e) {
			System.err.println(e.getMessage());
			System.exit(2);
		}
		
		if (print) 
			try { System.out.println(Xml.toString(doc));
			} catch(Exception e) { 
				System.err.println(e.getMessage()); System.exit(2); 
			}
		
		System.exit(r);
	}
	
//==============================================================================

	private static void printXml(String xmlPath) {
		int r = 0;
		
		File xmlf = new File(xmlPath);
		Document doc = null;
		
		if (!xmlf.canRead()) { 
			System.out.println("Can't read " + xmlPath);
			System.exit(1);
		}
		
		try {
			doc = Xml.getDocument(new FileInputStream(xmlf));
		} catch(Exception e) {
			System.err.println(e.getMessage());
			System.exit(2);
		}

		try { System.out.println(Xml.toString(doc));
		} catch(Exception e) { 
			System.err.println(e.getMessage()); System.exit(2); 
		}

		System.exit(r);
	}
	
//==============================================================================	
//    Main
//==============================================================================
	
	public static void prova() {
		try {
			throw new RuntimeException("asd");
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("finally");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		prova();
		
		int r = 0;
		final String usageString = 
			"-o operation-name -h";
		final String operationsString = 
			"operation-name: \n test_validateSoapDocument \n valida \n stampa ";
		String operation = "";
		
		Options options = new Options();
		
		{ 
			Option o = new Option("h", "help", false, "");
			options.addOption(o);
			o = new Option("o", "operation", true, "");
			o.setArgName("operation-name");
			options.addOption(o);
			o = new Option(null, "verbose", true, "");
			o.setArgName("verbose-level");
			options.addOption(o);
		}
		
		HelpFormatter helpFormatter = new HelpFormatter();
		CommandLineParser clp = new PosixParser();
		CommandLine cl = null;
		
		try { cl = clp.parse(options, args); }
		catch(ParseException e) { 
			System.err.println(e.getMessage());
			helpFormatter.printHelp(usageString, options);
			System.out.println(operationsString);
			System.exit(1);
		}
		
		if (cl.hasOption("help")) { 
			helpFormatter.printHelp(usageString, options);
			System.out.println(operationsString);
			System.exit(0);
		}
		
		if (cl.hasOption("operation")) 
			operation = cl.getOptionValue("operation");
		
		if (cl.hasOption("verbose")) 
			verbose = Integer.valueOf(cl.getOptionValue("verbose"));
		
		//----------------------------------------------------------------------
		
		// --- Testing ---
		
		if (operation.equals("test_validateSoapDocument")) {
			r = test_validateSoapDocument_chiamante();
			
		} else if (operation.equals("test_getSOAPElements")) {
			r = test_getSOAPElements();
			
			
		} else if (operation.equals("test_validateXmlDocument")) {
			r = test_validateXmlDocument_chiamante();
		
		// --- Utils ---
			
		} else if (operation.equals("validate")) {
			if (cl.getArgs().length < 2) r = 1;
			else validateXml(cl.getArgs()[0], cl.getArgs()[1], false);
			System.exit(0);
			
		} else if (operation.equals("print")) {
			if (cl.getArgs().length < 1) r = 1;
			else printXml(cl.getArgs()[0]);
			System.exit(0);
		
		} else if (operation.equals("")) {
			System.out.println("Nessuna operazione specificata.");
			System.exit(1);
			
		} else {
			System.out.println("Operazione non conosciuta.");
			System.exit(1);
			
		}
		
		
		if (0 == r)
			System.out.println("Test Superato");
		else 
			System.out.println("Test Fallito");
		
		System.exit(r);
	}
	static void foo(String[] a) { System.out.println(); }

}
