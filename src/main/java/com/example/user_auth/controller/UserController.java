package com.example.user_auth.controller;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.UserDTO;
import com.example.user_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.user.id")
    public ResponseEntity<UserDTO> getUserByID(@RequestParam Long id){
        return new ResponseEntity<>(userService.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("not found username")),HttpStatus.OK);
    }
    @GetMapping("/getUserEnable")
    @PreAuthorize("@userService.hasPermission('read','user')")
    public ResponseEntity<List<UserDTO>> getUserenableornot(@RequestParam boolean enable){
        return new ResponseEntity<>(userService.findUsernameEnabled(enable),HttpStatus.OK);
    }
    @PutMapping("/DesableAccount")
    @PreAuthorize("@userService.hasPermission('write','user')")
    public  ResponseEntity<UserDTO> desableAccount(@RequestParam long user_id){
        return new ResponseEntity<>(userService.desableAccount(user_id),HttpStatus.OK);
    }

    @PostMapping("/add-permission")
    @PreAuthorize("@userService.hasPermission('write','user')")
    public ResponseEntity<UserDTO> addPermissionUser(@RequestParam long user_id,@RequestParam String permissionName){
        return new ResponseEntity<>(userService.assignPermission(user_id,permissionName),HttpStatus.OK);
    }




}
