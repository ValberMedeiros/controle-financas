package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserDTO> findAll();

    public UserDTO findByUsername(String username);

    public UserDTO update(UserRequestDTO user, UUID uuid);

    public void delete(UUID uuid);

    public UserDTO create(UserRequestDTO user);

}
