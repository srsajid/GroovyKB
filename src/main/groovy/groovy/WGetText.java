package groovy;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class WGetText {
    public static void main(String[] args) throws Exception {
        String o[][] = new String[10][5];
        String urlString = System.getProperty("url", "https://google.com");
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) urlConnection;
        SSLSocketFactory sslSocketFactory = createSslSocketFactory();
        httpsUrlConnection.setSSLSocketFactory(sslSocketFactory);
        try {
            InputStream inputStream = httpsUrlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {}
    }

    private static SSLSocketFactory createSslSocketFactory() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
                System.out.println("dfdsfd");
            }
        } };
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext.getSocketFactory();
    }
}