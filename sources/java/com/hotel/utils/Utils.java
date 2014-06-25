package com.hotel.utils;

import javax.xml.datatype.XMLGregorianCalendar;

public class Utils {
	public static long calcolaNumeroGiorni(XMLGregorianCalendar begin, XMLGregorianCalendar end) {
		return calcolaNumeroGiorni(begin.toGregorianCalendar().getTimeInMillis(),
				end.toGregorianCalendar().getTimeInMillis());
	}
	public static long calcolaNumeroGiorni(long begin, long end) {
		long result = Math.round( (end - begin) / 86400000.0 );
		
		return result;
	}
}
