package org.exemple.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.exemple.data.BancoOrigenDTO;
import org.exemple.data.CertificadoBeneficios;
import org.exemple.data.Mail;
import org.exemple.data.Periodo;
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
import java.util.*;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;
import com.google.gson.Gson;

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
        System.out.println("List filter : " + bancoOrigenDTOList);
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

    public CertificadoBeneficios mainCertificadoBeneficios() throws Exception{
        // TODO Auto-generated method stub
        List<Periodo>listPeriodo = new ArrayList<Periodo>();
        String url  = "https://api.floid.app/cl/afc/get_certificado_beneficios";
        String bearerToken = "3c6e1b8f8dbdc4d883068bdd945f216ab2df9dae83950503ad849d2e720f3a9a48feabf4be8ad76f93a1d33d33ad910fc734fd60963f6aaeb5776fa74aaaf1d0";
        String requestPayload = "{ \"id\": \"11111111-1\", \"password\": \"1234\", \"sandbox\": \"true\" }";
        // Establecer la conexión
        // Establecer la conexión
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Enviar la solicitud
        connection.getOutputStream().write(requestPayload.getBytes("UTF-8"));
        connection.getOutputStream().flush();
        connection.getOutputStream().close();

        // Obtener la respuesta
        int responseCode = connection.getResponseCode();
        String response = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = reader.lines().collect(Collectors.joining());
            reader.close();
        } else {
            System.out.println("Error en la respuesta: " + responseCode);
        }

        // Mapear la respuesta a las clases
        Gson gson = new Gson();
        CertificadoBeneficios certificadoBeneficios = gson.fromJson(response, CertificadoBeneficios.class);
        System.out.println("Estado del servicio Numero: " + certificadoBeneficios.getCode());
        System.out.println("Estado del servicio Nombre : " + certificadoBeneficios.getMsg());
        System.out.println("Estado del servicio Mensaje: " + certificadoBeneficios.getCaseid());
        // Imprimir los datos
        for (Periodo datum : certificadoBeneficios.getData()) {
            System.out.println("---------------------------------------------------------" );
            System.out.println("Fecha Solicitud : " + datum.getFecha_sol());


            System.out.println("N° Solicitud: " + datum.getN_sol());
            System.out.println("Nombre de la empresa: " + datum.getNombre_emp());
            System.out.println("Tipo de Solicitud: " + datum.getTipo_sol());
            System.out.println("Fecha Finiquito: " + datum.getFecha_finiquito());
            System.out.println("Fecha Último Giro: " + datum.getFecha_ult_giro());
            System.out.println("Giros Pagados: " + datum.getGiros_pagados());
            System.out.println("Financiamiento CIC: " + datum.getCIC());
            System.out.println("Financiamiento FCS: " + datum.getFCS());
            System.out.println("Totales pagados: " + datum.getTotales_pagados());
            listPeriodo.add(datum);
        }
        listPeriodo.stream().filter(x->x.getN_sol()== "21898888").collect(Collectors.toList());
        List<Periodo> lines = listPeriodo;

        List<Periodo> result = lines.stream()                // convert list to stream
                .filter(line -> !"21898888".equals(line))     // we dont like mkyong
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        result.forEach(System.out::println);
        System.out.println("Filtro " + listPeriodo);
        return certificadoBeneficios;

    }
}


/*
JavaMailSender
JavaMailSenderImpl
  nested exception is:
	javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)] with root cause

javax.net.ssl.SSLHandshakeException: No appropriate protocol (protocol is disabled or cipher suites are inappropriate)
 */