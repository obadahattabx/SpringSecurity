package com.example.user_auth.reporistory;

import com.example.user_auth.model.entity.RefreshToken;
import com.example.user_auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    boolean existsByUserId(long userId);

    @Modifying
    void deleteByUser(User user);

    @Modifying
    void deleteByExpiryDateBefore (Instant now);
}
