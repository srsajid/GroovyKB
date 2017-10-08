package com.sr.wsdl

import javax.jws.WebService

/**
 * Created by sajedur on 3/19/2015.
 */

@WebService(endpointInterface = "com.sr.wsdl.HelloWorld")
class HelloWorldImpl implements HelloWorld {
    public String getHelloWorldAsString(String name) {
        return "Ami Banglai Gaan Gai " + name;
    }


}
