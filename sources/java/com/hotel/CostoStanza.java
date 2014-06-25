
package com.hotel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for costoStanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="costoStanza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="valuta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="perNotte" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totale" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "costoStanza", propOrder = {
    "valuta",
    "perNotte",
    "totale"
})
public class CostoStanza {

    @XmlElement(required = true)
    protected String valuta;
    protected double perNotte;
    protected double totale;
    
    public CostoStanza() {
    	;
    }
    
    public CostoStanza(String valuta, double perNotte, double totale) {
    	this.valuta = valuta;
    	this.perNotte = perNotte;
    	this.totale = totale;
    }
    
    public String toString() {
    	return "["+valuta+", "+perNotte+", "+totale+"]";
    }

    /**
     * Gets the value of the valuta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValuta() {
        return valuta;
    }

    /**
     * Sets the value of the valuta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValuta(String value) {
        this.valuta = value;
    }

    /**
     * Gets the value of the perNotte property.
     * 
     */
    public double getPerNotte() {
        return perNotte;
    }

    /**
     * Sets the value of the perNotte property.
     * 
     */
    public void setPerNotte(double value) {
        this.perNotte = value;
    }

    /**
     * Gets the value of the totale property.
     * 
     */
    public double getTotale() {
        return totale;
    }

    /**
     * Sets the value of the totale property.
     * 
     */
    public void setTotale(double value) {
        this.totale = value;
    }

}
