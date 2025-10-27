package com.example.user_auth.service;

import com.example.user_auth.exceptions.ResourceNotFoundException;
import com.example.user_auth.model.entity.RefreshToken;
import com.example.user_auth.model.entity.User;
import com.example.user_auth.reporistory.RefreshTokenRepo;
import com.example.user_auth.reporistory.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${app.jwt.refresh-expiration-ms}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtils jwtUtils;
    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }

    @Transactional
    public  RefreshToken addRefreshToken(String  token,Long userId){

        RefreshToken refreshToken=new RefreshToken();
        User user=userRepo.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("not found user"));

        refreshTokenRepo.deleteByUser(user);
        refreshToken.setUser(user);
        refreshTokenRepo.flush();
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(jwtUtils.extractExpiration(token).toInstant());
        return  refreshTokenRepo.save(refreshToken);


    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(token);
            throw new RuntimeException("Referesh token was expired , please sign in again");
        }
        return  token;
    }

    @Transactional
    public void cleanupExpiredTokens(){
        refreshTokenRepo.deleteByExpiryDateBefore(Instant.now());
    }

    @Transactional
    public void deleteByUserId(long userId){
        User user=userRepo.findById(userId).orElseThrow(()->new UsernameNotFoundException("not found username"));
        refreshTokenRepo.deleteByUser(user);

    }


}
