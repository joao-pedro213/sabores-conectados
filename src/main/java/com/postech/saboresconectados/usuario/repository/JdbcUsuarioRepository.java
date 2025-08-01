package com.postech.saboresconectados.usuario.repository;

import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.model.enumerator.TipoUsuario;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JdbcUsuarioRepository implements UsuarioRepository {

    private JdbcClient jdbcClient;

    @Override
    public void create(Usuario usuario) {
       this.jdbcClient
               .sql("INSERT INTO usuarios (id, nome, email, login, senha, tipo, endereco, ultima_alteracao) " +
                    "VALUES (:id, :nome, :email, :login, :senha, :tipo, :endereco, :ultima_alteracao);")
               .param("id", usuario.getId())
               .param("nome", usuario.getNome())
               .param("email", usuario.getEmail())
               .param("login", usuario.getLogin())
               .param("senha", usuario.getSenha())
               .param("tipo", usuario.getTipoUsuario().getValue())
               .param("endereco", usuario.getEndereco())
               .param("ultima_alteracao", OffsetDateTime.now(ZoneOffset.UTC))
               .update();
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return this.jdbcClient
                .sql("SELECT id, nome, tipo, email, login, senha, endereco, ultima_alteracao " +
                     "FROM usuarios " +
                     "WHERE id = :id;")
                .param("id", id)
                .query((resultSet, rowNumber) -> Usuario
                        .builder()
                        .id(UUID.fromString(resultSet.getString("id")))
                        .nome(resultSet.getString("nome"))
                        .tipoUsuario(TipoUsuario.fromValue(resultSet.getString("tipo")))
                        .email(resultSet.getString("email"))
                        .login(resultSet.getString("login"))
                        .senha(resultSet.getString("senha"))
                        .endereco(resultSet.getString("endereco"))
                        .ultimaAlteracao(resultSet.getObject("ultima_alteracao", OffsetDateTime.class))
                        .build())
                .optional();
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return this.jdbcClient
                .sql("SELECT id, nome, tipo, email, login, senha, endereco, ultima_alteracao " +
                        "FROM usuarios " +
                        "WHERE login = :login;")
                .param("login", login)
                .query((resultSet, rowNumber) -> Usuario
                        .builder()
                        .id(UUID.fromString(resultSet.getString("id")))
                        .nome(resultSet.getString("nome"))
                        .tipoUsuario(TipoUsuario.fromValue(resultSet.getString("tipo")))
                        .email(resultSet.getString("email"))
                        .login(resultSet.getString("login"))
                        .senha(resultSet.getString("senha"))
                        .endereco(resultSet.getString("endereco"))
                        .ultimaAlteracao(resultSet.getObject("ultima_alteracao", OffsetDateTime.class))
                        .build())
                .optional();
    }

    @Override
    public Integer update(UUID id, Usuario usuario) {
        return this.jdbcClient
                .sql("UPDATE usuarios " +
                     "SET nome = :nome, email = :email, endereco = :endereco, ultima_alteracao = :ultima_alteracao " +
                     "WHERE id = :id;")
                .param("nome", usuario.getNome())
                .param("email", usuario.getEmail())
                .param("endereco", usuario.getEndereco())
                .param("ultima_alteracao", OffsetDateTime.now(ZoneOffset.UTC))
                .param("id", id)
                .update();
    }

    @Override
    public Integer deleteById(UUID id) {
        return this.jdbcClient
                .sql("DELETE FROM usuarios where id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public void changePassword(UUID id, String newPassword) {
        this.jdbcClient
                .sql("UPDATE usuarios SET senha = :senha, ultima_alteracao = :ultima_alteracao WHERE id = :id;")
                .param("senha", newPassword)
                .param("ultima_alteracao", OffsetDateTime.now(ZoneOffset.UTC))
                .param("id", id)
                .update();
    }

}
