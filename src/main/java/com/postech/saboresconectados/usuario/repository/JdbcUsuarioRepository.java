package com.postech.saboresconectados.usuario.repository;

import com.postech.saboresconectados.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JdbcUsuarioRepository implements UsuarioRepository {

    private JdbcClient jdbcClient;

    @Override
    public Integer create(Usuario usuario) {
       String sql = "INSERT INTO usuarios (id, nome, email, login, senha, tipo, endereco, ultima_alteracao) " +
               "VALUES (:id, :nome, :email, :login, :senha, :tipo, :endereco, :ultima_alteracao);";
       return this.jdbcClient
               .sql(sql)
               .param("id", usuario.getId())
               .param("nome", usuario.getNome())
               .param("email", usuario.getEmail())
               .param("login", usuario.getLogin())
               .param("senha", usuario.getSenha())
               .param("tipo", usuario.getTipoUsuario().getValue())
               .param("endereco", usuario.getEndereco())
               .param("ultima_alteracao", usuario.getUltimaAlteracao())
               .update();
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.of(null);
    }

    @Override
    public Integer update(Usuario usuario) {
        return 0;
    }

    @Override
    public Integer remove(UUID id) {
        return 0;
    }
}
