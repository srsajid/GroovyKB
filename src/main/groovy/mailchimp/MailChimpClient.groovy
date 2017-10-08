package mailchimp

import groovy.json.JsonOutput
import http.HttpUtil
import mailchimp.anotation.Field
import mailchimp.anotation.Method

import java.lang.reflect.Modifier

class MailChimpClient {

    private String buildUrl(MailChimpMethod<?> method) {
        String apikey = method.apikey;
        if(apikey == null) throw new IllegalArgumentException("apikey is not set");

        String prefix;
        int dash = apikey.lastIndexOf('-');
        if(dash > 0) {
            prefix = apikey.substring(dash + 1);
        } else {
            throw new IllegalArgumentException("Wrong apikey: "+apikey);
        }

        Method metaInfo = method.getMetaInfo();

        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(prefix);
        sb.append(".api.mailchimp.com/");
        sb.append(metaInfo.version()).append("/");
        sb.append(metaInfo.name());
        sb.append(".json");
        return sb.toString();
    }

    String getPayLoad(Payload paayload){
        Class clazz = paayload.class;
        Map mapping = new LinkedHashMap<String, java.lang.reflect.Field>();
        LinkedList<Class> classes = new LinkedList<Class>();
        for(Class<?> c= clazz; Payload.class.isAssignableFrom(c); c = c.getSuperclass()) {
            classes.addFirst(c);
        }

        for(Class<?> c: classes) {
            for(java.lang.reflect.Field f: c.getDeclaredFields()) {
                Field annotation = f.getAnnotation(Field.class);
                if(annotation != null) {
                    if(Modifier.isStatic(f.getModifiers())) {
                        throw new IllegalArgumentException("Field must not be static: "+f);
                    }

                    String name = annotation.name();
                    if(name.isEmpty()) {
                        name = f.getName();
                    }
                    Object value = f.get(paayload);
                    if(value != null) {
                        if(value instanceof Payload) {
                            value = getPayLoad(value)
                        }
                        mapping[name] = value
                    }
                    f.setAccessible(true);
                }
            }
        }

        return JsonOutput.toJson(mapping)
    }

    public def execute(MailChimpMethod<?> method) {
        String url = buildUrl(method)
        String payload = getPayLoad(method)
        execute(url, payload)
    }

    public def execute(String url, String payload) {
        String response = HttpUtil.doPostRequest(url, payload)
        return response
    }

}
