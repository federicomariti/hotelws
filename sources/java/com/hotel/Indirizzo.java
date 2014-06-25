
package com.hotel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for indirizzo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="indirizzo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="citta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paese" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codicePostale" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "indirizzo", propOrder = {
    "indirizzo",
    "citta",
    "stato",
    "paese",
    "codicePostale"
})
public class Indirizzo {

    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String citta;
    @XmlElement(required = true)
    protected String stato;
    @XmlElement(required = true)
    protected String paese;
    protected int codicePostale;

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the citta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Sets the value of the citta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitta(String value) {
        this.citta = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

    /**
     * Gets the value of the paese property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaese() {
        return paese;
    }

    /**
     * Sets the value of the paese property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaese(String value) {
        this.paese = value;
    }

    /**
     * Gets the value of the codicePostale property.
     * 
     */
    public int getCodicePostale() {
        return codicePostale;
    }

    /**
     * Sets the value of the codicePostale property.
     * 
     */
    public void setCodicePostale(int value) {
        this.codicePostale = value;
    }

}
