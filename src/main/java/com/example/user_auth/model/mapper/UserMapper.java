package com.example.user_auth.model.mapper;

import com.example.user_auth.model.dto.UserDTO;
import com.example.user_auth.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
    List<UserDTO> toListDTO(List<User> users);

}
