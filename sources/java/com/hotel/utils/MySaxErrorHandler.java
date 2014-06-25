package com.hotel.utils;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MySaxErrorHandler implements ErrorHandler {

	private void gestioneGenerica(String pre, SAXParseException e) 
	throws SAXException {
		System.err.println(pre + ": line " + e.getLineNumber() + ": " + 
				e.getMessage());
		throw e;
	}
	public void warning(SAXParseException exception) throws SAXException {
		gestioneGenerica("warning", exception);
	}
	
	public void fatalError(SAXParseException exception) throws SAXException {
		gestioneGenerica("fatalError", exception);
	}
	
	public void error(SAXParseException exception) throws SAXException {
		gestioneGenerica("error", exception);
	}

}
