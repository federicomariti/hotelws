package com.hotel.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hotel.utils.MySaxErrorHandler;
import com.hotel.utils.Xml;

public class Proxy extends HttpServlet {
	
	private static int debug = 1;
	
	private static final long serialVersionUID = 1L;
	private static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";
	private String soapSchemaLocation;
	
//==============================================================================
	
	private Vector<Service> services = new Vector<Service>();
	
	/**
	 * descrive le proprieta' di un web service, quali la locazione dello schema 
	 * XML, l'endpoint in cui e' pubblicato, i nomi delle operazioni possibili
	 * @author federico
	 *
	 */
	private static class Service {
		URI wsEndpoint;
		String schemaLocation;
		List<String> operationName;
		boolean encryptData;
		
		public Service(String schemaLocation, String wsEndpoint, 
				String[] operationName, boolean encryptData)  throws URISyntaxException {
			this.schemaLocation = schemaLocation;
			this.wsEndpoint = new URI(wsEndpoint);
			this.operationName = new Vector<String>();
			for (int i=0; i<operationName.length; i++) 
				this.operationName.add(operationName[i]);
			this.encryptData = encryptData;
		}
		public String toString() {
			return 
			"wsEndpoint=" + wsEndpoint +
			", schemaLoc=" + schemaLocation + 
			", operations=" + operationName.toString() +
			", encrypt=" + encryptData;
		}
		public boolean contains(String operation) {
			return operationName.contains(operation);
		}
	}
	
	/**
	 * stabilisce se 
	 * @param operationName
	 * @param vector
	 * @return
	 */
	private Service isIn(String operationName, Vector<Service> vector) {
		int i;
		for (i=0; i<vector.size(); i++)
			if (vector.get(i).contains(operationName)) return vector.get(i);
		return null;
	}
	
//==============================================================================
	
	private void printInfo(PrintWriter writer) {
		for (int i=0; i<services.size(); i++) 
			writer.println(services.get(i));
		writer.println("soapLocation=" + soapSchemaLocation);
	}
	
//==============================================================================
	
	public void init() throws ServletException {
		//super.init();
		if (debug > 0) System.out.println("[DBG][Proxy][init]");
		/**
		 * legge i valori iniziali dei parametri che definiscono i web service 
		 * di competenza del proxy. I parametri sono: la locazione dello schema 
		 * XML, l'endpoint del web service, i nomi delle operazioni possibili.
		 * Tali valori vengono scritti nel vettore di Service, services.
		 */
		
		String wd = getServletContext().getRealPath("") + "/";
		
		// i nomi dei web service
		String webServices = getServletConfig().getInitParameter("web-services");
		String[] webService = webServices.split(" ");
		// i nomi degli schemi relativi ai precedenti web service
		String schemas = getServletConfig().getInitParameter("schemas");
		String[] schema = schemas.split(" ");
		for (int i=0; i<schema.length; i++) 
			schema[i] = wd + schema[i]; //aggiungi la working directory
		// gli endpoints relativi ai precedenti web service
		String endpoints = getServletConfig().getInitParameter("endpoints");
		String[] endpoint = endpoints.split(" ");
		
		for (int i=0; i<webService.length; i++) {
			// le operazioni invocabili sul web service i-esimo
			String operations = getServletConfig().getInitParameter(
					webService[i] + "-operations");
			String[] operation = operations.split(" ");
			// verifica se il web service i-esimo cifra i dati 
			String encrypt = getServletConfig().getInitParameter(
					webService[i] + "-encrypt");
			boolean encryptData = false;
			if (null != encrypt && encrypt.equals("true")) 
				encryptData = true;
			// memorizza tutte le informazioni ricavate del web service i-esimo
			try { 
				services.add(new Service(schema[i], endpoint[i], operation,
						encryptData));
				
			} catch(Exception e) { 
				throw new RuntimeException(e); 
			}
		}
		
		soapSchemaLocation = new File(wd+"envelope.xml").canRead() ? 
				wd+"envelope.xml" : SOAP_NAMESPACE;	
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		if (debug > 0) System.out.println("[DBG][Proxy][doGet]");
		/**
		 * TODO remove me!
		 * usato per il debug
		 */
		printInfo(resp.getWriter());
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (debug > 0) System.out.println("[DBG][Proxy][doPost]");
		
		// *********************************************************************
		// *** Crea il Document della busta SOAP Validando il contenuto SOAP ***
		// *********************************************************************
		
		Document doc = null;
		try { 
			doc = Xml.validate(soapSchemaLocation, req.getInputStream(), 
					new MySaxErrorHandler());
			
		} catch(Exception e) {
			// la busta SOAP non e' valida
			e.printStackTrace();
			// TODO l'errore e' del client, non del server
			throw new ServletException(e);
		}
		
		// *** Preleva il wrapper element all'interno di soap:Body ***
		NodeList nl = doc.getElementsByTagNameNS(SOAP_NAMESPACE, "Body");
		Node n = nl.item(0); // elemento soap:Body
		nl = n.getChildNodes();
		int pos = Xml.getPosOfFirstElementFrom(nl, 0);
		if (pos == nl.getLength()) {
			// non ci sono elementi in nl
			// TODO l'errore e' del client, non del server
			throw new ServletException("Il corpo della busta SOAP e' vuoto.");
		}
		n = nl.item(pos); // elemento wrapper
		
		{	//debug
			//System.out.println("[DBG][Proxy][doPost] wrapper name = " + n.getLocalName());
			//System.out.println("[DBG][Proxy][doPost] service = " + isIn(n.getLocalName(), services));
		}
		
		Service service;
		
		// ********************************************************************
		// *** Ottieni il nome dell'operazione da eseguire   			    ***
		// *** e Valida il contenuto del wrapper element, se non e' cifrato ***
		// ********************************************************************
		
		if (null == (service=isIn(n.getLocalName(), services))) {
			// l'operazione non e' tra quelle disponibili
			// TODO l'errore e' del client, non del server
			throw new ServletException(
					"L'operazione richiesta non e' disponibile");
		}
		
		if (!service.encryptData) {
			if (debug > 1)
				System.out.println("[DBG][Proxy][doPost] validate xml content");
			
			if (!Xml.validateNode(n, new File(service.schemaLocation))) {
				// il contenuto applicativo nel soap:Body non e' valido 
				// TODO l'errore e' del client, non del server
				throw new ServletException("contenuto applicativo non valido");
			}
		}
		
		// **************************************************
		// *** Inoltra il messaggio al giusto web service ***
		// **************************************************
		
		HttpResponse httpResp = null;
		try { 
			httpResp = lai.utils.Http.post(
					service.wsEndpoint, 
					Xml.toString(doc), 
					resp.getContentType()
					);
			
		} catch(Exception e) { throw new ServletException(e); }
		
		// ************************************************************
		// *** Ricevi il messaggio di risposta 						***
		// *** ed Inoltralo al client 								***
		// *************************************************************
		
		InputStream wsResponse = httpResp.getEntity().getContent();
		BufferedInputStream bis = new BufferedInputStream(wsResponse);
		OutputStream bos = (resp.getOutputStream());
		//BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		final int BUFFER_SIZE = 1024;
		int bytesRead = 0; byte[] buffer = new byte[BUFFER_SIZE];
		// copia l'entity di httpResp in resp
		while (-1 != (bytesRead = bis.read(buffer))) 
			bos.write(buffer, 0, bytesRead);
		bos.close();

		// imposta gli header di resp
		if (httpResp.containsHeader("Content-Type")) {
			HeaderIterator i = httpResp.headerIterator("Content-Type");
			resp.setContentType(((Header)i.next()).getValue());
		} else {
			resp.setContentType("text/xml");
		}
		if (httpResp.containsHeader("Content-Length")) {
			Header h = (Header)httpResp.headerIterator("Content-Length").next();
			resp.setContentLength(Integer.valueOf((h.getValue())));
		}
		
		{ 	/* debug
			System.out.println("[DBG][Proxy][doPost] httpResp.containsHeader(Content-Type); " + httpResp.containsHeader("Content-Type"));
			HeaderIterator headerIter = httpResp.headerIterator("Content-Type");
			while (headerIter.hasNext()) {
				Header next = (Header)headerIter.next();
				System.out.println("[DBG][Proxy][doPost] nextHeader(Content-Type) = <" + next.getName() + ", " + next.getValue() + ">");
			}
			headerIter = httpResp.headerIterator();
			while (headerIter.hasNext()) {
				Header next = (Header)headerIter.next();
				System.out.println("[DBG][Proxy][doPost] nextHeader() = <" + next.getName() + ", " + next.getValue() + ">");
			}
			*/
		}
		
	}
}
