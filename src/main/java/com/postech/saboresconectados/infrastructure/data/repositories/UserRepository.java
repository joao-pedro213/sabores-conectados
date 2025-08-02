package com.postech.saboresconectados.infrastructure.data.repositories;

import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByLogin(String login);
}
