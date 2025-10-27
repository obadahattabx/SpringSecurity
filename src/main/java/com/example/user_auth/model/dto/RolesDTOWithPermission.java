package com.example.user_auth.model.dto;

import com.example.user_auth.model.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesDTOWithPermission {
    private long id;
    private String name;
    private List<PermissionDTO> permissions;



}
