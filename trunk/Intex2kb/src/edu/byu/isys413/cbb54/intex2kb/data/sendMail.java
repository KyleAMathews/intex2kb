/*
Some SMTP servers require a username and password authentication before you
can use their Server for Sending mail. This is most common with couple
of ISP's who provide SMTP Address to Send Mail.
 
This Program gives any example on how to do SMTP Authentication
(User and Password verification)
 
This is a free source code and is provided as it is without any warranties and
it can be used in any your code for free.
 
Author : Sudhir Ancha
 */

package edu.byu.isys413.cbb54.intex2kb.data;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import javax.mail.Session;

/*
  To use this program, change values for the following three constants,
 
    SMTP_HOST_NAME -- Has your SMTP Host Name
    SMTP_AUTH_USER -- Has your SMTP Authentication UserName
    SMTP_AUTH_PWD  -- Has your SMTP Authentication Password
 
  Next change values for fields
 
  emailMsgTxt  -- Message Text for the Email
  emailSubjectTxt  -- Subject for email
  emailFromAddress -- Email Address whose name will appears as "from" address
 
  Next change value for "emailList".
  This String array has List of all Email Addresses to Email Email needs to be sent to.
 
 
  Next to run the program, execute it as follows,
 
  sendMail authProg = new sendMail();
 
 */

public class sendMail {
    
    private static final String SMTP_HOST_NAME = "smtp.1and1.com";
    private static final String SMTP_AUTH_USER = "info@2kmystuff.com";
    private static final String SMTP_AUTH_PWD  = "intex24";
    
    public void postMail( String recipients[ ],
            String message) throws MessagingException {
        boolean debug = false;
        
        //Set generic message properties
        String from = "info@2kmystuff.com";
        String subject = "MyStuff Digital Photography";
        
        //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        
        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);
        
        session.setDebug(debug);
        
        // create a message
        Message msg = new MimeMessage(session);
        
        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);
        
        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        
        
        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
        System.out.println("sent");
    }
    
    
    /**
     * SimpleAuthenticator is used to do simple authentication
     * when the SMTP server requires it.
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }
    
}


