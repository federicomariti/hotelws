
package com.hotel.prenotazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.hotel.CartaCredito;
import com.hotel.Persona;
import com.hotel.Stanza;


/**
 * 
 * 	La prenotazione contiene tutte le informazioni presenti in una
 * 	ricerca di stanza, piu' le caratteristiche della stanza, le
 * 	informazioni sul cliente e relativa carta di credito.
 *     
 * 
 * <p>Java class for prenotazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="prenotazione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="arrivo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="partenza" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="stanzaId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stanza" type="{http://www.hotel.com/}stanza"/>
 *         &lt;element name="persona" type="{http://www.hotel.com/}persona"/>
 *         &lt;element name="cartaCredito" type="{http://www.hotel.com/}cartaCredito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prenotazione", propOrder = {
    "sessionId",
    "arrivo",
    "partenza",
    "stanzaId",
    "stanza",
    "persona",
    "cartaCredito"
})
public class Prenotazione {

    @XmlElement(required = true)
    protected String sessionId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar arrivo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar partenza;
    @XmlElement(required = true)
    protected String stanzaId;
    @XmlElement(required = true)
    protected Stanza stanza;
    @XmlElement(required = true)
    protected Persona persona;
    @XmlElement(required = true)
    protected CartaCredito cartaCredito;

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the arrivo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivo() {
        return arrivo;
    }

    /**
     * Sets the value of the arrivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivo(XMLGregorianCalendar value) {
        this.arrivo = value;
    }

    /**
     * Gets the value of the partenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPartenza() {
        return partenza;
    }

    /**
     * Sets the value of the partenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPartenza(XMLGregorianCalendar value) {
        this.partenza = value;
    }

    /**
     * Gets the value of the stanzaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStanzaId() {
        return stanzaId;
    }

    /**
     * Sets the value of the stanzaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStanzaId(String value) {
        this.stanzaId = value;
    }

    /**
     * Gets the value of the stanza property.
     * 
     * @return
     *     possible object is
     *     {@link Stanza }
     *     
     */
    public Stanza getStanza() {
        return stanza;
    }

    /**
     * Sets the value of the stanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Stanza }
     *     
     */
    public void setStanza(Stanza value) {
        this.stanza = value;
    }

    /**
     * Gets the value of the persona property.
     * 
     * @return
     *     possible object is
     *     {@link Persona }
     *     
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Sets the value of the persona property.
     * 
     * @param value
     *     allowed object is
     *     {@link Persona }
     *     
     */
    public void setPersona(Persona value) {
        this.persona = value;
    }

    /**
     * Gets the value of the cartaCredito property.
     * 
     * @return
     *     possible object is
     *     {@link CartaCredito }
     *     
     */
    public CartaCredito getCartaCredito() {
        return cartaCredito;
    }

    /**
     * Sets the value of the cartaCredito property.
     * 
     * @param value
     *     allowed object is
     *     {@link CartaCredito }
     *     
     */
    public void setCartaCredito(CartaCredito value) {
        this.cartaCredito = value;
    }

}
