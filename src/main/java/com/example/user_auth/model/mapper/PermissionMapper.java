package com.example.user_auth.model.mapper;

import com.example.user_auth.model.dto.PermissionDTO;
import com.example.user_auth.model.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity(PermissionDTO permissionDTO);
    Optional<PermissionDTO> toDTO(Optional<Permission> permission);
    PermissionDTO toDTO(Permission permission);
    List<PermissionDTO> toDtoList(List<Permission> permissionList);
}
