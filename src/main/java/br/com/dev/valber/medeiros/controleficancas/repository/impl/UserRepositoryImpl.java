package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findUserByLogin(String login) {
        String sql =
                "SELECT uuid, login, password " +
                        "FROM uuser " +
                        "WHERE login = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new User(
                    UUID.fromString(rs.getString("uuid")),
                    rs.getString("login"),
                    rs.getString("password")
            )
        , login);
    }

}
