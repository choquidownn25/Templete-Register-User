package org.exemple.ports.spi;

import org.exemple.data.UserDTO;


public interface UserPersistencePort {
    UserDTO registerUser(UserDTO userDTO);
}
