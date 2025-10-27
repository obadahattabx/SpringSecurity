package com.example.user_auth.model.dto;

import com.example.user_auth.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private boolean enabled=true;
    private List<String> rolesname;
}

