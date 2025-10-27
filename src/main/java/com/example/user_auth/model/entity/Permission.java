package com.example.user_auth.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,nullable = false)
    private String name;

    @Column(name = "resource_type")
    private String resourceType;

    private String action;

    @CreationTimestamp
    @Column(name = "creat_at")
    private LocalDateTime createAt;

    @ManyToMany(mappedBy = "permissions")
    private List<User> users=new ArrayList<>();

    @ManyToMany(mappedBy = "permissions")
    private List<Roles> roles=new ArrayList<>();
}
