package startrack;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



/**
 * Created by pritom on 26/05/2015.
 */
public class JavaUrlConnector {
    public static final String CONTENT_TYPE_XML = "text/xml; charset=utf-8";
    public static final String CONTENT_TYPE_JSON = "application/json";


    public static Map callUrl(String requestURL, String method, String requestData, String contentType, Map headers) throws Exception {
        HttpsURLConnection connection = null;
        String httpResponse = "", responseMessage = "";
        Integer httpCode = 0;
        try {
            String contentLength = "" + requestData.length(), accept = null;
            if (contentType.length() > 0 && contentType.equals(CONTENT_TYPE_XML)) {
                accept = "text/xml";
            }
            System.setProperty("com.sun.net.ssl.enableECC","false");
            System.setProperty("jsse.enableSNIExtension","false");
            System.setProperty("https.protocols", "TLSv1,SSLv3,SSLv2Hello");

            connection = (HttpsURLConnection) new URL(requestURL).openConnection();
            setAcceptAllVerifier(connection);

            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            if (method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put")) {
                connection.setDoInput(true);
                addHeaderToRequest(connection, headers);
                if (accept != null && accept.length() > 0) {
                    connection.setRequestProperty("Accept", accept);
                }
                if (contentType != null && contentType.length() > 0) {
                    connection.setRequestProperty("Content-Type", contentType);
                }
                connection.setRequestProperty("Pragma", "no-cache");
                if (requestData.length() > 0) {
                    connection.setRequestProperty("Content_length", contentLength);
                    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                    writer.write(requestData);
                    writer.flush();
                    writer.close();
                }
            }
            else {
                connection.setRequestProperty("Pragma", "no-cache");
                addHeaderToRequest(connection, headers);
            }
            if(connection != null) {
                httpCode = connection.getResponseCode();
                responseMessage = connection.getResponseMessage();
                InputStream is = null;

                if(httpCode == 200 && responseMessage.equalsIgnoreCase("OK")) {
                    is = (InputStream) connection.getInputStream();
                } else {
                    is = (InputStream) connection.getErrorStream();
                }

                Writer writer5 = new StringWriter();

                char[] buffer = new char[1024];
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    int n;
                    while ((n = reader.read(buffer)) != -1) {
                        writer5.write(buffer, 0, n);
                    }
                    httpResponse = writer5.toString();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    responseMessage = ex.toString();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            responseMessage = ex.toString();
        }
        finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            catch (Exception ex10) {

            }
        }
        Map result = new HashMap();
        result.put("code", httpCode);
        result.put("response", httpResponse);
        result.put("message", responseMessage);
        return result;
    }

    protected static void setAcceptAllVerifier(HttpsURLConnection connection) throws NoSuchAlgorithmException, KeyManagementException {
        SSLSocketFactory sslSocketFactory = null;
        if( null == sslSocketFactory) {
            SSLContext sc = SSLContext.getInstance("SSLv3");
            sc.init(null, ALL_TRUSTING_TRUST_MANAGER(), new java.security.SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
        }
        connection.setSSLSocketFactory(sslSocketFactory);
        connection.setHostnameVerifier(ALL_TRUSTING_HOSTNAME_VERIFIER());
    }

    private static TrustManager[] ALL_TRUSTING_TRUST_MANAGER() {
        return new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };
    }

    private static HostnameVerifier ALL_TRUSTING_HOSTNAME_VERIFIER() {
      return  new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                System.out.println("Hello ");
                return true;
            }
        };
    }

    private static void addHeaderToRequest(HttpsURLConnection connection, Map headers) {
        Iterator iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            connection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
            iterator.remove();
        }
    }
}
