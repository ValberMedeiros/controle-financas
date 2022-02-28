package br.com.dev.valber.medeiros.controleficancas.repository;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.IncomeDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.IncomeRequestDTO;

import java.util.List;
import java.util.UUID;

public interface IncomeRepository {

    int create(IncomeRequestDTO entity);

    List<IncomeDTO> findAll();

    IncomeDTO findById(UUID uuid);

    void delete(UUID uuid);

    int update(IncomeRequestDTO entity, UUID uuid);

}
