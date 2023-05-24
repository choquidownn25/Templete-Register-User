package org.exemple.data.response;
import lombok.*;

import javax.mail.Address;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmailDTORespopnse {
    private String subject;
    private Address[] from;
    private String dni;
    private String company;
    private String phone;
    private String contend;
    private Date receivedDate;

}
