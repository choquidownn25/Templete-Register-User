package org.example.mappers;

import org.example.entity.User;
import org.exemple.data.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userDTOToUser(User user);
    User userToUserDto(UserDTO userDTO);
    //listado
    List<UserDTO> UserDTOListToUserList(List<User> userList);
    List<User> UserListToUserDTOList(List<UserDTO> userDTOList);
}
