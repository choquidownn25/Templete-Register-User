package org.exemple.service;

import org.exemple.data.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.search.FlagTerm;


@Component
public class EmailServiceImpl implements  EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private static JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private  MailProperties mailProperties;

    public void sendEmail(Mail mail)
    {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public  void receiveEmails() throws MessagingException{

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        Message[] messages = folder.search(flagTerm);

        for (Message message : messages) {
            // Process the email message
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            System.out.println("Date: " + message.getReceivedDate());
            System.out.println("--------------------------------------------------");
        }

        folder.close(false);
        store.close();

    }
    public  void receiveEmailsOutlook() throws MessagingException, IOException {
        //Establish a connection to the Outlook server: Use the IMAP protocol to connect to the Outlook server.
        // Create a new Session object with the required properties for connecting to the server.
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.imaps.host", "imap-mail.outlook.com");
        props.setProperty("mail.imaps.port", "993");
        //Authenticate with your Outlook account:
        // Provide your Outlook email address and password to authenticate the session.
        Session session = Session.getInstance(props, null);
        String username = "choquidownn2255@outlook.com";
        String password = "1234hiphop";

        Store store = session.getStore("imaps");
        store.connect(username, password);
        //Open the inbox folder and retrieve messages: Open the inbox folder and fetch
        // the messages using the Folder class.
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        //Process the retrieved messages: You can iterate through the retrieved messages and extract
        // relevant information such as subject, sender, and content.

        for (Message message : messages) {
            String subject = message.getSubject();
            Address[] senders = message.getFrom();
            String content = message.getContent().toString();

            // Process the email data as needed
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            System.out.println("Date: " + message.getReceivedDate());
            System.out.println("--------------------------------------------------");
        }

        // Close the connection and folders
        inbox.close(false);
        store.close();

    }

    public void receiveEmailsJavaMailClass() throws MessagingException {
        Session session = javaMailSender.getSession();
        Store store = session.getStore("imaps");
        store.connect("imap-mail.outlook.com", "choquidownn2255@outlook.com", "1234hiphop");

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            // Process each email message here
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Date: " + message.getReceivedDate());
            System.out.println("--------------------------------------------------");
            // ...
        }

        inbox.close(false);
        store.close();
    }
}
/*
JavaMailSender
JavaMailSenderImpl
  nested exception is:
	javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)] with root cause

javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
 */