package org.exemple.data.response;

import lombok.*;
import org.exemple.data.BancoOrigenDTO;

import javax.mail.Address;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BancoOrigenDTOResponse {
    private  List<BancoOrigenDTO> listBancoOrigenDTOs;
    private Message message;
}
