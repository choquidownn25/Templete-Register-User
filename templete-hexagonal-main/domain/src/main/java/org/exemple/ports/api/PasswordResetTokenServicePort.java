package org.exemple.ports.api;


import org.exemple.data.request.PasswordResetTokenRequest;
import org.exemple.data.response.PasswordResetTokenResponse;

public interface PasswordResetTokenServicePort {
    PasswordResetTokenResponse findByToken(String token);
    PasswordResetTokenResponse save(PasswordResetTokenRequest passwordResetToken);
}
