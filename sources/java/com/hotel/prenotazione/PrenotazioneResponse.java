
package com.hotel.prenotazione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.hotel.RispostaHotel;
import com.hotel.StatoPrenotazione;


/**
 * 
 * 	La risposta ad una richiesta di prenotazione di una stanza
 * 	ritorna l'esito della prenotazione con lo stato della
 * 	stessa. Se piu' tipologie di camere soddisfano la richiesta di
 * 	prenotazione allora l'esito e' "non prenotata" e vengono
 * 	mostrate le possibili scelte di camere che soddisfano la
 * 	domanda di prenotazione. Se l'esisto e' positivo viene
 * 	ritornato l'identificatore della prenotazione.
 *     
 * 
 * <p>Java class for prenotazioneResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="prenotazioneResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esito" type="{http://www.hotel.com/}statoPrenotazione"/>
 *         &lt;element name="prenotazioneId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "prenotazioneResponse", propOrder = {
    "sessionId",
    "esito",
    "prenotazioneId",
    "rispostaHotel"
})
public class PrenotazioneResponse {

    @XmlElement(required = true)
    protected String sessionId;
    @XmlElement(required = true)
    protected StatoPrenotazione esito;
    protected String prenotazioneId;
    protected List<RispostaHotel> rispostaHotel;

    
    public String toString() {
    	String result = "[sessionId="+sessionId+", esito="+esito+", prenotazioneId"+
    		prenotazioneId+", rispostaHotel=[";
    	for (int i=0; i<rispostaHotel.size(); i++)
    		result += rispostaHotel.get(i);
    	return result + "]]";
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
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link StatoPrenotazione }
     *     
     */
    public StatoPrenotazione getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPrenotazione }
     *     
     */
    public void setEsito(StatoPrenotazione value) {
        this.esito = value;
    }

    /**
     * Gets the value of the prenotazioneId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenotazioneId() {
        return prenotazioneId;
    }

    /**
     * Sets the value of the prenotazioneId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenotazioneId(String value) {
        this.prenotazioneId = value;
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
