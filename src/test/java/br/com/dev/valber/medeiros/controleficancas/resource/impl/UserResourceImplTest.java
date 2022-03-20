package br.com.dev.valber.medeiros.controleficancas.resource.impl;

import br.com.dev.valber.medeiros.controleficancas.domain.User;
import br.com.dev.valber.medeiros.controleficancas.domain.dto.UserDTO;
import br.com.dev.valber.medeiros.controleficancas.domain.request.UserRequestDTO;
import br.com.dev.valber.medeiros.controleficancas.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserResourceImplTest {
    public static final String PATH = "/api/users";

    private MockMvc mockMvc;

    @InjectMocks
    private UserResourceImpl resource;

    @Mock
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    void findAll() throws Exception {
        var response = getUserDTOs();

        when(service.findAll()).thenReturn(response);

        mockMvc.perform(
                get(PATH))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var userDTO = getUserRequestDTO();
        var content = mapper.writeValueAsString(userDTO);

        mockMvc.perform(
                post(PATH)
                        .contentType(APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var userDTO = getUserRequestDTO();
        var content = mapper.writeValueAsString(userDTO);

        mockMvc.perform(
                put(PATH + "/{id}", UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(PATH + "/{id}", UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByUsername() throws Exception {
        var response = getUserDTO();

        when(service.findByUsername(any())).thenReturn(response);

        mockMvc.perform(
                get(PATH + "/{username}", "teste"))
                .andExpect(status().isOk());
    }

    @Test
    void refreshToken() throws Exception {
        mockMvc.perform(
                get(PATH + "/token/refresh"))
                .andExpect(status().isOk());
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