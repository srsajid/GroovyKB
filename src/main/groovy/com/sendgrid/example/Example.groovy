package com.sendgrid.example

import com.sendgrid.*
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email;

public class Example {
    public static void main(String[] args) throws IOException {
        Email from = new Email("info@eset.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("sajed1990@gmail.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}