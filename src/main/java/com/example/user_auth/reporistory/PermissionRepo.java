package com.example.user_auth.reporistory;

import com.example.user_auth.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {

    Optional<Permission> findByName(String name);
    boolean existsByName(String name);
    List<Permission> findByResourceType(String resourceType);
    List<Permission> findByAction(String action);

    @Query(value = "SELECT p From Permission p JOIN p.users u WHERE u.id= :userId")
    List<Permission> findPermissionByUserId(@Param("userId") long userId);
}
