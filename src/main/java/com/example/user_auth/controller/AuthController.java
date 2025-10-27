package com.example.user_auth.controller;

import com.example.user_auth.model.dto.JwtResponse;
import com.example.user_auth.model.dto.LoginRequest;
import com.example.user_auth.model.dto.SignupRequest;
import com.example.user_auth.model.dto.UserDTO;
import com.example.user_auth.model.entity.User;
import com.example.user_auth.service.AuthService;
import com.example.user_auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody SignupRequest signupRequest){
        User user= new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(signupRequest.getPassword());
        user.setEnabled(signupRequest.isEnabled());
        List<String> rolename=signupRequest.getRolesname();
        return new ResponseEntity<>(userService.registerUser(user,rolename), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.createAuth(loginRequest),HttpStatus.OK);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<JwtResponse> refreshtoken(@RequestBody Map<String,String> request){
    String token=request.get("refreshToken");
    return new ResponseEntity<>(authService.generateTokenByRefreshToken(token),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> signout(@RequestParam long userID){
        authService.logout(userID);
        return new ResponseEntity<>("Sign out successfully ",HttpStatus.OK);

    }
}
