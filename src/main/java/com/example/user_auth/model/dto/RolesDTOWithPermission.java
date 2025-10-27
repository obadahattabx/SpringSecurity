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

    public RolesDTOWithPermission(Roles roles){
        this.id=roles.getId();
        this.name=roles.getName();
        this.permissions=roles.getPermissions().stream()
                .map(permission -> new PermissionDTO(permission))
                .toList();
    }

}
