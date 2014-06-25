
package com.hotel;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hotel package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hotel
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RispostaHotel }
     * 
     */
    public RispostaHotel createRispostaHotel() {
        return new RispostaHotel();
    }

    /**
     * Create an instance of {@link CartaCredito }
     * 
     */
    public CartaCredito createCartaCredito() {
        return new CartaCredito();
    }

    /**
     * Create an instance of {@link CostoStanza }
     * 
     */
    public CostoStanza createCostoStanza() {
        return new CostoStanza();
    }

    /**
     * Create an instance of {@link Stanza }
     * 
     */
    public Stanza createStanza() {
        return new Stanza();
    }

    /**
     * Create an instance of {@link Indirizzo }
     * 
     */
    public Indirizzo createIndirizzo() {
        return new Indirizzo();
    }

    /**
     * Create an instance of {@link Persona }
     * 
     */
    public Persona createPersona() {
        return new Persona();
    }

}
