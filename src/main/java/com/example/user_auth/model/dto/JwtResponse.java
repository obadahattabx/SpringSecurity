package com.example.user_auth.model.dto;


import com.example.user_auth.model.entity.Roles;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type="Bearer";
    private String refreshToken;
    private long id;
    private String username;
    private List<String> roles;


    public JwtResponse(String accessToken, String refreshToken, Long id,
                       String username,List<Roles> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.roles = roles.stream()
                .map(r->r.getName())
                .toList();
    }

}
