package br.com.dev.valber.medeiros.controleficancas.repository.income;

import br.com.dev.valber.medeiros.controleficancas.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncomeRepository extends JpaRepository<Income, UUID> {
}
