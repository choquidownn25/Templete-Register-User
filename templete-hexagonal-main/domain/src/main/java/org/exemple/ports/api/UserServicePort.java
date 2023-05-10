package org.exemple.ports.api;

import org.exemple.data.UserDTO;
import org.exemple.data.request.SignupRequest;

import javax.validation.Valid;

public interface UserServicePort {
    UserDTO registerUser(SignupRequest signUpRequest);
}
