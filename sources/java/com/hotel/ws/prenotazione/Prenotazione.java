package com.hotel.ws.prenotazione;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2012-01-02T11:24:28.959+01:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://www.hotel.com/ws/prenotazione/", name = "prenotazione")
@XmlSeeAlso({com.hotel.prenotazione.ObjectFactory.class, com.hotel.ObjectFactory.class})
public interface Prenotazione {

    @ResponseWrapper(localName = "prenotazioneResponse", targetNamespace = "http://www.hotel.com/prenotazione/", className = "com.hotel.prenotazione.PrenotazioneResponse")
    @RequestWrapper(localName = "prenotazione", targetNamespace = "http://www.hotel.com/prenotazione/", className = "com.hotel.prenotazione.Prenotazione")
    @WebMethod
    public void prenotazione(
        @WebParam(mode = WebParam.Mode.INOUT, name = "sessionId", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.ws.Holder<java.lang.String> sessionId,
        @WebParam(name = "arrivo", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.datatype.XMLGregorianCalendar arrivo,
        @WebParam(name = "partenza", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.datatype.XMLGregorianCalendar partenza,
        @WebParam(name = "stanzaId", targetNamespace = "http://www.hotel.com/prenotazione/")
        java.lang.String stanzaId,
        @WebParam(name = "stanza", targetNamespace = "http://www.hotel.com/prenotazione/")
        com.hotel.Stanza stanza,
        @WebParam(name = "persona", targetNamespace = "http://www.hotel.com/prenotazione/")
        com.hotel.Persona persona,
        @WebParam(name = "cartaCredito", targetNamespace = "http://www.hotel.com/prenotazione/")
        com.hotel.CartaCredito cartaCredito,
        @WebParam(mode = WebParam.Mode.OUT, name = "esito", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.ws.Holder<com.hotel.StatoPrenotazione> esito,
        @WebParam(mode = WebParam.Mode.OUT, name = "prenotazioneId", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.ws.Holder<java.lang.String> prenotazioneId,
        @WebParam(mode = WebParam.Mode.OUT, name = "rispostaHotel", targetNamespace = "http://www.hotel.com/prenotazione/")
        javax.xml.ws.Holder<java.util.List<com.hotel.RispostaHotel>> rispostaHotel
    );
}
