
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.hotel.ws.prenotazione;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.hotel.RispostaHotel;
import com.hotel.StatoPrenotazione;
import com.hotel.ricerca.RicercaStanza;
import com.hotel.utils.Database;
import com.hotel.ws.ricerca.FaultResponse;
import com.hotel.ws.ricerca.RicercaImpl;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2012-01-02T11:24:28.874+01:00
 * Generated source version: 2.4.2
 * 
 */

@javax.jws.WebService(
		serviceName = "prenotazioneService",
		portName = "prenotazione",
		targetNamespace = "http://www.hotel.com/ws/prenotazione/",
		wsdlLocation = "prenotazione.wsdl",
		endpointInterface = "com.hotel.ws.prenotazione.Prenotazione")

		public class PrenotazioneImpl implements Prenotazione {

	private static int debug = 1;

	private static final boolean QUERY_FOR_PRENOTAZIONE_ID = true;
	/**
	 * il primo id delle prenotazioni da inserire nel database
	 */
	private static final String PRENOTAZIONE_ID = "AA0000";
	/**
	 * l'ultimo id delle prenotazioni inserito nel databse
	 */
	private String prenotazioneId = "AA0003";

	private static final Logger LOG = Logger.getLogger(PrenotazioneImpl.class.getName());

	private static final String SERVER_HOST = "localhost";
	private static final String DATABASE_NAME = "hotel";
	private static final String DATABASE_LOGIN = "mariti";
	private static final String DATABASE_PASSW = "asdf";

	/* (non-Javadoc)
	 * @see com.hotel.ws.prenotazione.Prenotazione#prenotazione(java.lang.String  sessionId ,)javax.xml.datatype.XMLGregorianCalendar  arrivo ,)javax.xml.datatype.XMLGregorianCalendar  partenza ,)java.lang.String  stanzaId ,)com.hotel.Stanza  stanza ,)com.hotel.Persona  persona ,)com.hotel.CartaCredito  cartaCredito ,)com.hotel.StatoPrenotazione  esito ,)java.lang.String  prenotazioneId ,)java.util.List<com.hotel.RispostaHotel>  rispostaHotel )*
	 */
	public void prenotazione(
			javax.xml.ws.Holder<java.lang.String> sessionId,
			javax.xml.datatype.XMLGregorianCalendar arrivo,
			javax.xml.datatype.XMLGregorianCalendar partenza,
			java.lang.String stanzaId,
			com.hotel.Stanza stanza,
			com.hotel.Persona persona,
			com.hotel.CartaCredito cartaCredito,
			javax.xml.ws.Holder<com.hotel.StatoPrenotazione> esito,
			javax.xml.ws.Holder<java.lang.String> prenotazioneId,
			javax.xml.ws.Holder<java.util.List<com.hotel.RispostaHotel>> rispostaHotel) { 
		LOG.info("Executing operation prenotazione");
		if (debug > 0) {
			System.out.print("sessionId = " + sessionId.value);
			System.out.print(", arrivo = " + arrivo);
			System.out.print(", partenza = " + partenza);
			System.out.print(", stanzaId = " + stanzaId);
			System.out.print(", stanza = " + stanza);
			System.out.print(", persona = " + persona);
			System.out.println(", cartaCredito = " + cartaCredito);
		}

		Connection con = null;
		ResultSet result = null; 

		try {
			con = Database.getConnection(SERVER_HOST, DATABASE_NAME, 
					DATABASE_LOGIN,	DATABASE_PASSW);
			con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			con.setAutoCommit(false);

			boolean conflittiSerializzazione = false;

			do {// fin tanto che si presentano conflitti nella serializzazione delle
				// transazioni nel database, esegui il rollback della transazione 
				// e ritenta l'operazione di prenotazione

				// *******************************************************
				// *** Ricerca della stanza specificata, con valore di ***
				// *** diponibilita' effettivo						   ***
				// ******************************************************* 
				RicercaStanza ricerca = 
					new RicercaStanza(sessionId.value, arrivo, partenza, stanza);

				List<RispostaHotel> _rispostaHotel =
					RicercaImpl.ricercaStanza(con, ricerca, stanzaId);

				// *** prenotazione ***

				if (null == stanzaId || _rispostaHotel.size() > 1) {
					// prenotazione ambigua
					if (debug > 1)
						System.out.println("[DBG][PrenotazioneImpl][prenotazione] ambigua");
					esito.value = StatoPrenotazione.NL;
					prenotazioneId.value = null;
					rispostaHotel.value = _rispostaHotel;

				} else if (_rispostaHotel.isEmpty())  {
					// stanza non disponibile
					if (debug > 1)
						System.out.println("[DBG][PrenotazioneImpl][prenotazione] non disponibile");
					esito.value = StatoPrenotazione.NL;
					prenotazioneId.value = null;
					rispostaHotel.value = _rispostaHotel;

				} else {
					// *************************************************************
					// *** prenotazione della stanza con identificatore stanzaId ***
					// *************************************************************
					
					if (debug > 1) 
						System.out.println("[DBG][PrenotazioneImpl][prenotazione] start prenotazione");
					
					try {

						// verifica se il cliente e' gia' registrato
						String query = "SELECT * FROM ospiti WHERE codiceFiscale = ?";
						PreparedStatement preStmt = con.prepareStatement(query);
						preStmt.setString(1, persona.getCodiceFiscale());
						preStmt.execute();
						result = preStmt.getResultSet();
						if (result.next()) { // cliente gia' registrato
							;

						} else { // cliente non registrato

							// *** registazione del cliente ***
							query = "INSERT INTO ospiti (codiceFiscale, nome, " +
							"cognome, email, telefono) VALUES (?, ?, ?, ?, ?)";
							preStmt = con.prepareStatement(query);
							preStmt.setString(1, persona.getCodiceFiscale());
							preStmt.setString(2, persona.getNome());
							preStmt.setString(3, persona.getCognome());
							preStmt.setString(4, persona.getEmail());
							preStmt.setString(5, persona.getTelefono());
							preStmt.execute();

						}
						result.close();
						
						// *** registrazione della prenotazione ***

						// genera prenotazione id 
						if (QUERY_FOR_PRENOTAZIONE_ID) {
							query = "SELECT MAX(id) FROM dettaglio_prenotazioni";
							preStmt = con.prepareStatement(query);
							preStmt.execute();
							result = preStmt.getResultSet();
							result.next();
							this.prenotazioneId = result.getString(1) == null ? 
									PRENOTAZIONE_ID : result.getString(1);
							result.close();

						} 
						int a = Integer.valueOf(this.prenotazioneId.substring(2)) + 1;
						this.prenotazioneId = prenotazioneId.value =
							this.prenotazioneId.substring(0, 2) + addZeros(a);

						// lo stato della prenotazione, se tutto va bene
						esito.value = StatoPrenotazione.PD;

						query = "INSERT INTO dettaglio_prenotazioni " +
						"(id, numAdulti, numBambini, codiceFiscale, tipoCC, " +
						"numeroCC, ideCC, scadenzaCC, etaBambini) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
						preStmt = con.prepareStatement(query);
						preStmt.setString(1, this.prenotazioneId);
						preStmt.setInt(2, stanza.getNumAdulti());
						preStmt.setInt(3, stanza.getNumBambini());
						preStmt.setString(4, persona.getCodiceFiscale());
						preStmt.setString(5, cartaCredito.getTipo());
						preStmt.setInt(6, cartaCredito.getNumero());
						preStmt.setInt(7, cartaCredito.getIdentificatore());
						preStmt.setString(8, cartaCredito.getScadenzaAnnoMese().toString());
						preStmt.setString(9, stanza.getEtaBambiniString());
						preStmt.execute();

						query = "INSERT INTO prenotazioni " +
						"(idStanza, idPrenotazione, stato, arrivo, partenza) " +
						"VALUES (?, ?, ?, ?, ?)";
						preStmt = con.prepareStatement(query);
						preStmt.setString(1, stanzaId);
						preStmt.setString(2, this.prenotazioneId);
						preStmt.setString(3, esito.value.value());
						preStmt.setDate(4, Date.valueOf(arrivo.toString()));
						preStmt.setDate(5, Date.valueOf(partenza.toString()));
						preStmt.execute();

						// consolida le modifiche effettuate sul database
						con.commit();

						// *** end prenotazione *** 
						
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("------\n"+e.getMessage());
						
						// annulla tutti i cambiamenti effettuati sul database
						try { con.rollback(); } 
						catch(SQLException e2) { e2.printStackTrace(); }

						if (e.getMessage().equals(
						"Could not serialize access due to concurrent update")) {
							// ritenta la prenotazione
							conflittiSerializzazione = true;

						} else {
							// altro errore, termina la transazione
							conflittiSerializzazione = false;
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					} 
				}
			} while(conflittiSerializzazione);
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		} finally {
			// chiudi la connessione al database
			try { con.close(); } 
			catch(SQLException e) { e.printStackTrace(); }

		}

	}

	private static String addZeros(int a) {
		String b = "";
		if (a%10000 < 10) b = "000";
		else if (a%10000 < 100) b = "00";
		else if (a%10000 < 1000) b = "0";
		return b + a;
	}

}