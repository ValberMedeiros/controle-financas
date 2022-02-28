package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.ExpenseDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.ExpenseRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.ExpenseRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.MonthlyBalanceRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.ExpenseService;
import br.com.dev.valber.medeiros.controleficancas.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepositoryImpl repository;
    private MonthlyBalanceRepositoryImpl monthlyBalanceRepository;

    public ExpenseServiceImpl(ExpenseRepositoryImpl repository, MonthlyBalanceRepositoryImpl monthlyBalanceRepository) {
        this.repository = repository;
        this.monthlyBalanceRepository = monthlyBalanceRepository;
    }

    @Override
    public List<ExpenseDTO> findAll() {
        return repository.findAll();
    }

    @Override
    public ExpenseDTO findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public ExpenseDTO update(UUID uuid, ExpenseRequestDTO requestDTO) {
        requestDTO.setMonthlyBalanceUuid(getUuidMontlyBalance(requestDTO.getDueDate()));
        repository.update(requestDTO, uuid);
        return repository.findById(uuid);
    }

    @Override
    public ExpenseDTO create(ExpenseRequestDTO requestDTO) {
        requestDTO.setUuid(UUID.randomUUID());
        requestDTO.setMonthlyBalanceUuid(getUuidMontlyBalance(requestDTO.getDueDate()));
        repository.create(requestDTO);
        return findById(requestDTO.getUuid());
    }

    @Override
    public void delete(UUID uuid) {
        repository.delete(uuid);
    }

    private UUID getUuidMontlyBalance(LocalDate referenceDate) {
        return monthlyBalanceRepository
                .getUuidMonthlyBalanceByReferenceDate(DateUtils.replaceDate(referenceDate.toString()));
    }
}
