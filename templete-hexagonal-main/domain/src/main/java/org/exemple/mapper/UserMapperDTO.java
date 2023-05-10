package org.exemple.mapper;


import org.exemple.data.UserDTO;
import org.exemple.data.request.SignupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapperDTO {
    UserMapperDTO INSTANCE = Mappers.getMapper(UserMapperDTO.class);
    UserDTO userDTOToSignupRequest(SignupRequest signupRequest);
    SignupRequest signupRequestToUserDTO(UserDTO userDTO);
    }
