package com.example.user_auth.reporistory;

import com.example.user_auth.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Long> {

    Optional<Roles> findByName(String name);

    boolean existsByName(String name);
}
