package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.ExpenseRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private JdbcTemplate jdbcTemplate;

    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ExpenseDTO> findAll() {
        String sql =
                "SELECT expense.uuid, expense.amount, expense.description, expense.due_date, expense.expense_status, " +
                "expense.recurrent, monthly_balance.reference_date as monthlyBalanceReferenceDate " +
                "FROM expense INNER JOIN monthly_balance " +
                "ON monthly_balance_uuid = monthly_balance.uuid ";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new ExpenseDTO(
                    UUID.fromString(rs.getString("uuid")),
                    rs.getString("expense_status"),
                    rs.getBoolean("recurrent"),
                    rs.getDate("due_date").toLocalDate(),
                    rs.getDate("monthlyBalanceReferenceDate").toLocalDate(),
                    rs.getString("description"),
                    rs.getBigDecimal("amount")
        ));
    }

    @Override
    public ExpenseDTO findById(UUID uuid) {
        String sql =
                "SELECT expense.uuid, expense.amount, expense.description, expense.due_date, expense.expense_status, " +
                        "expense.recurrent, monthly_balance.reference_date as monthlyBalanceReferenceDate " +
                        "FROM expense INNER JOIN monthly_balance " +
                        "ON monthly_balance_uuid = monthly_balance.uuid " +
                        "WHERE expense.uuid = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new ExpenseDTO(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("expense_status"),
                        rs.getBoolean("recurrent"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getDate("monthlyBalanceReferenceDate").toLocalDate(),
                        rs.getString("description"),
                        rs.getBigDecimal("amount")
        ), uuid);
    }

    @Override
    public int create(ExpenseRequestDTO requestDTO) {
        String sql =
                "INSERT INTO expense (uuid, amount, description, due_date, expense_status, " +
                        "recurrent, monthly_balance_uuid) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, requestDTO.getUuid(), requestDTO.getAmount(), requestDTO.getDescription(),
                    requestDTO.getDueDate(), requestDTO.getExpenseStatus().toString(), requestDTO.getRecurrent(),
                    requestDTO.getMonthlyBalanceUuid()
        );
    }

    @Override
    public void delete(UUID uuid) {
        String sql =
                "DELETE FROM expense WHERE uuid = ?";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public int update(ExpenseRequestDTO entity, UUID uuid) {
        String sql =
                "UPDATE expense SET amount = ?, description = ?, due_date = ?, expense_status = ?, " +
                        "recurrent = ?, monthly_balance_uuid = ? " +
                        "WHERE uuid = ?";
        return jdbcTemplate.update(sql, entity.getAmount(), entity.getDescription(),
                entity.getDueDate(), entity.getExpenseStatus().toString(), entity.getRecurrent(),
                entity.getMonthlyBalanceUuid(), uuid
        );
    }
}
