package org.exemple.utils;

import org.exemple.data.BancoOrigenDTO;
import org.exemple.data.response.BancoOrigenDTOResponse;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extact {
    // Método auxiliar para obtener la información coincidente del Matcher
    public  String obtenerInformacion(Matcher matcher) {
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    public  String extractBancoOrigen(String texto) {
        Pattern pattern = Pattern.compile("Banco de origen\\r\\n(.+?)\\r\\n");
        Matcher matcher = pattern.matcher(texto);
        Pattern patternBancoDestino = Pattern.compile("Banco de destino\\r\\n(.+?)\\r\\n");
        Matcher matcherBancoDestino = patternBancoDestino.matcher(texto);

        Pattern patternBancoDestinoUpper = Pattern.compile("Banco Destino:\\\\s*([\\\\w\\\\s-]+)");
        Matcher matcherBancoDestinoUpper = patternBancoDestinoUpper.matcher(texto);

        Pattern patternBanco = Pattern.compile("Banco\\s*:\\s*([^\\n]+)");
        Matcher matcherBanco = patternBanco.matcher(texto);

        Pattern patternBancoItau = Pattern.compile("Banco Destino:\\s*(.*)");
        Matcher matcherBancoItau = patternBancoItau.matcher(texto);


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
        if(matcherBancoItau.find()){
            return matcherBancoItau.group(1);
        }
        return "";
    }

    public  String extractMontoRecibido(String texto) {
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
    public  String extractNombreCliente(String texto) {
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
    public  String extractNumeroComprobante(String texto) {
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
    public List<BancoOrigenDTO> filtrarLista(List<BancoOrigenDTO> listaObjetos, String id) {
        for (BancoOrigenDTO objeto : listaObjetos) {
            if (objeto.getBancoOrigen() == id) {
                return (List<BancoOrigenDTO>) objeto;
            }
        }
        return null;
    }

    public List<String> filtrarListas(List<String> listaObjetos, String id) {
        for (String objeto : listaObjetos) {
            if (objeto == id) {
                return Collections.singletonList(objeto);
            }
        }
        return null;
    }
}
