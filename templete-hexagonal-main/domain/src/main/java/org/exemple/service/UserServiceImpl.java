package org.exemple.service;

import net.bytebuddy.utility.RandomString;
import org.exemple.data.UserDTO;
import org.exemple.data.request.PasswordResetRequest;
import org.exemple.data.request.SignupRequest;
import org.exemple.data.response.Message;
import org.exemple.mapper.UserMapperDTO;
import org.exemple.ports.api.UserServicePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserServicePort {
    private final UserPersistencePort userPersistencePort;
    @Autowired
    public UserServiceImpl(UserPersistencePort userPersistencePort){
        this.userPersistencePort = userPersistencePort;
    }
    @Override
    public UserDTO registerUser(SignupRequest signUpRequest) {
        return userPersistencePort.registerUser(UserMapperDTO.INSTANCE.userDTOToSignupRequest(signUpRequest));
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return userPersistencePort.findUserByEmail(email);
    }

    @Override
    public UserDTO findUserByResetToken(String resetToken) {
        return userPersistencePort.findUserByResetToken(resetToken);
    }

    @Override
    public UserDTO updateResetPasswordToken(String token, String email) {
        return userPersistencePort.updateResetPasswordToken(token, email);
    }


}
