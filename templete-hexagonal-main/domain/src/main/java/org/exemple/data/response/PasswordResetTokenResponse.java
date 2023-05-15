package org.exemple.data.response;

import lombok.Data;
import org.exemple.data.UserDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
public class PasswordResetTokenResponse {
    private long id;
    private String token;
    private UserDTO user;
    private LocalDateTime expirationDate;
}
