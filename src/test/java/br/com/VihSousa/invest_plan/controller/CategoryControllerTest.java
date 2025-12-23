package br.com.vihsousa.invest_plan.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vihsousa.invest_plan.dto.category.CategoryCreateDTO;
import br.com.vihsousa.invest_plan.dto.category.CategoryResponseDTO;
import br.com.vihsousa.invest_plan.dto.category.CategoryUpdateDTO;
import br.com.vihsousa.invest_plan.service.CategoryService;
import br.com.vihsousa.invest_plan.service.exception.CategoryAlreadyExistsException;
import br.com.vihsousa.invest_plan.service.TokenService;
import br.com.vihsousa.invest_plan.repository.UserRepository;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    // =========================================================================
    //                      SUCCESS PATH TESTS                       
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 201 Created when category created")
    void shouldReturnCreatedWhenCategoryCreated() throws Exception {
        CategoryCreateDTO request = new CategoryCreateDTO("Entertainment");
        CategoryResponseDTO response = new CategoryResponseDTO(1L, "Entertainment");

        when(categoryService.createCategory(any())).thenReturn(response);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Entertainment"));
    }

    @Test
    @DisplayName("Should return 200 OK with list of categories")
    void shouldReturnListWhenFindAll() throws Exception {
        when(categoryService.findAll()).thenReturn(List.of(
            new CategoryResponseDTO(1L, "Entertainment"),
            new CategoryResponseDTO(2L, "Health ")
        ));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 200 OK when category updated")
    void shouldReturnOkWhenCategoryUpdated() throws Exception {
        // Arrange
        Long categoryId = 1L;
        CategoryUpdateDTO updateRequest = new CategoryUpdateDTO("Supermarket");
        CategoryResponseDTO response = new CategoryResponseDTO(categoryId, "Supermarket");

        // Mock configured to accept any ID and any update DTO
        when(categoryService.updateCategory(eq(categoryId), any(CategoryUpdateDTO.class)))
            .thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/categories/{id}", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Supermarket"));
    }

    // =========================================================================
    //                       ERROR PATH TESTS                        
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 400 Bad Request when name is blank")
    void shouldReturnBadRequestWhenNameBlank() throws Exception {
        // DTO invalid
        CategoryCreateDTO invalidRequest = new CategoryCreateDTO(""); 

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should return 409 Conflict when category name exists")
    void shouldReturnConflictWhenNameExists() throws Exception {
        CategoryCreateDTO request = new CategoryCreateDTO("Entertainment");
        
        when(categoryService.createCategory(any()))
            .thenThrow(new CategoryAlreadyExistsException("Category already exists"));

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Category Conflict"));
    }
}