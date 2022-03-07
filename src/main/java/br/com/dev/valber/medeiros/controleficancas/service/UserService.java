package br.com.dev.valber.medeiros.controleficancas.service;

import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserDTO> findAll();

    public UserDTO findByUsername(String username);

    public UserDTO update(UserRequestDTO user, UUID uuid);

    public void delete(UUID uuid);

    public UserDTO create(UserRequestDTO user);

    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
