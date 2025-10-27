package com.example.user_auth.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotBlank
    @Size(min = 4 ,max = 20,message = "Username must be between 4 to 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
//    @Size( max = 20, message = "Password must be  20 maximum characters")
//    @Pattern(
//            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).+$",
//            message = "Password must contain at least one uppercase......."
//    )
    private String password;

    @Column(name = "create_at")
    private LocalDateTime creatAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    private boolean enabled =true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @OrderBy("name ASC")
    private List<Roles> roles=new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @OrderBy("name ASC")
    private List<Permission> permissions=new ArrayList<>();


    @PrePersist
    protected  void onCreat(){
        creatAt=LocalDateTime.now();
        updateAt=LocalDateTime.now();
    }

    @PreUpdate
    protected  void onUpdate(){
        updateAt=LocalDateTime.now();
    }

    public boolean hasRole(String roleName){
        return roles.stream().anyMatch(r->r.getName().equals(roleName));
    }

    public void addRole(Roles role){
        if(!roles.contains(role))
            roles.add(role);
    }

    public void removeRole(Roles role){
        roles.remove(role);
    }

    public boolean hasPermission(String action,String resourceType){
        String permissionName=action+"_"+resourceType;

        return permissions.stream()
                .anyMatch(p->p.getName().equals(permissionName));
    }
    public void addPermission(Permission permission){
        if(!permissions.contains(permission)){
            permissions.add(permission);
        }
    }
}
