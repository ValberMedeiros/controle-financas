package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.IncomeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IncomeRepositoryImpl implements IncomeRepository {

    private JdbcTemplate jdbcTemplate;

    public IncomeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(IncomeRequestDTO entity) {
        String sql =
                "INSERT INTO income (uuid, description, amount, receipt_date) " +
                "VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, entity.getUuid(), entity.getDescription(),
                entity.getAmount(), entity.getReceiptDate());
    }

    @Override
    public List<IncomeDTO> findAll() {
        String sql =
                "SELECT uuid, description, amount, receipt_date FROM income";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new IncomeDTO(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getDate("receipt_date").toLocalDate(),
                        rs.getString("description"),
                        rs.getBigDecimal("amount")
                    ));
    }

    @Override
    public IncomeDTO findById(UUID uuid) {
        String sql =
                "SELECT uuid, description, amount, receipt_date " +
                "FROM income " +
                "WHERE uuid = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new IncomeDTO(
                UUID.fromString(rs.getString("uuid")),
                rs.getDate("receipt_date").toLocalDate(),
                rs.getString("description"),
                rs.getBigDecimal("amount")
        ), uuid);
    }

    @Override
    public void delete(UUID uuid) {
        String sql =
                "DELETE FROM income WHERE income.uuid = ?";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public int update(IncomeRequestDTO entity, UUID uuid) {
        String sql =
                "UPDATE income " +
                "SET description = ?, amount = ?, receipt_date = ? " +
                "WHERE income.uuid = ?";
        return jdbcTemplate.update(sql, entity.getDescription(), entity.getAmount(), entity.getReceiptDate(), uuid);
    }
}
