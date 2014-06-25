
package com.hotel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 	Usato nelle risposte alla ricerca/prenotazione di stanze per
 * 	definire un insieme di stanze che rispettano i vincoli imopsti
 * 	nella ricerca/prenotazione. Ogni stanza nella risposta ha il
 * 	relativo costo, per notte e totale. 
 *     
 * 
 * <p>Java class for rispostaHotel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rispostaHotel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stanza" type="{http://www.hotel.com/}stanza"/>
 *         &lt;element name="disponibilita" type="{http://www.hotel.com/}positiveInteger"/>
 *         &lt;element name="stanzaId" type="{http://www.hotel.com/}stanzaId"/>
 *         &lt;element name="costo" type="{http://www.hotel.com/}costoStanza"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rispostaHotel", propOrder = {
    "stanza",
    "disponibilita",
    "stanzaId",
    "costo"
})
public class RispostaHotel {

	@XmlElement(required = true)
    protected Stanza stanza;
    protected int disponibilita;
    @XmlElement(required = true)
    protected String stanzaId;
    @XmlElement(required = true)
    protected CostoStanza costo;
    
    public RispostaHotel() {
    	;
    }
    
    public RispostaHotel(Stanza stanza, int disponibilita, String stanzaId,
    		CostoStanza costo) {
    	this.stanza = stanza; 
    	this.disponibilita = disponibilita;
    	this.stanzaId = stanzaId;
    	this.costo = costo;
    }

    public String toString() {
    	return "[stanza="+stanza+"; disponibilita="+disponibilita+
    	"; stanzaId="+stanzaId+"; costo="+costo+ 
    		"]";
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
     * Gets the value of the disponibilita property.
     * 
     */
    public int getDisponibilita() {
        return disponibilita;
    }

    /**
     * Sets the value of the disponibilita property.
     * 
     */
    public void setDisponibilita(int value) {
        this.disponibilita = value;
    }

    /**
     * Gets the value of the stanzaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getstanzaId() {
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
    public void setstanzaId(String value) {
        this.stanzaId = value;
    }

    /**
     * Gets the value of the costo property.
     * 
     * @return
     *     possible object is
     *     {@link CostoStanza }
     *     
     */
    public CostoStanza getCosto() {
        return costo;
    }

    /**
     * Sets the value of the costo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostoStanza }
     *     
     */
    public void setCosto(CostoStanza value) {
        this.costo = value;
    }

}
