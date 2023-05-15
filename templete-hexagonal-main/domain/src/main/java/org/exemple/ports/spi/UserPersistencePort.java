package org.exemple.ports.spi;

import org.exemple.data.UserDTO;

import java.util.Optional;


public interface UserPersistencePort {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO findUserByEmail(String email);
    UserDTO findUserByResetToken(String resetToken);
    UserDTO updateResetPasswordToken(String token, String email);
}
