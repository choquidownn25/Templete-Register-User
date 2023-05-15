package org.exemple.data.request;

import lombok.Data;
import org.exemple.data.UserDTO;

import java.time.LocalDateTime;

@Data
public class PasswordResetTokenRequest {
    private long id;
    private String token;
    private UserDTO user;
    private LocalDateTime expirationDate;
}
