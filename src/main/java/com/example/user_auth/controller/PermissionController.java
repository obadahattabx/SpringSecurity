package com.example.user_auth.controller;

import com.example.user_auth.model.dto.PermissionDTO;
import com.example.user_auth.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/allPermissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PermissionDTO>> getAllPermission(){
        return new ResponseEntity<>(permissionService.findAllPermission(), HttpStatus.OK);
    }
    @PostMapping("/create-permission")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody  PermissionDTO permissionDTO){
        return new ResponseEntity<>(permissionService.createPermission(permissionDTO),HttpStatus.OK);
    }

    @GetMapping("/getPermission")
    @PreAuthorize("hasRole('ADMIN') or #user_id == principal.user.id ")
    public ResponseEntity<PermissionDTO> getPermissionByUserId(@RequestParam long user_id){
        return new ResponseEntity<>(permissionService.findPermissionById(user_id),HttpStatus.OK);
    }



}
