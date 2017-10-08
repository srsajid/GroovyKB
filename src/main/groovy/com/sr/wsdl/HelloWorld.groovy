package com.sr.wsdl

import javax.jws.WebMethod
import javax.jws.WebService
import javax.jws.soap.SOAPBinding

/**
 * Created by sajedur on 3/19/2015.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
interface HelloWorld {

    @WebMethod
    String getHelloWorldAsString(String name);
}