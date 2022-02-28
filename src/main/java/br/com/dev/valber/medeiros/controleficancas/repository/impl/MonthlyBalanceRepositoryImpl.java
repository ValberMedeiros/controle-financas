package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.MonthlyBalanceDateReferenceDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.TotalBalanceExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.MonthlyBalanceRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class MonthlyBalanceRepositoryImpl implements MonthlyBalanceRepository {

    public static final String REFERENCE_DATE = "reference_date";
    private JdbcTemplate jdbcTemplate;

    public MonthlyBalanceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TotalBalanceExpenseDTO getTotalBalanceExpense(LocalDate reference) {
        String sql =
                "SELECT monthly_balance.uuid, monthly_balance.reference_date, sum(expense.amount) as total_amount " +
                "FROM monthly_balance " +
                "INNER JOIN expense ON monthly_balance.uuid = expense.monthly_balance_uuid " +
                "WHERE monthly_balance.reference_date = ?" +
                "GROUP BY monthly_balance.uuid";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new TotalBalanceExpenseDTO(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getDate(REFERENCE_DATE).toLocalDate(),
                        rs.getBigDecimal("total_amount")
                ), reference);
    }

    @Override
    public List<ExpenseDTO> getExpensesForMonthlyBalances(LocalDate reference) {
        String sql =
                "SELECT expense.uuid, expense.amount, expense.description, expense.due_date, expense.expense_status, " +
                "expense.recurrent, monthly_balance.reference_date as monthlyBalanceReferenceDate " +
                "FROM expense INNER JOIN monthly_balance " +
                "ON monthly_balance_uuid = monthly_balance.uuid WHERE monthly_balance.reference_date = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new ExpenseDTO(
                            UUID.fromString(rs.getString("uuid")),
                            rs.getString("expense_status"),
                            rs.getBoolean("recurrent"),
                            rs.getDate("due_date").toLocalDate(),
                            rs.getDate("monthlyBalanceReferenceDate").toLocalDate(),
                            rs.getString("description"),
                            rs.getBigDecimal("amount")
                    ),
                    reference
                );
    }

    @Override
    public List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceDateReferences() {
        String sql =
                "SELECT monthly_balance.uuid, monthly_balance.reference_date " +
                "FROM monthly_balance";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new MonthlyBalanceDateReferenceDTO(
                UUID.fromString(rs.getString("uuid")),
                rs.getDate(REFERENCE_DATE).toLocalDate()
        ));
    }

    @Override
    public int create(MonthlyBalanceRequestDTO entity) {
        String sql =
                "INSERT INTO monthly_balance (uuid, reference_date) values (?, ?) ";
        return jdbcTemplate.update(sql, entity.getUuid(), LocalDate.parse(entity.getReferenceDate()));
    }

    @Override
    public MonthlyBalanceDTO update(MonthlyBalanceRequestDTO entity, UUID uuid) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {
        String sql =
                "DELETE FROM monthly_balance WHERE uuid = ?";
        jdbcTemplate.update(sql, uuid);
    }

    @Override
    public MonthlyBalanceDTO getMonthlyBalance(UUID uuid) {
        String sql =
                "SELECT * FROM monthly_balance WHERE uuid = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> new MonthlyBalanceDTO(
                UUID.fromString(rs.getString("uuid")),
                rs.getDate(REFERENCE_DATE).toLocalDate()
        ), uuid);
    }

    @Override
    public UUID getUuidMonthlyBalanceByReferenceDate(LocalDate reference) {
        String sql =
                "SELECT uuid " +
                        "FROM monthly_balance " +
                        "WHERE reference_date = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum)
                -> UUID.fromString(rs.getString("uuid"))
        , reference);
    }
}
