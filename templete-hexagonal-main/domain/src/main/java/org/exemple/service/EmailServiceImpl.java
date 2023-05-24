package org.exemple.service;

import org.exemple.data.Mail;
import org.exemple.data.response.EmailDTOResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
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

    public  void receiveEmails() throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        FlagTerm flagTerms = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        Message[] mesaje = folder.search(flagTerms);
        for (Message mesajes : mesaje) {
            // Process the email message
            String bodys = ((MimeMultipart) mesajes.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Subject: " + mesajes.getSubject());
            System.out.println("From: " + InternetAddress.toString(mesajes.getFrom()));
            System.out.println("Contenido: " + bodys);
            System.out.println("Date: " + mesajes.getReceivedDate());
            System.out.println("--------------------------------------------------");
        }
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
        Message[] messages = folder.search(flagTerm);

        for (Message message : messages) {
            // Process the email message
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            System.out.println("Contenido: " + body);
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
            System.out.println("Contenido: " + content);
            System.out.println("Date: " + message.getReceivedDate());
            System.out.println("--------------------------------------------------");
        }

        // Close the connection and folders
        inbox.close(false);
        store.close();

    }

    public List<Message> listReceiveEmails() throws MessagingException, IOException {
        List<Message> listReceiveEmails = new ArrayList<>();
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        FlagTerm flagTerms = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        Message[] mesaje = folder.search(flagTerms);
        for (Message mesajes : mesaje) {
            // Process the email message
            String bodys = ((MimeMultipart) mesajes.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Subject: " + mesajes.getSubject());
            System.out.println("From: " + InternetAddress.toString(mesajes.getFrom()));
            System.out.println("Contenido: " + bodys);
            System.out.println("Date: " + mesajes.getReceivedDate());
            listReceiveEmails.add(mesajes);
            System.out.println("--------------------------------------------------");
        }
        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
        Message[] messages = folder.search(flagTerm);

        for (Message message : messages) {
            // Process the email message
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            System.out.println("Contenido: " + body);
            System.out.println("Date: " + message.getReceivedDate());
            listReceiveEmails.add(message);
            System.out.println("--------------------------------------------------");
        }

        folder.close(false);
        store.close();
        return listReceiveEmails;
    }
    public List<EmailDTOResponse> receiveEmailsIMCP() throws MessagingException, IOException{
        List<EmailDTOResponse> infoEmail = new ArrayList<>();
        EmailDTOResponse emailInfo = new EmailDTOResponse();
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
        Message[] messages = folder.search(flagTerm);

        for (Message message : messages) {
            // Process the email message
            System.out.println("Subject: " + message.getSubject());
            emailInfo.setSubject(message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            Address[] address =message.getFrom();
            emailInfo.setFrom(address);
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Contenido: " + body);
            emailInfo.setContend(body);

            String strDNI = body;
            String[] arrOfStrDNI = strDNI.split("Nombre:\\r\\nDNI:\\r\\nEmpresa:E-sing\\r\\nCelular:\\r\\n,+", 76);
            System.out.println("Muestra Nombre a mostrar :" + arrOfStrDNI[0].substring(8,28).toString());
            emailInfo.setCompany(arrOfStrDNI[0].substring(8,27).toString());
            emailInfo.setDni(arrOfStrDNI[0].substring(34,41).toString());
            System.out.println("Date: " + message.getReceivedDate());
            emailInfo.setReceivedDate(message.getReceivedDate());
            System.out.println("--------------------------------------------------");
            infoEmail.add(emailInfo);
        }

        folder.close(false);
        store.close();
        return infoEmail;
    }

    public List<EmailDTOResponse> receiveEmailsHTML() throws MessagingException, IOException{
        List<EmailDTOResponse> infoEmail = new ArrayList<>();
        EmailDTOResponse emailInfo = new EmailDTOResponse();


        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
        Message[] messages = folder.search(flagTerm);

        for (Message message : messages) {
            // Process the email message
            System.out.println("Subject: " + message.getSubject());
            emailInfo.setSubject(message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            Address[] address =message.getFrom();
            emailInfo.setFrom(address);
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Contenido: " + body);
            emailInfo.setContend(body);

            String strDNI = body;


            // Patrones de expresiones regulares para buscar el nombre, DNI y número de teléfono
            String patronNombre = "Nombre: (.+)";
            String patronDNI = "DNI: (.+)";
            String patronTelefono = "Celular: (.+)";

            // Crear los objetos Pattern y Matcher
            Pattern patternNombre = Pattern.compile(patronNombre);
            Pattern patternDNI = Pattern.compile(patronDNI);
            Pattern patternTelefono = Pattern.compile(patronTelefono);

            Matcher matcherNombre = patternNombre.matcher(body);
            Matcher matcherDNI = patternDNI.matcher(body);
            Matcher matcherTelefono = patternTelefono.matcher(body);

            // Buscar la información del usuario
            String nombre = obtenerInformacion(matcherNombre);
            String dni = obtenerInformacion(matcherDNI);
            String telefono = obtenerInformacion(matcherTelefono);

            // Imprimir la información encontrada
            System.out.println("Nombre: " + nombre);
            System.out.println("DNI: " + dni);
            System.out.println("Teléfono: " + telefono);

            emailInfo.setCompany(nombre);
            emailInfo.setDni(dni);
            emailInfo.setPhone(telefono);
            System.out.println("Date: " + message.getReceivedDate());
            emailInfo.setReceivedDate(message.getReceivedDate());
            System.out.println("--------------------------------------------------");
            infoEmail.add(emailInfo);
        }

        folder.close(false);
        store.close();
        return infoEmail;
    }
    // Método auxiliar para obtener la información coincidente del Matcher
    private static String obtenerInformacion(Matcher matcher) {
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
}


/*
JavaMailSender
JavaMailSenderImpl
  nested exception is:
	javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)] with root cause

javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
 */