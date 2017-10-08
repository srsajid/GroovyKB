package jdbc

import java.sql.DriverManager

/**
 * Created by sajedur on 9/9/2015.
 */
class Main {
    public static void main(String[] args) {
        Class.forName("com.mysql.jdbc.Driver");
        def con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                "root", "");
        def stmt = con.createStatement();
//        def r = stmt.executeQuery("select * from customer");
//        println(10)
        Map access = [code: "dfasdfadfasdf"]
        URI redirectURI = new URI("/com/strawberrynet/?");
        String redirectUrl = (redirectURI.scheme ? redirectURI.scheme + "://" : "") + (redirectURI.host ?: "") +  (redirectURI.path ?: "") + "?" + (redirectURI.query ? redirectURI.query + "&code=" : "code=" ) +  access.code
        println(redirectUrl)



    }
}
