package http;

public class HttpUtil {

    public static String doGetRequest(String server) throws IOException {
        URL url = new URL(server);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setUseCaches(false);
        return getResponseText(conn);
    }

    public static URLConnection getPostConnection(String server, String data, Map requestProperty = [:]) throws IOException {
        URL url = new URL(server);
        URLConnection conn = url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        requestProperty.each {
            conn.setRequestProperty(it.key, it.value);
        }
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(data);
        writer.flush();
        writer.close();
        return conn
    }

    public static URLConnection getPostConnection(String server, Map data, Map requestProperty = [:]) throws IOException {
        return getPostConnection(server, serializeMap(data), requestProperty)
    }

    public static String doPostRequest(String server, String data, Map requestProperty = [:]) throws IOException {
        URLConnection connection = getPostConnection(server, data, requestProperty)
        return getResponseText(connection);
    }

    public static String doPostRequest(String server, Map data, Map requestProperty = [:]) throws IOException {
        doPostRequest(server, serializeMap(data), requestProperty)
    }

    public static String getResponseText(URLConnection conn) throws IOException {
        StringBuffer answer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            answer.append(line);
        }
        reader.close();
        return answer.toString();
    }

    public static String serializeMap(Map map) {
        if(map.size() == 0) {
            return 0;
        }
        StringBuilder builder = new StringBuilder()
        map.each {key, value ->
            if(value instanceof List) {
                value.each {
                    builder.append("&" + key + "=")
                    if(it) {
                        builder.append(URLEncoder.encode(it.toString(), "UTF8"))
                    }
                }
            } else {
                builder.append("&" + key + "=")
                if(value) {
                    builder.append(URLEncoder.encode(value.toString(), "UTF8"))
                }
            }
        }
        return builder.toString().substring(1)
    }

    static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}