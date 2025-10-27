package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.PermissionDTO;
import com.example.user_auth.model.entity.Permission;
import com.example.user_auth.reporistory.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;

    public List<PermissionDTO> findAllPermission(){
        return permissionRepo.findAll().stream()
                .map(permission -> new PermissionDTO(permission))
                .toList();
    }

    public PermissionDTO findPermissionById(Long id){
        return new PermissionDTO(permissionRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not found permission by id:"+id)));
    }
    public List<PermissionDTO> findPermissionByResourceType(String resourceTypeName){
        return permissionRepo.findByResourceType(resourceTypeName).stream()
                .map(permission -> new PermissionDTO(permission)).toList();
    }
    public List<PermissionDTO> findPermissionByAction(String action){
        return permissionRepo.findByAction(action).stream()
                .map(permission -> new PermissionDTO(permission))
                .toList();
    }
    public List<PermissionDTO> findPermissionByUserId(Long user_id){
        return permissionRepo.findPermissionByUserId(user_id).stream()
                .map(permission -> new PermissionDTO(permission))
                .toList();
    }
    public Optional<PermissionDTO> findPermissionByName(String name){
        return permissionRepo.findByName(name).map(permission -> new PermissionDTO(permission));
    }
    public boolean existsPermissionByName(String name){
        return permissionRepo.existsByName(name);
    }
    public PermissionDTO createPermission(PermissionDTO permissionDto){
        Permission permission=permissionDto.toPermission();
        return new PermissionDTO(permissionRepo.save(permission));
    }


}
