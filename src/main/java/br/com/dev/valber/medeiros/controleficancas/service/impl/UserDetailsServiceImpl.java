package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.UserRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.security.UserDetailsData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositoryImpl userRepository;

    public UserDetailsServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User userByLogin = null;

        if (username.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        try {
            userByLogin = userRepository.findUserByLogin(username);
        } catch (EmptyResultDataAccessException ex) {
            throw new BusinessException("User [" + username + "] not found, Authentication failed", "authentication.failed");
        }

        return new UserDetailsData(Optional.of(userByLogin));
    }

}
