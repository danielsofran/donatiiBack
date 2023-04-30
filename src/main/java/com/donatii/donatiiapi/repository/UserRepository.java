package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAndParola(String email, String parola);
    Optional<User> findUserByEmail(String email);
}

