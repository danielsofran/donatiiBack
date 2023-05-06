package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndParola(String email, String parola);
    Optional<User> findUserByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.sustineri s WHERE s = :cauzaId AND u.id = :userId")
    boolean existsSustinere(@Param("userId") Long userId, @Param("cauzaId") Long cauzaId);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sustineri(user_id, cauza_id) VALUES (:userId, :cauzaId)", nativeQuery = true)
    void addSustinere(@Param("userId") Long userId, @Param("cauzaId") Long cauzaId);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM sustineri WHERE user_id = :userId AND cauza_id = :cauzaId", nativeQuery = true)
    void deleteSustinere(@Param("userId") Long userId, @Param("cauzaId") Long cauzaId);
}

