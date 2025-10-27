package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.UserDTO;
import com.example.user_auth.model.entity.Permission;
import com.example.user_auth.model.entity.Roles;
import com.example.user_auth.model.entity.User;
import com.example.user_auth.model.mapper.PermissionMapper;
import com.example.user_auth.model.mapper.UserMapper;
import com.example.user_auth.reporistory.RolesRepo;
import com.example.user_auth.reporistory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;
    public List<UserDTO> findAllUser(){
        return userMapper.toListDTO(userRepo.findAll());
    }

    public UserDTO findById(long userId){
        return userMapper.toDTO(userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("not found username")));
    }

    public UserDTO findByUsername(String username){
        return userMapper.toDTO(userRepo.findByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("not found username")));
    }
    public Optional<User> findByUsernameU(String username){
        return userRepo.findByUsername(username);
    }



    public UserDTO registerUser(User user,List<String> rolesname){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(rolesname!=null && !rolesname.isEmpty()){
           List<Roles> roles=rolesname.stream()
                   .map(rolename->rolesRepo.findByName(rolename)
                           .orElseThrow(()->new RuntimeException("not found roles")))
                   .toList();
           user.setRoles(roles);
            roles.stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .forEach(user::addPermission);

        }

        User usersaved=userRepo.save(user);
        return userMapper.toDTO(usersaved);


    }

    public UserDTO updateUserRoles(long userId,List<String> rolesName){
        User user=userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("username not found"));
        if(rolesName!=null && !rolesName.isEmpty()){
            List<Roles>roles=rolesName.stream()
                    .map(roleName->rolesRepo.findByName(roleName)
                            .orElseThrow(()->new RuntimeException("not found roles")))
                    .toList();
            user.setRoles(roles);
        }
        User userUpdate=userRepo.save(user);
        return userMapper.toDTO(userUpdate);
    }

    public void deleteUser(long userId){
        userRepo.deleteById(userId);
    }

    public  boolean existsByUsername(String username){
        return userRepo.existsByUsername(username);
    }

    public List<UserDTO> getAllUser(){
        return userMapper.toListDTO(userRepo.findAll());
    }

    public UserDTO addRole(Roles roles,long userId){
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("not found username"));
        user.addRole(roles);
        return userMapper.toDTO(userRepo.save(user));
    }
    public void deleteUser(Long user_id){

        userRepo.deleteById(user_id);
    }
    public boolean existsUsername(String username){
        return userRepo.existsByUsername(username);
    }

    public List<UserDTO> findUsernameEnabled(boolean enable){
        return userMapper.toListDTO(userRepo.findByUsernameAndEnabled(enable));
    }
    public UserDTO desableAccount(long userId){
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("not found username"));
        user.setEnabled(false);
        userRepo.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO assignPermission(Long user_id,String permissionName){
        User user=userRepo.findById(user_id)
                .orElseThrow(()->new ResourceNotFoundException("not found username"));
        Permission permission =permissionMapper.toEntity(permissionService.findPermissionByName(permissionName)
                .orElseThrow(()->new ResourceNotFoundException("not found permission")));;
        user.addPermission(permission);
        return  userMapper.toDTO(userRepo.save(user));
    }

    public boolean hasPermission(String action,String resource){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        User user=userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("not found username"));
        return user.hasPermission(action.toUpperCase(),resource.toUpperCase());
    }


}
