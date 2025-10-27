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



}
