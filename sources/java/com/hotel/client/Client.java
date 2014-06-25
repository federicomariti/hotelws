package com.hotel.client;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hotel.CartaCredito;
import com.hotel.Persona;
import com.hotel.RispostaHotel;
import com.hotel.Stanza;
import com.hotel.StatoPrenotazione;
import com.hotel.ws.prenotazione.Prenotazione;
import com.hotel.ws.ricerca.Ricerca;

public class Client {

	private static final String USAGE_1 = "-o ricerca -b arrivo -e partenza -a numAdulti -c numBambini [--codiceTipoStanza codiceTipoStanza] [-i sessionId]";
	
	private static final String USAGE_2 = "-o prenotazione -s idStanza -b arrivo -e partenza -a " +
			"numAdulti -c numBambini -p Persona -C CartaCredito " +
			"[--etaBambini <etaBambini>] [-i sessionId] ";
	
	private static final String USAGE_TYPES = 
			"Persona = codiceFiscale:nome:cognome:email:telefono\n" +
			"CardaCredito = tipo:numero:identificatore:scadenza\n" +
			"IntegerList = integer[,integer[,...]]";
	
	private static void printHelp(HelpFormatter hf, Options options) {
		PrintWriter out = new PrintWriter(System.out);
		int width = 80;
		hf.printUsage(out, width, USAGE_1);
		hf.printUsage(out, width, USAGE_2);
		hf.printOptions(out, width, options, 2, 1);
		hf.printWrapped(out, width, USAGE_TYPES);
		out.close();
	}
	
	private static boolean verificaParametri(CommandLine cl, String[] p) {
		for (int i=0; i<p.length; i++) 
			if (!cl.hasOption(p[i])) {
				System.out.println("Parametro '" + p[i] +
						   "' non presente.");
				return false;
			}
		return true;
	}
	
	private static final int EXITVALUE_GENERIC_ERROR = 1;
	private static final int EXITVALUE_OPZ_NONSPECIF = 2;
	private static final int EXITVALUE_OPZ_NONVALIDA = 3;
	private static final int EXITVALUE_PRM_NONSPECIF = 4;
	
	private static ClassPathXmlApplicationContext context = 
		new ClassPathXmlApplicationContext("com/hotel/client/beans.xml");
	
	public static void main(String[] args) throws Exception {
		int returnValue = 0;
		
		Options options = new Options();
		{
			Option o = new Option("h", "help", false, "");
			options.addOption(o);
			o = new Option("o", "operation", true, "operazione da esegire");
			o.setArgName("operationName"); options.addOption(o);
			o = new Option("i", "sessionId", true, "operation argument");
			o.setArgName("string"); options.addOption(o);
			o = new Option("b", "arrivo", true, "operation argument");
			o.setArgName("yyyy-mm-dd"); options.addOption(o);
			o = new Option("e", "partenza", true, "operation argument");
			o.setArgName("yyyy-mm-dd"); options.addOption(o);
			o = new Option("a", "numAdulti", true, "operation argument");
			o.setArgName("integer"); options.addOption(o);
			o = new Option("c", "numBambini", true, "operation argument");
			o.setArgName("integer"); options.addOption(o);
			o = new Option("s", "stanzaId", true, "operation argument");
			o.setArgName("string"); options.addOption(o);
			o = new Option("p", "persona", true, "operation argument");
			o.setArgName("Persona"); options.addOption(o);
			o = new Option("C", "cartaCredito", true, "operation argument");
			o.setArgName("CartaCredito"); options.addOption(o);
			o = new Option(null, "etaBambini", true, "operation argument");
			o.setArgName("IntegerList"); options.addOption(o);
			o = new Option(null, "codiceTipoStanza", true, "operation argument");
			o.setArgName("int"); options.addOption(o);
		}
		
		HelpFormatter hf = new HelpFormatter();
		CommandLineParser clp = new PosixParser();
		CommandLine cl = null;
		
		try { cl = clp.parse(options, args); }
		catch (Exception e) { 
			printHelp(hf, options);
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		// ----
		
		String operationName = "";
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		
		// ----
		
		if (cl.hasOption("help")) {
			printHelp(hf, options);
			System.exit(0);
		}
		if (cl.hasOption("operation")) 
			operationName = cl.getOptionValue("operation");
		
		//----
		
		if (operationName.equals("_ricerca")) {
			String sessionId = "122333ABC", 
				arrivo = "2011-12-31",
				partenza = "2012-01-02",
				numAdulti = "2",
				numBambini = "2";
			Ricerca ricerca = (Ricerca)context.getBean("ricerca");
			Holder<String> _sessionId = new Holder<String>(sessionId);
			XMLGregorianCalendar _arrivo = datatypeFactory.newXMLGregorianCalendar(arrivo);
			XMLGregorianCalendar _partenza = datatypeFactory.newXMLGregorianCalendar(partenza);
			Stanza _stanza = new Stanza(Integer.valueOf(numAdulti),
					Integer.valueOf(numBambini));
			Holder<List<RispostaHotel>> _rispostaHotel = new Holder<List<RispostaHotel>>(); 
			
			ricerca.ricercaStanza(_sessionId, _arrivo, _partenza, _stanza, _rispostaHotel);
			
			Iterator<RispostaHotel> i = 
				(Iterator<RispostaHotel>)_rispostaHotel.value.iterator();
			while (i.hasNext()) 
				System.out.println("risposta hotel=" + i.next());
			
		} else if (operationName.startsWith("ricerca")){
			// *******************
			// ***   Ricerca   ***
			// *******************
			String[] paramNecessari = {"arrivo", "partenza", "numAdulti", 
					"numBambini" };
			if (!verificaParametri(cl, paramNecessari))
				System.exit(EXITVALUE_PRM_NONSPECIF);
			
			Holder<String> sessionId = 
				new Holder<String>(cl.getOptionValue("sessionId"));
			
			XMLGregorianCalendar arrivo = 
				datatypeFactory.newXMLGregorianCalendar(cl.getOptionValue("arrivo"));
			
			XMLGregorianCalendar partenza = 
				datatypeFactory.newXMLGregorianCalendar(cl.getOptionValue("partenza"));
			
			Integer codiceTipoStanza = cl.hasOption("codiceTipoStanza") ? 
					Integer.valueOf(cl.getOptionValue("codiceTipoStanza")) :
						null;
			
			Stanza stanza = new Stanza(
					Integer.valueOf(cl.getOptionValue("numAdulti")),
					Integer.valueOf(cl.getOptionValue("numBambini")), 
					cl.getOptionValue("etaBambini"), ',', codiceTipoStanza
					);
				
			Holder<List<RispostaHotel>> rispostaHotel = 
				new Holder<List<RispostaHotel>>(); 
			
			// invloca la ricerca
			Ricerca ricerca = (Ricerca) context.getBean(operationName);
			ricerca.ricercaStanza(sessionId, arrivo, partenza, stanza, rispostaHotel);
			
			// stampa la risposta
			Iterator<RispostaHotel> i = 
				(Iterator<RispostaHotel>)rispostaHotel.value.iterator();
			while (i.hasNext()) 
				System.out.println("risposta hotel=" + i.next());
			
		} else if (operationName.startsWith("prenotazione")) {
			// ************************
			// ***   Prenotazione   ***
			// ************************
			String[] paramNecessari = { "arrivo", "partenza", "numAdulti", 
					"numBambini", "persona", "cartaCredito" };
			if (!verificaParametri(cl, paramNecessari))
				System.exit(EXITVALUE_PRM_NONSPECIF);
			
			// argomenti della prenotazione obbligatori
			XMLGregorianCalendar arrivo = 
				datatypeFactory.newXMLGregorianCalendar(cl.getOptionValue("arrivo"));
			
			XMLGregorianCalendar partenza = 
				datatypeFactory.newXMLGregorianCalendar(cl.getOptionValue("partenza"));
			
			int numAdulti = Integer.valueOf(cl.getOptionValue("numAdulti"));
			
			int numBambini = Integer.valueOf(cl.getOptionValue("numBambini"));
			
			String _persona = cl.getOptionValue("persona");
			String[] tmp = _persona.split(":");
			Persona persona = 
				new Persona(tmp.length>0?tmp[0]:null, tmp.length>1?tmp[1]:null,
						tmp.length>2?tmp[2]:null, tmp.length>3?tmp[3]:null, 
						tmp.length>4?tmp[4]:null);
			
			String _cartaCredito = cl.getOptionValue("cartaCredito");
			tmp = _cartaCredito.split(":");
			if (tmp.length != 4) {
				System.out.println("argomento <CartaCredito> non valido.");
				printHelp(hf, options);
				System.exit(EXITVALUE_PRM_NONSPECIF);
			}
			CartaCredito cartaCredito = new CartaCredito(tmp[0], 
					Integer.valueOf(tmp[1]), Integer.valueOf(tmp[2]), 
					datatypeFactory.newXMLGregorianCalendar(tmp[3]));
			
			// argomenti della prenotazione facoltativi
			Holder<String> sessionId = 
				new Holder<String>(cl.getOptionValue("sessionId"));
			
			String stanzaId = cl.getOptionValue("stanzaId");
			
			String etaBambini = cl.getOptionValue("etaBambini");
			
			Integer codiceTipoStanza = cl.hasOption("codiceTipoStanza") ? 
					Integer.valueOf(cl.getOptionValue("codiceTipoStanza")) :
						null;
			
			
			Stanza stanza = new Stanza(
					numAdulti, numBambini, etaBambini, ',', codiceTipoStanza);
			
			// holders risultati
			Holder<List<RispostaHotel>> rispostaHotel = 
				new Holder<List<RispostaHotel>>(); 
			Holder<StatoPrenotazione> esito = new Holder<StatoPrenotazione>();
			Holder<String> prenotazioneId = new Holder<String>();
			
			// invoca la prenotazione
			Prenotazione prenotazione = 
				(Prenotazione) context.getBean(operationName);			
			prenotazione.prenotazione(sessionId, arrivo, partenza, stanzaId, 
					stanza, persona, cartaCredito, esito, prenotazioneId, 
					rispostaHotel);

			System.out.printf("esito=%s; prenotazioneId=%s; rispostaHotel=%s\n",
					esito.value, prenotazioneId.value, 
					rispostaHotel.value);
			
		} else if (operationName.equals("")){
			System.err.println("operazione non specificata");
			returnValue = EXITVALUE_OPZ_NONSPECIF;
			
		} else {
			System.err.println("operazione non valida");
			returnValue = EXITVALUE_OPZ_NONVALIDA;
			
		}
		
		System.exit(returnValue);
	}

}
