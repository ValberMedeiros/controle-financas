package br.com.dev.valber.medeiros.controleficancas.repository.income;

import br.com.dev.valber.medeiros.controleficancas.domain.Expense;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    /**
     * Query para realizar atualização de uma Income
     * @param requestDTO dados a serem atualizados
     * @param uuid uuid da entidade a ser atualizada
     * @return objeto income com dados atualizados
     */
    @Transactional
    @Modifying
    @Query(value = "update Expense " +
            "set amount = :#{#requestDTO.amount}, " +
            "description = :#{#requestDTO.description}, " +
            "dueDate = :#{#requestDTO.dueDate}, " +
            "expenseStatus = :#{#requestDTO.expenseStatus}, " +
            "recurrent = :#{#requestDTO.recurrent}, " +
            "monthlyBalance = :#{#requestDTO.#monthlyBalance} " +
            "where uuid = :uuid")
    public void update(ExpenseRequestDTO requestDTO, UUID uuid);
}
