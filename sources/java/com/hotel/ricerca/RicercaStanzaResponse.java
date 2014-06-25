
package com.hotel.ricerca;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.hotel.RispostaHotel;


/**
 * 
 * 	La risposta alla ricerca si una stanza contiene un insisem di
 * 	stanze con relativa descrizione e costo.
 *     
 * 
 * <p>Java class for ricercaStanzaResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ricercaStanzaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rispostaHotel" type="{http://www.hotel.com/}rispostaHotel" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaStanzaResponse", propOrder = {
    "sessionId",
    "rispostaHotel"
})
public class RicercaStanzaResponse {

    @XmlElement(required = true)
    protected String sessionId;
    protected List<RispostaHotel> rispostaHotel;

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
     * Gets the value of the rispostaHotel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rispostaHotel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRispostaHotel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RispostaHotel }
     * 
     * 
     */
    public List<RispostaHotel> getRispostaHotel() {
        if (rispostaHotel == null) {
            rispostaHotel = new ArrayList<RispostaHotel>();
        }
        return this.rispostaHotel;
    }

}
