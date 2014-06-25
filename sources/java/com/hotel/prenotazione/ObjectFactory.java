
package com.hotel.prenotazione;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hotel.prenotazione package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancellaPrenotazione_QNAME = new QName("http://www.hotel.com/prenotazione/", "cancellaPrenotazione");
    private final static QName _Prenotazione_QNAME = new QName("http://www.hotel.com/prenotazione/", "prenotazione");
    private final static QName _CancellaPrenotazioneResponse_QNAME = new QName("http://www.hotel.com/prenotazione/", "cancellaPrenotazioneResponse");
    private final static QName _PrenotazioneResponse_QNAME = new QName("http://www.hotel.com/prenotazione/", "prenotazioneResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hotel.prenotazione
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Prenotazione }
     * 
     */
    public Prenotazione createPrenotazione() {
        return new Prenotazione();
    }

    /**
     * Create an instance of {@link PrenotazioneResponse }
     * 
     */
    public PrenotazioneResponse createPrenotazioneResponse() {
        return new PrenotazioneResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/prenotazione/", name = "cancellaPrenotazione")
    public JAXBElement<Object> createCancellaPrenotazione(Object value) {
        return new JAXBElement<Object>(_CancellaPrenotazione_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Prenotazione }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/prenotazione/", name = "prenotazione")
    public JAXBElement<Prenotazione> createPrenotazione(Prenotazione value) {
        return new JAXBElement<Prenotazione>(_Prenotazione_QNAME, Prenotazione.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/prenotazione/", name = "cancellaPrenotazioneResponse")
    public JAXBElement<Object> createCancellaPrenotazioneResponse(Object value) {
        return new JAXBElement<Object>(_CancellaPrenotazioneResponse_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrenotazioneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/prenotazione/", name = "prenotazioneResponse")
    public JAXBElement<PrenotazioneResponse> createPrenotazioneResponse(PrenotazioneResponse value) {
        return new JAXBElement<PrenotazioneResponse>(_PrenotazioneResponse_QNAME, PrenotazioneResponse.class, null, value);
    }

}
