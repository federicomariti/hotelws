
package com.hotel.ricerca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.hotel.Stanza;


/**
 * <p>Java class for ricercaStanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaStanza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="arrivo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="partenza" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="stanza" type="{http://www.hotel.com/}stanza"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaStanza", propOrder = {
    "sessionId",
    "arrivo",
    "partenza",
    "stanza"
})
public class RicercaStanza {

    protected String sessionId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar arrivo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar partenza;
    @XmlElement(required = true)
    protected Stanza stanza;
    
    public RicercaStanza() {
    	;
    }
    
    public RicercaStanza(String sessionId, String arrivo, String partenza, 
    		Stanza stanza) {
    	this.sessionId = sessionId;
    	this.arrivo = StringToCalendar(arrivo);
    	this.partenza = StringToCalendar(partenza);
    	this.stanza = stanza;
    }
    
    public RicercaStanza(String sessionId, XMLGregorianCalendar arrivo, 
    		XMLGregorianCalendar partenza, Stanza stanza) {
    	this.sessionId = sessionId;
    	this.arrivo = arrivo;
    	this.partenza = partenza;
    	this.stanza = stanza;
    }

    public String toString() {
    	return "[sessionId="+sessionId+", arrivo="+arrivo.toString()+
    		", "+partenza.toString()+", stanza="+stanza.toString()+"]";
    }
    
    private String CalendarToString(XMLGregorianCalendar c) {
    	return c.getYear()+"-"+c.getMonth()+"-"+c.getDay();
    }
    
    private XMLGregorianCalendar StringToCalendar(String date) {
    	DatatypeFactory datatypeFactory = null;
    	try { datatypeFactory = DatatypeFactory.newInstance(); } 
    	catch (Exception e) { e.printStackTrace(); }
    	return datatypeFactory.newXMLGregorianCalendar(date);
    }
    
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
    
    public String getArrivoString() { 
    	return CalendarToString(arrivo);
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
    public void setArrivo(String value) {
    	arrivo = StringToCalendar(value);
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
    public String getPartenzaString() {
    	return CalendarToString(partenza);
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
    public void setPartenza(String value) {
    	partenza = StringToCalendar(value);
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

}
