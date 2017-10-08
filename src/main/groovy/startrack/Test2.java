package startrack;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pritom on 26/05/2015.
 */
public class Test2 {
    public static void main(String[] args) {
        try {
            String responseString = "";
            String outputString = "";
            String wsURL = "https://services.startrackexpress.com.au:7560/DMZExternalService/InterfaceServices/ExternalOps.serviceagent/OperationsEndpoint1";
            String SOAPAction = "/DMZExternalService/InterfaceServices/ExternalOps.serviceagent/OperationsEndpoint1/calculateCost";
//            String SOAPAction = "/DMZExternalService/InterfaceServices/ExternalOps.serviceagent/OperationsEndpoint1/getServiceCodes";
//            String SOAPAction = "/DMZExternalService/InterfaceServices/ExternalOps.serviceagent/OperationsEndpoint1/calculateCostAndEstimatedTime";
//            String xmlInput =
//                    " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n" +
//                            " <soapenv:Header/>\n" +
//                            " <soapenv:Body>\n" +
//                            " <web:GetWeather>\n" +
//                            " <!--Optional:-->\n" +
//                            " <web:City>Dhaka</web:City>\n" +
//                            " </web:GetWeather>\n" +
//                            " </soapenv:Body>\n" +
//                            " </soapenv:Envelope>";

            String SOAP_HEADER = "<soapenv:Header>\n<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
                    "   <wsse:UsernameToken wsu:Id=\"UsernameToken-1\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                    "       <wsse:Username>TAY00002</wsse:Username>\n" +
                    "       <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Tay12345</wsse:Password>\n" +
                    "       <wsse:Nonce>EGlSVmA=</wsse:Nonce>\n" +
                    "       <wsu:Created xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">2015-05-26T04:55:35Z</wsu:Created>\n" +
                    "   </wsse:UsernameToken>\n" +
                    "</wsse:Security>\n</soapenv:Header>";

            String SOAP_BODY = "<soapenv:Body>\n" +
                    "      <v1:getConsignmentDetailRequest>\n" +
                    "         <v1:header>\n" +
                    "            <v11:source>TEAM</v11:source>\n" +
                    "            <v11:accountNo>11112222</v11:accountNo>\n" +
                    "            <v11:userAccessKey>30405060708090</v11:userAccessKey>\n" +
                    "         </v1:header>\n" +
                    "         <v1:consignmentId>TAY00180</v1:consignmentId>\n" +
                    "         <v1:consignmentId>TAY00181</v1:consignmentId>\n" +
                    "      </v1:getConsignmentDetailRequest>\n" +
                    "   </soapenv:Body>";


            SOAP_BODY = "<soapenv:Body>\n" +
                    "    <v1:calculateCostRequest>\n" +
                    "        <v1:header>\n" +
                    "            <v11:source>TEAM</v11:source>\n" +
                    "            <v11:accountNo>11112222</v11:accountNo>\n" +
                    "            <v11:userAccessKey>30405060708090</v11:userAccessKey>\n" +
                    "        </v1:header>\n" +
                    "        <v1:senderLocation>\n" +
                    "            <v11:suburb>MOUNT ISA</v11:suburb>\n" +
                    "            <v11:postCode>4825</v11:postCode>\n" +
                    "            <v11:state>QLD</v11:state>\n" +
                    "        </v1:senderLocation>\n" +
                    "        <v1:receiverLocation>\n" +
                    "            <v11:suburb>broome</v11:suburb>\n" +
                    "            <v11:postCode>6725</v11:postCode>\n" +
                    "            <v11:state>WA</v11:state>\n" +
                    "        </v1:receiverLocation>\n" +
                    "        <v1:serviceCode>EXP</v1:serviceCode>\n" +
                    "        <v1:noOfItems>1</v1:noOfItems>\n" +
                    "        <v1:weight>10</v1:weight>\n" +
                    "        <v1:volume>0.01</v1:volume>\n" +
                    "        <v1:includeTransitWarranty>true</v1:includeTransitWarranty>\n" +
                    "        <v1:transitWarrantyValue>1000</v1:transitWarrantyValue>\n" +
                    "        <v1:includeFuelSurcharge>true</v1:includeFuelSurcharge>\n" +
                    "        <v1:includeSecuritySurcharge>true</v1:includeSecuritySurcharge>\n" +
                    "    </v1:calculateCostRequest>\n" +
                    "</soapenv:Body>";


            String SOAP_MESSAGE = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://startrackexpress/Common/actions/externals/FreightCalculation/v1\" xmlns:v11=\"http://startrackexpress/Common/Primitives/v1\">" +
                    "\n" + SOAP_HEADER + "\n" + SOAP_BODY + "\n</soapenv:Envelope>";


            /*SOAP_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns1=\"http://startrackexpress/Common/Primitives/v1\" xmlns:ns2=\"http://startrackexpress/Common/actions/externals/FreightCalculation/v1\" xmlns:ns3=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><SOAP-ENV:Header>\n" +
                    "<wsse:Security SOAP-ENV:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
                    "<wsse:UsernameToken wsu:Id=\"UsernameToken-1\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                    "    <wsse:Username>TAY00002</wsse:Username>\n" +
                    "    <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">Tay12345</wsse:Password>\n" +
                    "    <wsse:Nonce>Ekhwdwg=</wsse:Nonce>\n" +
                    "    <wsu:Created xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">2015-05-26T08:50:29Z</wsu:Created>\n" +
                    "   </wsse:UsernameToken>\n" +
                    "</wsse:Security>\n" +
                    "</SOAP-ENV:Header><SOAP-ENV:Body><ns2:calculateCostAndEstimatedTimeRequest><ns2:header><ns1:source>TEAM</ns1:source><ns1:accountNo>11111111</ns1:accountNo><ns1:userAccessKey>30405060708090</ns1:userAccessKey></ns2:header><ns2:senderLocation><ns1:suburb>MOUNT ISA</ns1:suburb><ns1:postCode>4825</ns1:postCode><ns1:state>QLD</ns1:state></ns2:senderLocation><ns2:receiverLocation><ns1:suburb>BROOME</ns1:suburb><ns1:postCode>6725</ns1:postCode><ns1:state>WA</ns1:state></ns2:receiverLocation><ns2:serviceCode>EXP</ns2:serviceCode><ns2:noOfItems>1</ns2:noOfItems><ns2:weight>25</ns2:weight><ns2:volume>.01</ns2:volume><ns2:includeTransitWarranty>true</ns2:includeTransitWarranty><ns2:transitWarrantyValue>0</ns2:transitWarrantyValue><ns2:includeFuelSurcharge>false</ns2:includeFuelSurcharge><ns2:choice><ns2:despatchDate>2011-06-25</ns2:despatchDate></ns2:choice></ns2:calculateCostAndEstimatedTimeRequest></SOAP-ENV:Body></SOAP-ENV:Envelope>";*/

            System.out.println(SOAP_MESSAGE);

            Map headers = new HashMap();
            headers.put("SOAPAction", SOAPAction);
            headers.put("Accept-Encoding", "gzip,deflate");
            //headers.put("User-Agent", "Jakarta Commons-HttpClient/3.1");
            headers.put("Host", "services.startrackexpress.com.au:7560");
            Map result = JavaUrlConnector.callUrl(wsURL, "POST", SOAP_MESSAGE, JavaUrlConnector.CONTENT_TYPE_XML, headers);
            System.out.println(result);
            result = JavaUrlConnector.callUrl(wsURL, "POST", SOAP_MESSAGE, JavaUrlConnector.CONTENT_TYPE_XML, headers);
            System.out.println(result);

            /*URL url = new URL(wsURL);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();


            byte[] buffer = xmlInput.getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();
            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            while ((responseString = in.readLine()) != null) {
                outputString = outputString + responseString;
            }
            System.out.println(outputString);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
