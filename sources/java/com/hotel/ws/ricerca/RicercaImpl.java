package com.hotel.ws.ricerca;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import org.w3c.dom.Node;

import com.hotel.CostoStanza;
import com.hotel.RispostaHotel;
import com.hotel.Stanza;
import com.hotel.utils.Database;
import com.hotel.utils.Utils;
import com.hotel.utils.Xml;

import com.hotel.ricerca.RicercaStanza;

@WebServiceProvider(
		targetNamespace = "http://www.hotel.com/ricerca/",
		serviceName = "ricercaService",
		portName = "ricerca"
)
@ServiceMode(value = Service.Mode.MESSAGE)

public class RicercaImpl implements Provider<SOAPMessage> {

	private static int debug = 1;
	
	private static final Logger LOG = Logger.getLogger(RicercaImpl.class.getName());

	private static final String ricercaNamespace = "http://www.hotel.com/ricerca/";
	private static final String hotelNamespace = "http://www.hotel.com/";

	private static final String SERVER_HOST = "localhost";
	private static final String DATABASE_NAME = "hotel";
	private static final String DATABASE_LOGIN = "mariti";
	private static final String DATABASE_PASSW = "asdf";

	private static final QName SESSION_ID=  
		new QName(ricercaNamespace, "sessionId");
	private static final QName ARRIVO = 
		new QName(ricercaNamespace, "arrivo");
	private static final QName PARTENZA =
		new QName(ricercaNamespace, "partenza");
	private static final QName STANZA = 
		new QName(ricercaNamespace, "stanza");
	private static final QName NUM_ADULTI = 
		new QName(hotelNamespace, "numAdulti");
	private static final QName NUM_BAMBINI = 
		new QName(hotelNamespace, "numBambini");
	private static final QName CODICE_TIPO_STANZA = 
		new QName(hotelNamespace, "codiceTipoStanza");
	private static final QName TIPO_STANZA = 
		new QName(hotelNamespace, "tipoStanza");
	private static final QName DESCRIZIONE = 
		new QName(hotelNamespace, "descrizione");
	
	private class ClientException extends Exception {

		private static final long serialVersionUID = 1L;
		
		ClientException(String message) {
			super(message);
		}
		ClientException(Throwable throwable) {
			super(throwable);
		}
		ClientException(String message, Throwable throwable) {
			super(message, throwable);
		}
	}
	
// =============================================================================
	
	/**
	 * i nomi degli argomenti necessari per eseguire l'operazione di ricerca di una camera
	 */
	private static String[] argsRicerca = { "arrivo", "partenza", "numAdulti", "numBambini" };
	
	/**
	 * A partire da un elemento SOAP e da un oggetto Java allocato, mappa il 
	 * valore dell'elemnto nell'oggetto come specificato negli oggetti 
	 * <code>mappaSetter</code> e <code>mappaGetter</code>.  
	 * @param element elemento da analizzare
	 * @param object oggetto che mappa in java <code>element</code>
	 * @return la collezione dei localName dei SOAPElement che si sono trovati 
	 * all'interno di <code>element</code>
	 * @throws Exception
	 */
	private Collection<String> parseElementIn(SOAPElement element, Object object) throws Exception {
		List<String> args = new LinkedList<String>();
		Iterator<Node> i = element.getChildElements();
		while (i.hasNext()) {
			Node n = i.next();
			if (!(n instanceof SOAPElement)) continue; 
			SOAPElement child = (SOAPElement) n;
			if (debug > 1) System.out.println("[DBG][RicercaImpl][parseElementIn] "+child.getLocalName());
			
			if (mappaSetter.containsKey(child.getElementQName())) {
				args.add(child.getLocalName());
				MetodoSetter setter = mappaSetter.get(child.getElementQName());
				if (null == setter.conversioneArgomento)
					setter.metodoSetter.invoke(object, child.getTextContent());
				else
					setter.metodoSetter.invoke(object, setter.conversioneArgomento.invoke(null, child.getTextContent()));
				
			} else if (mappaGetter.containsKey(child.getElementQName())) {
				Method getter = mappaGetter.get(child.getElementQName());
				Collection<String> c = parseElementIn(child, getter.invoke(object));
				args.addAll(c);
			}
		}
		return args;
	}
	
	/**
	 * inizializza gli oggetti <code>mappaSetter</code> e <code>mappaGetter</code>
	 */
	private void initMappe() {
		try {
			mappaSetter.put(SESSION_ID, new MetodoSetter(
					RicercaStanza.class.getMethod("setSessionId", String.class), 
					null));
			mappaSetter.put(ARRIVO, new MetodoSetter(
					RicercaStanza.class.getMethod("setArrivo", String.class), 
					null));
			mappaSetter.put(PARTENZA, new MetodoSetter(
					RicercaStanza.class.getMethod("setPartenza", String.class), 
					null));
			mappaSetter.put(NUM_ADULTI, new MetodoSetter(
					Stanza.class.getMethod("setNumAdulti", int.class), 
					Integer.class.getMethod("valueOf", String.class)));
			mappaSetter.put(NUM_BAMBINI, new MetodoSetter(
					Stanza.class.getMethod("setNumBambini", int.class), 
					Integer.class.getMethod("valueOf", String.class)));
			
			mappaGetter.put(STANZA, RicercaStanza.class.getMethod("getStanza"));
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Siano X e Y due elementi SOAP, sia X un elemento senza alcun figlio
	 * SOAPElement, e Y abbia un figlio dello stesso tipo di X. Si consideri 
	 * inoltre C, la classe Java che realizza l'astrazione del tipo di dato a 
	 * cui appartiene l'elemento Y.
	 * <p>
	 * Questa mappa puo' essere usata per associare il qualified name 
	 * dell'elemento X al metodo setter della classe C che imposta proprio il 
	 * valore di X figlio di Y in un oggetto Java. Inoltre, tale classe e' 
	 * solo per quegli elementi X il cui contenuto e' testuale, non hanno altri 
	 * figli. Nel caso in cui il metodo setter associato prenda come argomento 
	 * formale un tipo diverso da String, puo' essere specificato anche un 
	 * metodo di conversione oltre al metodo setter.
	 */
	Map<QName, MetodoSetter> mappaSetter = new LinkedHashMap<QName, MetodoSetter>();

	/**
	 * Siano X e Y due elementi SOAP, sia X un elemento con uno o piu' figli
	 * SOAPElement, e Y abbia un figlio dello stesso tipo di X. Si consideri 
	 * inoltre C, la classe Java che realizza l'astrazione del tipo di dato a 
	 * cui appartiene l'elemento Y.
	 * <p>
	 * Questa mappa puo' essere usata per associare il qualified name dell'
	 * elemento X al metodo getter della classe C che fornisce l'oggetto Java 
	 * il cui tipo corrisponde al tipo dell'elemento X.
	 */
	Map<QName, Method> mappaGetter = new LinkedHashMap<QName, Method>();
	
	private static class MetodoSetter {
		Method metodoSetter;
		Method conversioneArgomento;
		
		public MetodoSetter(Method m, Method c) {
			metodoSetter = m;
			conversioneArgomento = c;
		}
	}
	
	
// =============================================================================
	
	/**
	 * crea un messaggio SOAP con il body contenete un elemento soap:Fault. 
	 *
	 * @param message il fault string
	 * @param faultCode11 il fault code nell'envelope di soap 1.1
	 * @return il messaggio SOAP
	 */
	private SOAPMessage createSOAPFaultMessage(String message, String faultCode11) {
		SOAPMessage result = null;
		try {
			MessageFactory factory = MessageFactory.newInstance();
			result = factory.createMessage();
			SOAPFault fault = result.getSOAPBody().addFault();
			fault.setFaultCode(new QName(
					SOAPConstants.URI_NS_SOAP_ENVELOPE, faultCode11));
			fault.setFaultString(message);
			result.saveChanges();
			
		} catch(SOAPException e) {
			e.printStackTrace();
		} 
		return result;
	}

	public SOAPMessage invoke(SOAPMessage req) {
		LOG.info("Executing operation invoke.");
		SOAPMessage resp = null;
		Connection con = null;
		try {
			// *** Ricava il nome dell'operazione da eseguire dal messaggio ***
			// *** SOAP ricevuto											***
			SOAPBody body = req.getSOAPBody();
			SOAPBodyElement wrapperEl = Xml.getFirstSoapBodyElement(body);
			if (null == wrapperEl) {
				//errore: il soap:Body e' vuoto
				throw new ClientException("Il corpo della busta SOAP e' vuoto");
			}

			String operationName = wrapperEl.getLocalName();
			
			LOG.info("Request execution of " + operationName + " operation");

			if (operationName.equals("ricercaStanza")) {

				// ******************************************************
				// *** Ricava i valori degli argmomenti della ricerca ***
				// *** dal messaggio SOAP ricevuto					  ***
				// ******************************************************

				RicercaStanza ricerca = new RicercaStanza();
				ricerca.setStanza(new Stanza());
				initMappe();
				List<String> argomentiTrovati = new LinkedList<String>();
				
				argomentiTrovati.addAll(parseElementIn(wrapperEl, ricerca));
				
				// verifica che la richiesta di ricerca di stanze abbia tutti 
				// gli argomenti necessari
				for (int j=0; j<argsRicerca.length; j++) 
					if (!argomentiTrovati.contains(argsRicerca[j])) 
						throw new ClientException("La richiesta di ricerca stanza non contiene l'argomento "+argsRicerca[j]);

				if (debug > 0) 
					System.out.println("[DBG][RicercaImpl][invoke] ricercaStanza="+ricerca);
				
				// *****************************************************
				// *** Interrogazione al database: 					 ***
				// *** Ricerca delle stanze che verificano i vincoli ***
				// *** e che siano diponibili per la prenotazione	 ***
				// *****************************************************
				con = Database.getConnection(SERVER_HOST, DATABASE_NAME, 
						DATABASE_LOGIN,	DATABASE_PASSW);
				con.setAutoCommit(false);

				Iterator<RispostaHotel> rispostaHotel = 
					ricercaStanza(con, ricerca).iterator();

				if (null == ricerca.getSessionId()) {
					// genera il sessionId 
					ricerca.setSessionId("123sessionid321");
				}

				// *** Memorizza la richiesta e associala al sessionID ***
				// TODO

				// *************************************
				// *** Crea il messaggio di risposta ***
				// *************************************
				resp = createRicercaStanzaResponseMessage(rispostaHotel, 
						ricerca.getSessionId());

				// *** fine esecuzione ricercaStanza ***

			} else {
				throw new ClientException("L'operazione " + operationName + " non esiste.");
				
			}

		} catch(SQLException e) { 
			e.printStackTrace();
			resp = createSOAPFaultMessage("Fault occurred while processing.", "Server");

		} catch (SOAPException e) {
			e.printStackTrace();
			resp = createSOAPFaultMessage("Fault occurred while processing.", "Server");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			resp = createSOAPFaultMessage("Fault occurred while processing.", "Server");
			
		} catch(ClientException e) {
			LOG.severe(e.getMessage());
			resp = createSOAPFaultMessage(e.getMessage(), "Client");

		} catch(Exception e) {
			e.printStackTrace();
			resp = createSOAPFaultMessage("Fault occured while processing.", "Server");
			
		} finally {
			// chiudi la connessione al database
			try { 
				if (null != con) con.close(); 
				
			} catch (SQLException e) { 
				e.printStackTrace(); 
			}

		}

		return resp;
	} /* end invoke */


	/**
	 * Tramite la connessione {@code con} interroga il database, ricerca le 
	 * stanze che ripettano i seguenti vincoli descritti nell'oggetto 
	 * {@code ricerca}: <p>
	 * - arrivo,<p>
	 * - partenza,<p>
	 * - numero di adulti,<p>
	 * - numero di bambini,<p>
	 * - tipo della stanza (non obbligatorio)<p>
	 * Viene restituita una lista di oggetti @{code RispostaHotel} che 
	 * contiene, oltre alle informazioni sul ogni signola stanza che rispetti i 
	 * vincoli precedenti, il valore di disponibilita effettivo, al momento 
	 * dell'interrogazione al database, il costo e il codice identificativo 
	 * usato dall'albergo per riferire tale stanza.<p>
	 * @param ricerca
	 * @return una lista di oggetti <code>RispostaHotel</hotel>
	 */
	public static List<RispostaHotel> ricercaStanza(Connection con, 
			RicercaStanza ricerca) throws SQLException {
		return ricercaStanza(con, ricerca, null);
	}

	/**
	 * Agisce come <code>ricercaStanza(Connection, RicercaStanza)</code> ma la
	 * ricerca viene discriminata anche dal valore dell'identificatore
	 * @param con
	 * @param ricerca
	 * @param stanzaId
	 * @return una lista di oggetti <code>RispostaHotel</hotel>
	 */
	public static List<RispostaHotel> ricercaStanza(Connection con, 
			RicercaStanza ricerca, String stanzaId)  throws SQLException {
		if (debug > 2)
			System.out.println("[DBG][RicercaImpl][ricercaStanza] ricerca=" + ricerca);

		LinkedList<RispostaHotel> risposteHotel = null;
		PreparedStatement preStmt = null;
		ResultSet stanze = null;
		try {
			String tipoSubquery = "", stanzaIdSubquery = "";
			if (null != stanzaId)
				stanzaIdSubquery = "id = ? AND "; 
			if (null != ricerca.getStanza().getCodiceTipoStanza())
				tipoSubquery = "tipoStanza = ? AND "; 

			String query = "SELECT * FROM stanze WHERE " +
			stanzaIdSubquery + tipoSubquery + 
			"numAdulti >= ? AND numBambini >= ?";

			preStmt = con.prepareStatement(query);

			int parIndex = 1;
			if (null != stanzaId) 
				preStmt.setString(parIndex++, stanzaId);
			if (null != ricerca.getStanza().getCodiceTipoStanza())
				preStmt.setInt(parIndex++, 
						ricerca.getStanza().getCodiceTipoStanza());
			preStmt.setInt(parIndex++, ricerca.getStanza().getNumAdulti());
			preStmt.setInt(parIndex++, ricerca.getStanza().getNumBambini());
			// esegui la query su stanze
			preStmt.execute();
			stanze = preStmt.getResultSet();
			risposteHotel = new LinkedList<RispostaHotel>();
			// itera tutte le stanze trovate
			while (stanze.next()) {
				if (debug > 2)
					System.out.printf("[DBG][RicercaImpl][ricercaStanza] stanza trovata (ResultSet): id=%s, numAdulti=%s, numBambini=%s, disponibilita=%s\n", 
							stanze.getString("id"), stanze.getInt("numAdulti"), stanze.getInt("numBambini"), stanze.getInt("disponibilita"));

				// interroga il database per sapere quante prenotazioni sono
				// state effettuate per la stanza corrente
				int numPrenotazioni = getNumeroPrenotazioni(
						con, 
						stanze.getString("id"),
						Date.valueOf(ricerca.getArrivoString()),
						Date.valueOf(ricerca.getPartenzaString())
				);


				int disponibilita = 
					stanze.getInt("disponibilita") - numPrenotazioni;
				if (disponibilita > 0) {
					String descrizioneTipoStanza = "";
					query = "SELECT descrizione FROM tipi_stanza " +
					"WHERE codice = ?";
					preStmt = con.prepareStatement(query);
					preStmt.setInt(1, stanze.getInt("tipoStanza"));
					preStmt.execute();
					ResultSet _descrizioneTipoStanza = preStmt.getResultSet();
					if (_descrizioneTipoStanza.next()) 
						descrizioneTipoStanza = 
							_descrizioneTipoStanza.getString(1);

					long totGiorni = Utils.calcolaNumeroGiorni(ricerca.getArrivo(), ricerca.getPartenza());

					CostoStanza costo = new CostoStanza("euro", 
							stanze.getFloat("costoPerNotte"), 
							totGiorni * stanze.getFloat("costoPerNotte")
					);
					Stanza stanza = new Stanza(stanze.getInt("numAdulti"), 
							stanze.getInt("numBambini"), 
							stanze.getInt("tipoStanza"), //codiceTipoStanza
							descrizioneTipoStanza, 
							stanze.getString("descrizione") 
					);
					risposteHotel.add(
							new RispostaHotel(stanza, disponibilita, 
									stanze.getString("id"), costo)
					);
				}						

			} /* end while (stanze.next()) * fine iterazione stanze */

		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode());

		} finally {
			if (null != stanze) stanze.close();
			if (null != preStmt) preStmt.close();

		}

		return risposteHotel;
	}

	/**
	 * Tamite la connessione @{code con} interroga il database, ricercando il 
	 * numero di prenotazioni di una specifica stanza (identificata dal codice 
	 * dell'hotel) che rientrano in uno determinato periodo di tempo.
	 * @param con la connessione al database
	 * @param idStanza l'identificatore della stanza usato dall'hotel
	 * @param arrivo la data di inizio periodo in formato yyyy-mm-dd
	 * @param partenza la data di fine periodo yyyy-mm-dd
	 * @return il numero di prenotazioni nel periodo
	 * @throws SQLException
	 */
	public static int getNumeroPrenotazioni(Connection con, String idStanza, 
			Date arrivo, Date partenza)  throws SQLException {
		if (debug > 2)
			System.out.printf("[DBG][RicercaImpl][getNumeroPrenotazioni] idStanza=%s, arrivo=%s, partenza=%s\n", 
					idStanza, arrivo, partenza);

		String query = "SELECT COUNT(*) FROM prenotazioni WHERE " +
		"idStanza = ? AND (" + 
		"( arrivo > ? AND arrivo < ? ) OR " +
		"( arrivo <= ? AND partenza > ? ))";
		PreparedStatement preStmt = con.prepareStatement(query);
		preStmt.setString(1, idStanza);
		preStmt.setDate(2, arrivo);
		preStmt.setDate(3, partenza);
		preStmt.setDate(4, arrivo);
		preStmt.setDate(5, arrivo);
		// esegui la query su prenotazioni
		preStmt.execute();
		ResultSet prenotazioni = preStmt.getResultSet();
		prenotazioni.next();
		int result = prenotazioni.getInt(1);
		prenotazioni.close();
		return result;
	}


	/**
	 * crea il messaggio SOAP di risposta ad una operazione di ricerca di una 
	 * stanza.
	 * @param rispostaHotel l'insieme delle risposte fornite dall'hotel
	 * @param sessionId l'identificatore di sessione del client
	 * @return il SOAP message di risposta
	 * @throws Exception
	 */
	private static SOAPMessage createRicercaStanzaResponseMessage(
			Iterator<RispostaHotel> rispostaHotel, String sessionId)
	throws SOAPException {
		MessageFactory msgFactory = MessageFactory.newInstance();
		SOAPMessage resp = msgFactory.createMessage();
		SOAPBody body = resp.getSOAPBody();
		// crea il wrapper element
		SOAPBodyElement wrapperEl = body.addBodyElement(
				new QName(ricercaNamespace, "ricercaStanzaResponse"));				
		// L0: riempi il wrapper element
		wrapperEl.addChildElement(
				new QName(ricercaNamespace, "sessionId")).
				setTextContent(sessionId);
		while (rispostaHotel.hasNext()) {
			SOAPElement rispostaHotelEl = wrapperEl.addChildElement(
					new QName(ricercaNamespace, "rispostaHotel"));
			RispostaHotel next = rispostaHotel.next();

			// L1: riempi il rispostaHotel element corrente
			SOAPElement stanzaEl = rispostaHotelEl.addChildElement(
					new QName(hotelNamespace, "stanza"));
			rispostaHotelEl.addChildElement(
					new QName(hotelNamespace, "disponibilita")).
					setTextContent(String.valueOf(next.getDisponibilita()));
			rispostaHotelEl.addChildElement(
					new QName(hotelNamespace, "stanzaId")).
					setTextContent(next.getstanzaId());
			SOAPElement costoEl = rispostaHotelEl.addChildElement(
					new QName(hotelNamespace, "costo"));

			// L2: riempi la stanza element
			stanzaEl.addChildElement(NUM_ADULTI).setTextContent(
					String.valueOf(next.getStanza().getNumAdulti()));
			stanzaEl.addChildElement(NUM_BAMBINI).setTextContent(
					String.valueOf(next.getStanza().getNumBambini()));
			stanzaEl.addChildElement(CODICE_TIPO_STANZA).setTextContent(
					String.valueOf(next.getStanza().getCodiceTipoStanza()));
			//tipo stanza (descrizione breve)
			stanzaEl.addChildElement(TIPO_STANZA).setTextContent(
					String.valueOf(next.getStanza().getTipoStanza()));
			stanzaEl.addChildElement(DESCRIZIONE).setTextContent(
					next.getStanza().getDescrizione());

			// L2: riempi il costo element
			costoEl.addChildElement(
					new QName(hotelNamespace, "valuta")).
					setTextContent("euro");
			costoEl.addChildElement(
					new QName(hotelNamespace, "perNotte")).
					setTextContent(String.valueOf(
							next.getCosto().getPerNotte()));
			costoEl.addChildElement(
					new QName(hotelNamespace, "totale")).
					setTextContent(String.valueOf(
							next.getCosto().getTotale()));

		} /* end while (rispostaHotel.next()) */

		return resp;
	}
}
