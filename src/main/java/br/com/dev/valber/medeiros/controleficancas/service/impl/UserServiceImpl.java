package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.UserRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepositoryImpl userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO findByUsername(String username) {
        try {
            return entityToDto(userRepository.findUserByLogin(username));
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("User [" + username + "] not found.", "user.not.found");
        }
    }

    @Override
    public UserDTO update(UserRequestDTO user, UUID uuid) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.update(user, uuid);
            return userRepository.findByUuid(uuid);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("User [" + uuid + "] not found.", "user.not.found");
        }
    }

    @Override
    public UserDTO create(UserRequestDTO user) {
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        return userRepository.findByUuid(user.getUuid());
    }

    @Override
    public void delete(UUID uuid) {
        try {
            userRepository.findByUuid(uuid);
            userRepository.delete(uuid);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("User [" + uuid + "] not found.", "user.not.found");
        }
    }

    private UserDTO entityToDto(User user) {
        return new UserDTO(user.getUuid(), user.getLogin(), user.getPassword());
    }
}
