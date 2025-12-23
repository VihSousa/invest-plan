package br.com.vihsousa.invest_plan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.vihsousa.invest_plan.repository.UserRepository;
import br.com.vihsousa.invest_plan.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vihsousa.invest_plan.dto.user.UserCreateDTO;
import br.com.vihsousa.invest_plan.dto.user.UserResponseDTO;
import br.com.vihsousa.invest_plan.dto.user.UserUpdateDTO;
import br.com.vihsousa.invest_plan.service.UserService;
import br.com.vihsousa.invest_plan.service.exception.EmailAlreadyExistsException;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;

import br.com.vihsousa.invest_plan.service.TokenService;
import br.com.vihsousa.invest_plan.repository.UserRepository;


@WebMvcTest(UserController.class) // Loads only the Web layer context for this Controller
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // The simulated "Postman"

    @Autowired
    private ObjectMapper objectMapper; // Converts Java <-> JSON

    @MockBean
    private UserService userService; // Service mock injected into the Controller

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    // =========================================================================
    //                      SUCCESS PATH TESTS                       
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 201 Created when creating user")
    void shouldReturnCreatedWhenUserCreated() throws Exception {
        UserCreateDTO request = new UserCreateDTO("Vih", "vih@email.com", "12345678");
        UserResponseDTO response = new UserResponseDTO(1L, "Vih", "vih@email.com");

        when(userService.createUser(any(UserCreateDTO.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Vih"));
    }

    @Test
    @DisplayName("Should return 200 OK when finding user by ID")
    void shouldReturnOkWhenUserFound() throws Exception {
        UserResponseDTO response = new UserResponseDTO(1L, "Vih", "vih@email.com");
        when(userService.findUserById(1L)).thenReturn(response);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("vih@email.com"));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 200 OK when updating user")
    void shouldReturnOkWhenUserUpdated() throws Exception {
        UserUpdateDTO updateRequest = new UserUpdateDTO("New Name", "new@email.com", "123");
        UserResponseDTO response = new UserResponseDTO(1L, "New Name", "new@email.com");

        when(userService.updateUser(eq(1L), any(UserUpdateDTO.class))).thenReturn(response);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"));
    }

    @Test
    @DisplayName("Should return 204 No Content when deleting user")
    void shouldReturnNoContentWhenUserDeleted() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    // =========================================================================
    //                       ERROR PATH TESTS                        
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 400 Bad Request when DTO is invalid (Password too short)")
    void shouldReturnBadRequestWhenInvalidData() throws Exception {
        // Password with 3 digits (minimum is 8 in DTO)
        UserCreateDTO invalidRequest = new UserCreateDTO("Vih", "email@teste.com", "123");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest()); // @Valid blocks here
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 409 Conflict when email already exists")
    void shouldReturnConflictWhenEmailExists() throws Exception {
        UserCreateDTO request = new UserCreateDTO("Vih", "dup@email.com", "12345678");

        // Simulate the service by throwing the exception.
        when(userService.createUser(any(UserCreateDTO.class)))
                .thenThrow(new EmailAlreadyExistsException("Email exists"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Data Conflict")); // Validates the GlobalExceptionHandler
    }

    @Test
    @DisplayName("Should return 404 Not Found when user does not exist")
    void shouldReturnNotFoundWhenUserMissing() throws Exception {
        when(userService.findUserById(99L)).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/users/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}