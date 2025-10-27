package com.example.user_auth.model.dto;

import com.example.user_auth.model.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PublicKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {
    private long id;
    private String name;
    private String resourceType;
    private String action;


}
