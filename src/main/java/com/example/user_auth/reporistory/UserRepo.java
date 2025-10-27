package com.example.user_auth.reporistory;

import com.example.user_auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String  username);

    boolean existsByUsername(String username);

    @Query(value = "SELECT u FROM User u JOIN u.roles r where r.name=:roleName ")
    Optional<List<User>> findByRoleName(@Param("roleName") String  roleName);

    @Query(value = "SELECT u FROM User u WHERE u.enabled= :enable")
    List<User> findByUsernameAndEnabled(@Param("enable") boolean enable);
}
