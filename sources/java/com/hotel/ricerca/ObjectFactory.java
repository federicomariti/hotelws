
package com.hotel.ricerca;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hotel.ricerca package. 
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

    private final static QName _RicercaStanzaResponse_QNAME = new QName("http://www.hotel.com/ricerca/", "ricercaStanzaResponse");
    private final static QName _FaultString_QNAME = new QName("http://www.hotel.com/ricerca/", "faultString");
    private final static QName _RicercaStanza_QNAME = new QName("http://www.hotel.com/ricerca/", "ricercaStanza");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hotel.ricerca
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RicercaStanza }
     * 
     */
    public RicercaStanza createRicercaStanza() {
        return new RicercaStanza();
    }

    /**
     * Create an instance of {@link RicercaStanzaResponse }
     * 
     */
    public RicercaStanzaResponse createRicercaStanzaResponse() {
        return new RicercaStanzaResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaStanzaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/ricerca/", name = "ricercaStanzaResponse")
    public JAXBElement<RicercaStanzaResponse> createRicercaStanzaResponse(RicercaStanzaResponse value) {
        return new JAXBElement<RicercaStanzaResponse>(_RicercaStanzaResponse_QNAME, RicercaStanzaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/ricerca/", name = "faultString")
    public JAXBElement<String> createFaultString(String value) {
        return new JAXBElement<String>(_FaultString_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaStanza }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hotel.com/ricerca/", name = "ricercaStanza")
    public JAXBElement<RicercaStanza> createRicercaStanza(RicercaStanza value) {
        return new JAXBElement<RicercaStanza>(_RicercaStanza_QNAME, RicercaStanza.class, null, value);
    }

}
