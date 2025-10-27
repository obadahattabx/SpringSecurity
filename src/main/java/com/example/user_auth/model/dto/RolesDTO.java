package com.example.user_auth.model.dto;

import com.example.user_auth.model.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesDTO {
    private long id;
    private String name;
    public RolesDTO(Roles roles){
        this.id=roles.getId();
        this.name=roles.getName();

    }
}
