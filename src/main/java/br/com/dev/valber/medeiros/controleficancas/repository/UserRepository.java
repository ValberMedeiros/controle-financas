package br.com.dev.valber.medeiros.controleficancas.repository;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    public User findUserByLogin(String login);

    public int create(UserRequestDTO user);

    public int update(UserRequestDTO user, UUID uuid);

    public List<UserDTO> findAll();

    public int delete(UUID uuid);

    public UserDTO findByUuid(UUID uuid);
}
