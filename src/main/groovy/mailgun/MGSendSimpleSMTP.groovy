package mailgun

import javax.mail.*;
import javax.mail.internet.*;
import com.sun.mail.smtp.*;

public class MGSendSimpleSMTP {

    public static void main(String[] args) throws Exception {

        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.mailgun.org");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("startech@myprice.com.bd"));

        InternetAddress[] addrs = InternetAddress.parse("sajed1990@gmail.com", false);
        msg.setRecipients(Message.RecipientType.TO, addrs)

        msg.setSubject("Hello");
        msg.setText("Testing some Mailgun awesomness");
        msg.setSentDate(new Date());

        SMTPTransport t =
                (SMTPTransport) session.getTransport("smtps");
        t.connect("smtp.mailgun.com", "postmaster@myprice.com.bd", "bf19e7afffdf2cfb077aae75dc8711c2");
        t.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Response: " + t.getLastServerResponse());

        t.close();
    }
}
