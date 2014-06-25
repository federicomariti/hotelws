
package com.hotel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for statoPrenotazione.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="statoPrenotazione">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ER"/>
 *     &lt;enumeration value="CF"/>
 *     &lt;enumeration value="PD"/>
 *     &lt;enumeration value="NL"/>
 *     &lt;enumeration value="CN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "statoPrenotazione")
@XmlEnum
public enum StatoPrenotazione {


    /**
     * errore
     * 
     */
    ER,

    /**
     * confermata
     * 
     */
    CF,

    /**
     * pendente
     * 
     */
    PD,

    /**
     * nullo, prenotazione ambigua, o stanza non disponibile
     * 
     */
    NL,

    /**
     * cancellata
     * 
     */
    CN;

    public String value() {
        return name();
    }

    public static StatoPrenotazione fromValue(String v) {
        return valueOf(v);
    }

}
