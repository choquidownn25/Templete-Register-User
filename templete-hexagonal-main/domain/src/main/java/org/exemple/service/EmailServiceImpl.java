package org.exemple.service;

import org.exemple.data.Mail;
import org.exemple.data.response.BancoOrigenDTOResponse;
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
    public List<BancoOrigenDTOResponse> receiveEmailsHTMLBanco() throws MessagingException, IOException{
        List<BancoOrigenDTOResponse> infoEmail = new ArrayList<>();
        BancoOrigenDTOResponse emailInfo = new BancoOrigenDTOResponse();


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
            System.out.println("Email : " + message.getFrom());
            emailInfo.setFrom(address);
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Contenido: " + body);
            //emailInfo.setContend(body);

            String strDNI = body;

            // Patrones de expresiones regulares para buscar el nombre, DNI y número de teléfono
            String bancoOrigen = extractBancoOrigen(body);
            String montoRecibido = extractMontoRecibido(body);
            String nombreCliente = extractNombreCliente(body);
            String numeroComprobante = extractNumeroComprobante(body);


            System.out.println("Banco de origen: " + bancoOrigen);
            System.out.println("Monto recibido: " + montoRecibido);
            System.out.println("Nombre Cliente: " + nombreCliente);
            System.out.println("Número de comprobante: " + numeroComprobante);

            emailInfo.setBancoOrigen(bancoOrigen);
            emailInfo.setMontoRecibido(montoRecibido);
            emailInfo.setNombreCliente(nombreCliente);
            emailInfo.setNumeroComprobante(numeroComprobante);
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
    private static String extractBancoOrigen(String texto) {
        Pattern pattern = Pattern.compile("Banco de origen\\r\\n(.+?)\\r\\n");
        Matcher matcher = pattern.matcher(texto);
        Pattern patternBancoDestino = Pattern.compile("Banco de destino\\r\\n(.+?)\\r\\n");
        Matcher matcherBancoDestino = patternBancoDestino.matcher(texto);

        Pattern patternBancoDestinoUpper = Pattern.compile("Banco Destino:\\\\s*([\\\\w\\\\s-]+)");
        Matcher matcherBancoDestinoUpper = patternBancoDestinoUpper.matcher(texto);

        Pattern patternBanco = Pattern.compile("Banco\\s*:\\s*([^\\n]+)");
        Matcher matcherBanco = patternBanco.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }
        if(matcherBancoDestino.find()){
            return matcherBancoDestino.group(1);
        }
        if(matcherBancoDestinoUpper.find()){
            return matcherBancoDestinoUpper.group(1);
        }
        if(matcherBanco.find()){
            return matcherBanco.group(1);
        }
        return "";
    }

    private static String extractMontoRecibido(String texto) {
        Pattern pattern = Pattern.compile("Monto recibido\\r\\n(.+?)\\r\\n");
        Matcher matcher = pattern.matcher(texto);

        Pattern patternMontoOperacion = Pattern.compile("Monto de la Operacion\\r\\n(.+?)\\r\\n");
        Matcher matcherMontoOperacion = patternMontoOperacion.matcher(texto);
        Pattern patternMonto = Pattern.compile("Monto\\r\\n(.+?)\\r\\n");
        Matcher matcherMonto = patternMonto.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }
        if (matcherMontoOperacion.find()) {
            return matcher.group(1);
        }
        if (matcherMonto.find()) {
            return matcherMonto.group(1);
        }
        return "";
    }
    private static String extractNombreCliente(String texto) {
        Pattern pattern = Pattern.compile("Has recibido una transferencia de fondos de \\r\\n(.+?)\\r\\n");
        Matcher matcher = pattern.matcher(texto);

        Pattern patternNombreCliente = Pattern.compile(", nuestro cliente \\r\\n(.+?)\\r\\n");
        Matcher matcherNombreCliente = patternNombreCliente.matcher(texto);

        Pattern patternNombre = Pattern.compile("Nombre: (.+)");
        Matcher matcherNombre = patternNombre.matcher(texto);

        Pattern patternNombreCuenta = Pattern.compile("Nombre Cuenta: (.+)");
        Matcher matcherNombreCuenta = patternNombreCuenta.matcher(texto);
        Pattern patternNombreClienteCuenta = Pattern.compile("Nombre Cuenta: (.+)");
        Matcher matcherNombreClienteCuenta = patternNombreClienteCuenta.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }
        if(matcherNombreCliente.find()){
            return matcherNombreCliente.group(1);
        }
        if(matcherNombre.find()){
            return matcherNombre.group(1);
        }
        if(matcherNombreCuenta.find()){
            return matcherNombreCuenta.group(1);
        }
        if(matcherNombreClienteCuenta.find()){
            return matcherNombreClienteCuenta.group(1);
        }
        return "";
    }
    private static String extractNumeroComprobante(String texto) {
        Pattern pattern = Pattern.compile("Número de comprobante\\r\\n(.+?)\\r\\n");
        Matcher matcher = pattern.matcher(texto);
        Pattern patternNumeroCuenta = Pattern.compile("Numero Cuenta\\r\\n(.+?)\\r\\n");
        Matcher matcherNumeroCuenta = patternNumeroCuenta.matcher(texto);
        Pattern patternNumeroCuentaBancaria = Pattern.compile("Numero Cuenta\\r\\n(.+?)\\r\\n");
        Matcher matcherNumeroCuentaBancaria = patternNumeroCuentaBancaria.matcher(texto);
        if (matcher.find()) {
            return matcher.group(1);
        }
        if (matcherNumeroCuenta.find()) {
            return matcherNumeroCuenta.group(1);
        }
        if (matcherNumeroCuentaBancaria.find()) {
            return matcherNumeroCuentaBancaria.group(1);
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