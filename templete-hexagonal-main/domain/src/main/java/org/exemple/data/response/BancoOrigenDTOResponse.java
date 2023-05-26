package org.exemple.data.response;

import lombok.*;

import javax.mail.Address;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BancoOrigenDTOResponse {
    private String subject;
    private Address[] from;
    private String nombreCliente;
    private String bancoOrigen;
    private String montoRecibido;
    private String numeroComprobante;

    //private String contend;
    private Date receivedDate;


}
