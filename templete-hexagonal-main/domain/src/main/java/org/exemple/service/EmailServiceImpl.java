package org.exemple.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exemple.data.BancoOrigenDTO;
import org.exemple.data.Mail;
import org.exemple.data.response.BancoOrigenDTOResponse;
import org.exemple.utils.Extact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;


@Component
public class EmailServiceImpl implements  EmailService{
    List<BancoOrigenDTO> infoEmail = new ArrayList<>();
    BancoOrigenDTO emailInfo = new BancoOrigenDTO();
    Extact extact = new Extact();
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private static JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    private  MailProperties mailProperties;
    private ObjectMapper mapper = new ObjectMapper();
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
    public List<BancoOrigenDTO> receiveEmailsHTMLBanco(String nameBanco) throws MessagingException, IOException{
        List<String> infoEmails = new ArrayList<>();
        BancoOrigenDTO emailInf = new BancoOrigenDTO();
        //Declaring reference of File class
        File file = null;
        //Declaring reference of FileOutputStream class
        FileOutputStream fileOutStream = null;
        String bancoOrigen = null;
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);

        FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), false);
        Message[] messages = folder.search(flagTerm);
        file = new File("C:/E-sing/FileRestEmail.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter escritura = new BufferedWriter(fileWriter);
        //Creating Object of FileOutputStream class
        fileOutStream = new FileOutputStream(file);
        for (Message message : messages) {
            //In case file does not exists, Create the file
            if (!file.exists()) {
                file.createNewFile();
            }
            // Process the email message
            System.out.println("Subject: " + message.getSubject());
            emailInfo.setSubject(message.getSubject());
            System.out.println("From: " + InternetAddress.toString(message.getFrom()));
            Address[] address =message.getFrom();
            String sender = message.getFrom()[0].toString();
            System.out.println("Contenido: " + sender);
            emailInfo.setFrom(address);
            String body = ((MimeMultipart) message.getContent()).getBodyPart(0).getContent().toString();
            System.out.println("Contenido: " + body);


            //emailInfo.setContend(body);

            String strDNI = body;

            // Patrones de expresiones regulares para buscar el nombre, DNI y número de teléfono
            bancoOrigen = extact.extractBancoOrigen(body);
            String montoRecibido = extact.extractMontoRecibido(body);
            String nombreCliente = extact.extractNombreCliente(body);
            String numeroComprobante = extact.extractNumeroComprobante(body);


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
            //fetching the bytes from data String
            infoEmails.add(String.valueOf(emailInfo));
            infoEmail.add(emailInfo);
            for(int i=0;i<infoEmail.size();i++){
                escritura.write(String.valueOf(infoEmail.get(i)));
                escritura.newLine();

            }

        }

        folder.close(false);
        store.close();
        escritura.close();

        // Agrega los elementos de la lista de cadenas

        List<BancoOrigenDTO> bancoOrigenDTOList = infoEmails.stream()
                .map(str -> {
                    String[] data = str.split(","); // Suponiendo que los elementos de la lista de cadenas están separados por comas
                    BancoOrigenDTO bancoOrigenDTO = new BancoOrigenDTO();
                    bancoOrigenDTO.setSubject(data[0]);
                    bancoOrigenDTO.setFrom(emailInfo.getFrom());
                    bancoOrigenDTO.setNombreCliente(data[2]);
                    bancoOrigenDTO.setBancoOrigen(data[3]);
                    bancoOrigenDTO.setMontoRecibido(data[4]);
                    bancoOrigenDTO.setNumeroComprobante(data[5]);
                    // bancoOrigenDTO.setContend(data[6]); // Si deseas descomentar esta línea, asegúrate de tener el setter correspondiente en la clase BancoOrigenDTO
                    bancoOrigenDTO.setReceivedDate(emailInfo.getReceivedDate());
                    return bancoOrigenDTO;
                })
                .collect(Collectors.toList());
        //List<BancoOrigenDTO> bancoOrigenDTOList = new ArrayList<>();

        //return bancoOrigenDTOList.stream().filter(x->x.getBancoOrigen()==nameBanco).collect(Collectors.toList());
        return bancoOrigenDTOList;
    }




    private static BancoOrigenDTO convertirStringABancoOrigenDTO(String str) {
        String[] partes = str.split(","); // Asume que los campos están separados por comas

        String subject = partes[0];
        String[] from = partes[1].split(";"); // Asume que la dirección tiene componentes separados por punto y coma
        String nombreCliente = partes[2];
        String bancoOrigen = partes[3];
        String montoRecibido = partes[4];
        String numeroComprobante = partes[5];
        Date receivedDate = obtenerFecha(partes[6]); // Implementa obtenerFecha() para convertir el string a una instancia de Date

        BancoOrigenDTO dto = new BancoOrigenDTO();
        dto.setSubject(subject);
        //dto.setFrom(from);
        dto.setNombreCliente(nombreCliente);
        dto.setBancoOrigen(bancoOrigen);
        dto.setMontoRecibido(montoRecibido);
        dto.setNumeroComprobante(numeroComprobante);
        dto.setReceivedDate(receivedDate);

        return dto;
    }

    private static Date obtenerFecha(String str) {
        // Implementa la lógica para convertir el string a una instancia de Date
        // Puedes utilizar SimpleDateFormat u otras clases de Java para esto
        return null;
    }
}


/*
JavaMailSender
JavaMailSenderImpl
  nested exception is:
	javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)] with root cause

javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
 */