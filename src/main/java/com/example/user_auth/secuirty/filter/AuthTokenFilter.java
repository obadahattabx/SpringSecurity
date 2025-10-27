package com.example.user_auth.secuirty.filter;

import com.example.user_auth.model.entity.UserPricple;
import com.example.user_auth.service.JwtUtils;
import com.example.user_auth.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


    final String authHeader=request.getHeader("Authorization");
    String jwt=null;
    String username=null;
    if(authHeader!=null && authHeader.startsWith("Bearer ")){
        jwt=authHeader.substring(7);

        try {
            username=jwtUtils.getUserNameFromJwtToken(jwt);

        }
        catch (Exception e){
            logger.error("Unable to extract JWT or JWT invalid: " + e.getMessage());
        }
    }
    if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
        UserDetails user=context.getBean(UserDetailsServiceImp.class).loadUserByUsername(username);
        if(jwtUtils.validateJwtToken(jwt,user.getUsername())){
            UsernamePasswordAuthenticationToken authToken
                    =new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
    filterChain.doFilter(request,response);
    }
}
