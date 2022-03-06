package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public int create(UserRequestDTO user) {
        String sql =
                "INSERT INTO uuser (uuid, login, password) " +
                        "VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql, user.getUuid(), user.getLogin(), user.getPassword());
    }

    @Override
    public int update(UserRequestDTO user, UUID uuid) {
        String sql =
                "UPDATE uuser " +
                        "SET uuid = ?, login = ?, password = ? " +
                        "WHERE uuid = ?";

        return jdbcTemplate.update(sql, uuid, user.getLogin(), user.getPassword(), uuid);
    }

    @Override
    public List<UserDTO> findAll() {
        String sql =
                "SELECT uuid, login, password " +
                        "FROM uuser";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new UserDTO(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("login"),
                        rs.getString("password")
                )
        );
    }

    @Override
    public int delete(UUID uuid) {
        String sql =
                "DELETE FROM uuser WHERE uuid = ?";

        return jdbcTemplate.update(sql, uuid);
    }

    @Override
    public UserDTO findByUuid(UUID uuid) {
        String sql =
                "SELECT uuid, login, password " +
                        "FROM uuser " +
                        "WHERE uuid = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new UserDTO(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("login"),
                        rs.getString("password")
                )
        , uuid);
    }
}
