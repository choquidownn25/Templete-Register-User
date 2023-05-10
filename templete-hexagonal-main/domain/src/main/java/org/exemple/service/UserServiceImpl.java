package org.exemple.service;

import org.exemple.data.UserDTO;
import org.exemple.data.request.SignupRequest;
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

}
