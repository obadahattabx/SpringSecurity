package com.example.user_auth.model.mapper;

import com.example.user_auth.model.dto.RolesDTO;
import com.example.user_auth.model.dto.RolesDTOWithPermission;
import com.example.user_auth.model.entity.Roles;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    Roles toEntity(RolesDTO rolesDTO);
    RolesDTO toDto(Roles roles);
    RolesDTOWithPermission toDtoWithPermission(Roles roles);
    List<RolesDTOWithPermission> toDtoWithPermissionList(List<Roles> rolesList);
}
