package com.sr.wsdl

import javax.xml.ws.Endpoint

public class Main {

    public static void main(String[] args) throws SocketException {
        Endpoint.publish("http://localhost:9999/ws/hello", new HelloWorldImpl());
    }

}
