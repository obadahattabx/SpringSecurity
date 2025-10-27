package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.RolesDTOWithPermission;
import com.example.user_auth.model.entity.Permission;
import com.example.user_auth.model.entity.Roles;
import com.example.user_auth.reporistory.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {
    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PermissionService permissionService;

    public Roles createRole(Roles roles){
        return rolesRepo.save(roles);
    }

    public List<RolesDTOWithPermission> getAllRoles(){
        return rolesRepo.findAll().stream()
                .map(roles -> new RolesDTOWithPermission(roles)).toList();
    }

    public RolesDTOWithPermission assignPermission(Long role_id, String permissionName){
        Roles roles=rolesRepo.findById(role_id)
                .orElseThrow(()-> new ResourceNotFoundException("not found role"));
        Permission permission=permissionService.findPermissionByName(permissionName).get().toPermission();
       System.out.println(permission);
        roles.addPermission(permission);
        ;
        return new RolesDTOWithPermission(rolesRepo.save(roles));
    }
}
