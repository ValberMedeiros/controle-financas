package br.com.dev.valber.medeiros.controleficancas.service.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.exception.BusinessException;
import br.com.dev.valber.medeiros.controleficancas.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String username;

    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        username = "teste";

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Content-Type", "text/html");
        mockRequest.addHeader("if-none-match", "*");
        mockRequest.addHeader("customHeader", "customValue");
        mockRequest.addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZWVlZSIsImlzcyI6Ii9hcGkvbG9naW4iLCJleHAiOjE2NDgwMDYwNjd9.QttbUo1xBLhJyfaewtTjeCUtfcAlkVI4tARbJvsQr5VJN-SRKvttXA_sq9p0wq-Hkge5xUg3GovIeikvvqR4sQ");

        this.request = mockRequest;

    }



    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(getUserDTOs());

        var all = userService.findAll();
        assertEquals(10, all.size());
    }

    @Test
    void findByUsername() {
        when(userRepository.findUserByLogin(any())).thenReturn(getUser());

        var byUsername = userService.findByUsername(username);
        assertEquals("teste", byUsername.getLogin());
    }

    @Test
    void findByUsernameSholdThrowBusinessExceptionWhenUserNotFound() {
        var username = "teste";
        when(userRepository.findUserByLogin(any())).thenThrow(EmptyResultDataAccessException.class);

        Exception exception = assertThrows(BusinessException.class, () -> {
            userService.findByUsername(username);
        });
        assertEquals("User [" + username + "] not found.", exception.getMessage());
    }

    @Test
    void update() {
        when(userRepository.update(any(), any())).thenReturn(1);
        when(userRepository.findByUuid(any())).thenReturn(getUserDTO());

        var update = userService.update(getUserRequestDTO(), UUID.randomUUID());
        assertEquals("teste", update.getLogin());
    }

    @Test
    void updateSholdThrowBusinessExceptionWhenUserNotFound() {
        UUID uuid = UUID.randomUUID();
        var userRequestDTO = getUserRequestDTO();
        when(userRepository.update(any(), any())).thenReturn(1);
        when(userRepository.findByUuid(any())).thenThrow(EmptyResultDataAccessException.class);

        Exception exception = assertThrows(BusinessException.class, () -> {
            userService.update(userRequestDTO, uuid);
        });
        assertEquals("User [" + uuid + "] not found.", exception.getMessage());
    }

    @Test
    void create() {
        when(userRepository.create(any())).thenReturn(1);
        when(userRepository.findByUuid(any())).thenReturn(getUserDTO());

        var userDTO = userService.create(getUserRequestDTO());
        assertEquals("teste", userDTO.getLogin());
    }

    @Test
    void delete() {
        when(userRepository.delete(any())).thenReturn(1);
        when(userRepository.findByUuid(any())).thenReturn(getUserDTO());

        userService.delete(any());

        verify(userRepository, times(1)).delete(any());

    }

    @Test
    void deleteSholdThrowBusinessExceptionWhenUserNotFound() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.delete(any())).thenReturn(1);
        when(userRepository.findByUuid(any())).thenThrow(EmptyResultDataAccessException.class);

        Exception exception = assertThrows(BusinessException.class, () -> {
            userService.delete(uuid);
        });

        assertEquals("User [" + uuid + "] not found.", exception.getMessage());
    }

    @Test
    void getAccessToken() throws IOException {
        var mockHttpServletResponse = new MockHttpServletResponse();
        when(userRepository.findUserByLogin(any())).thenReturn(getUser());
        userService.getAccessToken(request, mockHttpServletResponse);
        assertTrue(mockHttpServletResponse.getContentAsString().contains("access_token"));
    }

    @Test
    void getAccessTokenSholdThrowExceptionWhenUserInTokenIsInvalid() throws IOException {
        var mockHttpServletResponse = new MockHttpServletResponse();
        when(userRepository.findUserByLogin(any())).thenThrow(EmptyResultDataAccessException.class);
        userService.getAccessToken(request, mockHttpServletResponse);
        assertTrue(mockHttpServletResponse.getContentAsString().contains("User [testeeee] not found."));
    }

    @Test
    void getAccessTokenSholdThrowBusinessExceptionWhenTokenIsMissing() throws IOException {
        var mockHttpServletResponse = new MockHttpServletResponse();

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Content-Type", "text/html");
        mockRequest.addHeader("if-none-match", "*");
        mockRequest.addHeader("customHeader", "customValue");

        var requestModified = mockRequest;
        requestModified.addHeader("Authorization", "teste");

        when(userRepository.findUserByLogin(any())).thenReturn(getUser());

        Exception exception = assertThrows(BusinessException.class, () -> {
            userService.getAccessToken(requestModified, mockHttpServletResponse);
        });

        assertEquals("Refresh token is missing.", exception.getMessage());
    }

    private List<UserDTO> getUserDTOs() {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            userDTOS.add(getUserDTO());
        }
        return userDTOS;
    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.randomUUID());
        userDTO.setLogin("teste");
        userDTO.setPassword("teste");
        return userDTO;
    }

    private User getUser() {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setLogin("teste");
        user.setPassword("teste");
        return user;
    }


    private UserRequestDTO getUserRequestDTO() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUuid(UUID.randomUUID());
        userRequestDTO.setLogin("teste");
        userRequestDTO.setPassword("teste");
        return userRequestDTO;
    }
}