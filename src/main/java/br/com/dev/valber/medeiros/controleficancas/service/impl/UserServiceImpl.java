package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.UserRepositoryImpl;
import br.com.dev.valber.medeiros.controleficancas.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static br.com.dev.valber.medeiros.controleficancas.security.JWTAuthenticatorFilter.TOKEN_KEY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @Override
    public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var attribute = request.getHeader(AUTHORIZATION);
        if (attribute != null && attribute.startsWith("Bearer ")) {
            try {
                var refreshToken = attribute.replace("Bearer ", "");
                var authenticationToken = getAuthenticationToken(refreshToken);
                UserDTO user = findByUsername(authenticationToken.getName());
                String accessToken = JWT.create()
                        .withSubject(user.getLogin())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .sign(Algorithm.HMAC512(TOKEN_KEY));

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                Map<String, String> error = new HashMap<>();
                error.put("path", request.getServletPath());
                error.put("timestamp", LocalDateTime.now().toString());
                error.put("message", exception.getMessage());
                error.put("unique-id", UUID.randomUUID().toString());
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new BusinessException("Refresh token is missing.", "authentication.failed");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String jwt) {
        var usuario = JWT.require(Algorithm.HMAC512(TOKEN_KEY))
                .build()
                .verify(jwt)
                .getSubject();

        if (usuario == null) {
            throw new BusinessException("JWT inválido.", "invalid.jwt");
        }

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
    }

    private UserDTO entityToDto(User user) {
        return new UserDTO(user.getUuid(), user.getLogin(), user.getPassword());
    }
}
