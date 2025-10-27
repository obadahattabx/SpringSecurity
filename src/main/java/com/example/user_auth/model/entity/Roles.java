package com.example.user_auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.dynalink.linker.LinkerServices;
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
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "creat_at")
    private LocalDateTime CreatAt;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users=new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_permission",
    joinColumns = @JoinColumn(name = "roles_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @OrderBy("name ASC")
    private List<Permission>permissions =new ArrayList<>();


    public void addPermission(Permission permission){
        if(!permissions.contains(permission))
            permissions.add(permission);
    }
}
