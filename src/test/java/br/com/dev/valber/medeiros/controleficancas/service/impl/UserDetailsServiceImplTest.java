package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Test()
    void loadUserByUsername() {
        User user = getUser();

        when(userRepository.findUserByLogin(any())).thenReturn(user);

        var userDetails = userDetailsService.loadUserByUsername(user.getLogin());

        assertNotNull(userDetails);
        assertEquals("junior", userDetails.getUsername());
    }

    @Test()
    void loadUserByUsernameSholdThrowEmptyResultDataAccessExceptionWhenUserNotFound(){
        when(userRepository.findUserByLogin(any())).thenThrow(EmptyResultDataAccessException.class);
        String username = "junior";

        Exception exception = assertThrows(BusinessException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        String expectedMessage = "User [" + username + "] not found, Authentication failed";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test()
    void loadUserByUsernameSholdThrowUsernameNotFoundExceptionWhenUserNameEmpty() {
        when(userRepository.findUserByLogin(any())).thenReturn(new User());
        String username = "";

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    private User getUser() {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setPassword("@1234");
        user.setLogin("junior");

        return user;
    }
}