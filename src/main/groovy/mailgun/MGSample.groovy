package mailgun

import java.io.File;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class MGSample {

    // ...

    public static String sendSimpleMessage() throws UnirestException {

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/myprice.com.bd")
                .basicAuth("api", "key-bc0ca15f580dd2504abfb3bd85c0634c")
                .queryString("from", "Excited User <startech@myprice.com.bd>")
                .queryString("to", "sajed1990@gmail.com")
                .queryString("subject", "hello")
                .queryString("text", "testing")
                .asString();

        return request.getBody();
    }

    public static void main(String[] args) {
        println sendSimpleMessage()
    }
}
