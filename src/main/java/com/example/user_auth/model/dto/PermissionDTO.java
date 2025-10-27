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

    public PermissionDTO(Permission permission){
        this.id=permission.getId();
        this.name=permission.getName();
        this.resourceType=permission.getResourceType();
        this.action=permission.getAction();
    }

    public Permission toPermission(){
        Permission p=new Permission();
        p.setId(this.id);
        p.setName(this.name);
        p.setResourceType(this.resourceType);
        p.setAction(this.action);
        return p;
    }

}
