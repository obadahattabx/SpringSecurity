package com.example.user_auth.model.dto;

import com.example.user_auth.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    @NotBlank
    private String username;
    private boolean enabled;
    private List<RolesDTO> roles;
    private List<PermissionDTO> permissions;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.enabled = user.isEnabled();
        this.roles = user.getRoles().stream()
                .map(roles1 -> new RolesDTO(roles1)).toList();
        this.permissions=user.getPermissions().stream()
                .map(permission -> new PermissionDTO(permission))
                .toList();
    }
}
