package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.dto.JwtResponse;
import com.example.user_auth.model.dto.LoginRequest;
import com.example.user_auth.model.entity.User;
import com.example.user_auth.reporistory.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Transactional
    public JwtResponse createAuth(LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("here "+authentication.isAuthenticated());
            UserDetails userDetails=(UserDetails) authentication.getPrincipal();
            User user=userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("not found"));
            String accessToken=jwtUtils.generateToken(authentication);
            String refrechToken=jwtUtils.generateRefreshToken(authentication);
            refreshTokenService.addRefreshToken(refrechToken,user.getId());
            return new JwtResponse(accessToken,refrechToken,user.getId(),user.getUsername(),user.getRoles());
        }
        return null;

    }
    public void logout(long userId){
        refreshTokenService.deleteByUserId(userId);
    }
    public JwtResponse generateTokenByRefreshToken(String token){
    User user=refreshTokenService.findByToken(token)
            .orElseThrow(()->new ResourceNotFoundException("not found token"))
            .getUser();
    String accessToken=jwtUtils.generateTokenFromUsername(user.getUsername());
    return new JwtResponse(accessToken,token,user.getId(),user.getUsername(),user.getRoles());


    }

}
