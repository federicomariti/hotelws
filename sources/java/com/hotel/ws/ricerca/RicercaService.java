package com.hotel.ws.ricerca;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-12-30T18:03:19.132+01:00
 * Generated source version: 2.4.2
 * 
 */
@WebServiceClient(name = "ricercaService", 
                  wsdlLocation = "ricerca.wsdl",
                  targetNamespace = "http://www.hotel.com/ws/ricerca/") 
public class RicercaService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.hotel.com/ws/ricerca/", "ricercaService");
    public final static QName Ricerca = new QName("http://www.hotel.com/ws/ricerca/", "ricerca");
    static {
        URL url = null;
        try {
            url = new URL("ricerca.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RicercaService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "ricerca.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RicercaService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public RicercaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RicercaService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RicercaService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RicercaService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public RicercaService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns Ricerca
     */
    @WebEndpoint(name = "ricerca")
    public Ricerca getRicerca() {
        return super.getPort(Ricerca, Ricerca.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Ricerca
     */
    @WebEndpoint(name = "ricerca")
    public Ricerca getRicerca(WebServiceFeature... features) {
        return super.getPort(Ricerca, Ricerca.class, features);
    }

}