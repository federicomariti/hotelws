
package com.hotel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for cartaCredito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cartaCredito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Identificatore" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="scadenzaAnnoMese" type="{http://www.w3.org/2001/XMLSchema}gYearMonth"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cartaCredito", propOrder = {
    "tipo",
    "numero",
    "identificatore",
    "scadenzaAnnoMese"
})
public class CartaCredito {

    @XmlElement(name = "Tipo", required = true)
    protected String tipo;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Identificatore")
    protected int identificatore;
    @XmlElement(required = true)
    @XmlSchemaType(name = "gYearMonth")
    protected XMLGregorianCalendar scadenzaAnnoMese;

    public CartaCredito() {
    	;
    }
    
    public CartaCredito(String tipo, int numero, int identificatore, XMLGregorianCalendar scadenzaAnnoMese) {
    	this.tipo = tipo;
    	this.numero = numero;
    	this.identificatore = identificatore;
    	this.scadenzaAnnoMese = scadenzaAnnoMese;
    }
    
    public String toString() {
    	return "[tipo="+tipo+", numero="+numero+", identificatore="+
    	 	identificatore+", scadenza="+scadenzaAnnoMese+"]";
    }
    
    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {
        this.numero = value;
    }

    /**
     * Gets the value of the identificatore property.
     * 
     */
    public int getIdentificatore() {
        return identificatore;
    }

    /**
     * Sets the value of the identificatore property.
     * 
     */
    public void setIdentificatore(int value) {
        this.identificatore = value;
    }

    /**
     * Gets the value of the scadenzaAnnoMese property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getScadenzaAnnoMese() {
        return scadenzaAnnoMese;
    }

    /**
     * Sets the value of the scadenzaAnnoMese property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setScadenzaAnnoMese(XMLGregorianCalendar value) {
        this.scadenzaAnnoMese = value;
    }

}
