package com.postech.saboresconectados.infrastructure.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "tipo", nullable = false)
    private String userType;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String password;

    @Column(name = "endereco", nullable = false)
    private String address;

    @LastModifiedDate
    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDateTime lastUpdated;
}
