package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.PermissionDTO;
import com.example.user_auth.model.entity.Permission;
import com.example.user_auth.model.mapper.PermissionMapper;
import com.example.user_auth.reporistory.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private PermissionMapper permissionMapper;
    public List<PermissionDTO> findAllPermission(){
        return permissionMapper.toDtoList(permissionRepo.findAll());
    }

    public PermissionDTO findPermissionById(Long id){
        return permissionMapper.toDTO(permissionRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("not found permission by id:"+id)));
    }
    public List<PermissionDTO> findPermissionByResourceType(String resourceTypeName){
        return   permissionMapper.toDtoList(permissionRepo.findByResourceType(resourceTypeName));

    }
    public List<PermissionDTO> findPermissionByAction(String action){
        return permissionMapper.toDtoList(permissionRepo.findByAction(action));
    }
    public List<PermissionDTO> findPermissionByUserId(Long user_id) {
        return permissionMapper.toDtoList(permissionRepo.findPermissionByUserId(user_id));
    }
    public Optional<PermissionDTO> findPermissionByName(String name){
        return permissionMapper.toDTO(permissionRepo.findByName(name));
    }
    public boolean existsPermissionByName(String name){
        return permissionRepo.existsByName(name);
    }
    public PermissionDTO createPermission(PermissionDTO permissionDto){
        Permission permission=permissionMapper.toEntity(permissionDto);
        return permissionMapper.toDTO(permissionRepo.save(permission));
    }


}
