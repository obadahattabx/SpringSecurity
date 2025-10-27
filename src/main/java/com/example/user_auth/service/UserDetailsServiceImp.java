package com.example.user_auth.service;

import com.example.user_auth.model.entity.UserPricple;
import com.example.user_auth.reporistory.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPricple userPricple=new UserPricple(userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("not found username")));
        return userPricple;
    }
}
