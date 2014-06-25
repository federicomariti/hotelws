
package com.hotel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 	una stanza e' caratterizzata dal numero di adulti e dal numero
 * 	di bambini che puo' ospitare. Altri parametri aggiuntivi
 * 	(utili per la prenotazione) possono essre l'eta dei bambini,
 * 	il tipo della stanza. Altri parametri aggiuntivi utili per la
 * 	risposta alla ricerca di una stanza sono la descrizione ed i
 * 	servizi extra presenti.
 *     
 * 
 * <p>Java class for stanza complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stanza">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numAdulti" type="{http://www.hotel.com/}positiveInteger"/>
 *         &lt;element name="numBambini" type="{http://www.hotel.com/}positiveInteger"/>
 *         &lt;element name="etaBambini" type="{http://www.hotel.com/}listaEta" minOccurs="0"/>
 *         &lt;element name="codiceTipoStanza" type="{http://www.hotel.com/}tipoStanza" minOccurs="0"/>
 *         &lt;element name="tipoStanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servizi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stanza", propOrder = {
    "numAdulti",
    "numBambini",
    "etaBambini",
    "codiceTipoStanza",
    "tipoStanza",
    "descrizione",
    "servizi"
})
public class Stanza {

    protected int numAdulti;
    protected int numBambini;
    @XmlList
    @XmlElement(type = Integer.class)
    protected List<Integer> etaBambini;
    protected Integer codiceTipoStanza;
    protected String tipoStanza;
    protected String descrizione;
    protected String servizi;
    
    public Stanza() {
    	;
    }
    
    public Stanza(int numAdulti, int numBambini) {
    	this.numAdulti = numAdulti;
    	this.numBambini = numBambini;
    }
    
    /* utile per la risposta */
    public Stanza(int numAdulti, int numBambini, Integer codiceTipoStanza, 
    		String tipoStanza, String descrizione) {
    	this(numAdulti, numBambini);
    	this.codiceTipoStanza = codiceTipoStanza;
    	this.tipoStanza = tipoStanza;
    	this.descrizione = descrizione;
    }
    
    /* utile per la richiesta */
    public Stanza(int numAdulti, int numBambini, String etaBambini, char listSepar, 
    		Integer codiceTipoStanza) {
    	this(numAdulti, numBambini);
    	if (null == etaBambini) {
    		this.etaBambini = null;
    	} else {
    		String[] _etaBambini = etaBambini.split(String.valueOf(listSepar));
    		this.etaBambini = new LinkedList<Integer>();
    		for (int i=0; i<_etaBambini.length; i++)
    			this.etaBambini.add(Integer.valueOf(_etaBambini[i]));
    	}
    	this.codiceTipoStanza = codiceTipoStanza;
    }
    
    public Stanza(int numAdulti, int numBambini, String etaBambini, char listSepar, 
    		Integer codiceTipoStanza, String tipoStanza, String descrizione, 
    		String servizi) {
    	this(numAdulti, numBambini, etaBambini, listSepar, codiceTipoStanza);
    	this.codiceTipoStanza = codiceTipoStanza;
    	this.descrizione = descrizione;
    	this.servizi = servizi;
    }
    
    
    public String toString() {
    	return "[numAdulti="+numAdulti+", numBambini="+numBambini+
    		", codiceTipoStanza="+codiceTipoStanza+", tipoStanza="+
    		tipoStanza+"]";
    }
    
    public String getEtaBambiniString() {
    	if (null == etaBambini) return "";
    	String result = "";
    	for (int i=0; i<etaBambini.size(); i++) {
    		String t = ", "; if (i == 0) t = "";
    		result = result + t + etaBambini.get(i);
    	}
    	return result;
    }

    /**
     * Gets the value of the numAdulti property.
     * 
     */
    public int getNumAdulti() {
        return numAdulti;
    }

    /**
     * Sets the value of the numAdulti property.
     * 
     */
    public void setNumAdulti(int value) {
        this.numAdulti = value;
    }

    /**
     * Gets the value of the numBambini property.
     * 
     */
    public int getNumBambini() {
        return numBambini;
    }

    /**
     * Sets the value of the numBambini property.
     * 
     */
    public void setNumBambini(int value) {
        this.numBambini = value;
    }

    /**
     * Gets the value of the etaBambini property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the etaBambini property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEtaBambini().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getEtaBambini() {
        if (etaBambini == null) {
            etaBambini = new ArrayList<Integer>();
        }
        return this.etaBambini;
    }

    /**
     * Gets the value of the codiceTipoStanza property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodiceTipoStanza() {
        return codiceTipoStanza;
    }

    /**
     * Sets the value of the codiceTipoStanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodiceTipoStanza(Integer value) {
        this.codiceTipoStanza = value;
    }

    /**
     * Gets the value of the tipoStanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoStanza() {
        return tipoStanza;
    }

    /**
     * Sets the value of the tipoStanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoStanza(String value) {
        this.tipoStanza = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the servizi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServizi() {
        return servizi;
    }

    /**
     * Sets the value of the servizi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServizi(String value) {
        this.servizi = value;
    }

}
