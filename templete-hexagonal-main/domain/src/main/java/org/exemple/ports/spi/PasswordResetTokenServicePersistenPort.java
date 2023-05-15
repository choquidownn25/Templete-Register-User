package org.exemple.ports.spi;


import org.exemple.data.request.PasswordResetTokenRequest;
import org.exemple.data.response.PasswordResetTokenResponse;

public interface PasswordResetTokenServicePersistenPort {
    PasswordResetTokenResponse findByToken(String token);
    PasswordResetTokenResponse save(PasswordResetTokenRequest passwordResetToken);
}
