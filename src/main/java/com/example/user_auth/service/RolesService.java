package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.RolesDTOWithPermission;
import com.example.user_auth.model.entity.Permission;
import com.example.user_auth.model.entity.Roles;
import com.example.user_auth.model.mapper.PermissionMapper;
import com.example.user_auth.model.mapper.RoleMapper;
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

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public Roles createRole(Roles roles){
        return rolesRepo.save(roles);
    }

    public List<RolesDTOWithPermission> getAllRoles(){
        return roleMapper.toDtoWithPermissionList(rolesRepo.findAll());
    }

    public RolesDTOWithPermission assignPermission(Long role_id, String permissionName){
        Roles roles=rolesRepo.findById(role_id)
                .orElseThrow(()-> new ResourceNotFoundException("not found role"));
        Permission permission=permissionMapper.toEntity(permissionService.findPermissionByName(permissionName)
                .orElseThrow(()->new ResourceNotFoundException("not found permission")));
       System.out.println(permission);
        roles.addPermission(permission);
        ;
        return roleMapper.toDtoWithPermission(rolesRepo.save(roles));
    }
}
