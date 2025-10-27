package com.example.user_auth.controller;

import com.example.user_auth.model.dto.RolesDTOWithPermission;
import com.example.user_auth.model.entity.Roles;
import com.example.user_auth.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @PostMapping("/addRole")
    public ResponseEntity<Roles> createRoles(@RequestBody Roles roles){
        return new ResponseEntity<>(rolesService.createRole(roles), HttpStatus.OK);
    }
    @GetMapping("/getRoles")
    public  ResponseEntity<List<RolesDTOWithPermission>> findAllRoles(){
        return new ResponseEntity<>(rolesService.getAllRoles(),HttpStatus.OK);
    }
    @PostMapping("/add-permission")
    public  ResponseEntity<RolesDTOWithPermission> assignPermissionRoles(@RequestParam long role_id, @RequestParam String permissionName){
        return new ResponseEntity<>(rolesService.assignPermission(role_id,permissionName),HttpStatus.OK);
    }


}
