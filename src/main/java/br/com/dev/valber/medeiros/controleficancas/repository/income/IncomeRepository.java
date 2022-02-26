package br.com.dev.valber.medeiros.controleficancas.repository.income;

import br.com.dev.valber.medeiros.controleficancas.domain.Income;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {

    /**
     * Query para realizar atualização de uma Income
     * @param dto dados a serem atualizados
     * @param uuid uuid da entidade a ser atualizada
     * @return objeto income com dados atualizados
     */
    @Transactional
    @Modifying
    @Query(value = "update Income " +
            "set amount = :#{#dto.amount}, " +
            "description = :#{#dto.description}, " +
            "receiptDate = :#{#dto.receiptDate} " +
            "where uuid = :uuid")
    public void update(@Param("dto") IncomeRequestDTO dto, @Param("uuid") UUID uuid);

}
