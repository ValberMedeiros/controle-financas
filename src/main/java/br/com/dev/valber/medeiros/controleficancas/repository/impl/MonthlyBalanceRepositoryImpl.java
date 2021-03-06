package br.com.dev.valber.medeiros.controleficancas.repository.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.*;
import br.com.dev.valber.medeiros.controleficancas.domain.request.MonthlyBalanceRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.MonthlyBalanceRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class MonthlyBalanceRepositoryImpl implements MonthlyBalanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public MonthlyBalanceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TotalBalanceDTO getTotalBalanceExpense(LocalDate reference) {
        String sql =
                "SELECT monthly_balance.uuid, monthly_balance.reference_date, sum(expense.amount) as total_amount " +
                "FROM monthly_balance " +
                "INNER JOIN expense ON monthly_balance.uuid = expense.monthly_balance_uuid " +
                "WHERE monthly_balance.reference_date = ?" +
                "GROUP BY monthly_balance.uuid";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TotalBalanceDTO.class), reference);
    }

    @Override
    public List<ExpenseDTO> getExpensesForMonthlyBalances(LocalDate reference) {
        String sql =
                "SELECT expense.uuid, expense.amount, expense.description, expense.due_date, expense.expense_status, " +
                "expense.recurrent, monthly_balance.reference_date as monthlyBalanceReferenceDate " +
                "FROM expense INNER JOIN monthly_balance " +
                "ON monthly_balance_uuid = monthly_balance.uuid WHERE monthly_balance.reference_date = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ExpenseDTO.class), reference);
    }

    @Override
    public List<MonthlyBalanceDateReferenceDTO> getMonthlyBalanceDateReferences() {
        String sql =
                "SELECT monthly_balance.uuid, monthly_balance.reference_date " +
                "FROM monthly_balance";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MonthlyBalanceDateReferenceDTO.class));
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
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MonthlyBalanceDTO.class), uuid);
    }

    @Override
    public UUID getUuidMonthlyBalanceByReferenceDate(LocalDate reference) {
        String sql =
                "SELECT uuid " +
                        "FROM monthly_balance " +
                        "WHERE reference_date = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UUID.class), reference);
    }

    @Override
    public List<IncomeDTO> getIncomesForMonthlyBalance(LocalDate reference) {
        String sql =
                "SELECT income.uuid, income.receipt_date, income.description, income.amount, " +
                        "monthly_balance.reference_date as monthlyBalanceReferenceDate " +
                        "FROM income " +
                        "INNER JOIN monthly_balance " +
                        "ON income.monthly_balance_uuid = monthly_balance.uuid " +
                        "WHERE monthly_balance.reference_date = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(IncomeDTO.class), reference
        );
    }

    @Override
    public TotalBalanceDTO getTotalBalanceIncomes(LocalDate reference) {
        String sql =
                "SELECT monthly_balance.uuid, monthly_balance.reference_date, sum(income.amount) as total_amount " +
                        "FROM monthly_balance " +
                        "INNER JOIN income ON monthly_balance.uuid = income.monthly_balance_uuid " +
                        "WHERE monthly_balance.reference_date = ?" +
                        "GROUP BY monthly_balance.uuid";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TotalBalanceDTO.class), reference);
    }

}
