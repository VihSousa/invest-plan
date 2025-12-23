package br.com.vihsousa.invest_plan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vihsousa.invest_plan.dto.category.CategoryCreateDTO;
import br.com.vihsousa.invest_plan.dto.category.CategoryResponseDTO;
import br.com.vihsousa.invest_plan.dto.category.CategoryUpdateDTO;
import br.com.vihsousa.invest_plan.model.Category;
import br.com.vihsousa.invest_plan.repository.CategoryRepository;
import br.com.vihsousa.invest_plan.service.exception.CategoryAlreadyExistsException;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    // =========================================================================
    //                      SUCCESS PATH TESTS                       
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should create category successfully")
    void shouldCreateCategorySuccessfully() {

        // Arrange
        CategoryCreateDTO dto = new CategoryCreateDTO("Entertainment");

        Category categorySaved = new Category();
        categorySaved.setId(10L);
        categorySaved.setName("Entertainment");

        // Simulation that there is NO category with this name
        when(categoryRepository.existsByName(dto.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(categorySaved);

        // Act
        CategoryResponseDTO response = categoryService.createCategory(dto);

        // Assert
        assertNotNull(response);
        assertEquals(10L, response.id());
        assertEquals("Entertainment", response.name());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Should find category by ID successfully")
    void shouldFindCategoryById() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Health");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        CategoryResponseDTO response = categoryService.findById(1L);

        // Assert
        assertEquals("Health", response.name());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should update category successfully")
    void shouldUpdateCategorySuccessfully() {
        // Arrange
        Long categoryId = 1L;
        CategoryUpdateDTO dto = new CategoryUpdateDTO("Supermarket");
        
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Market"); // Old name
        // Simulates ID search
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        
        // Simulating that there is NO category with this name
        when(categoryRepository.findByName(dto.name())).thenReturn(Optional.empty());
        
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // Act
        CategoryResponseDTO response = categoryService.updateCategory(categoryId, dto);

        // Assert
        assertEquals("Supermarket", response.name());
        assertEquals("Supermarket", existingCategory.getName()); // Confirms that the entity was changed
    }

    @Test
    @DisplayName("Should delete category successfully")
    void shouldDeleteCategorySuccessfully() {
        // Arrange
        when(categoryRepository.existsById(1L)).thenReturn(true);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    // =========================================================================
    //                       ERROR PATH TESTS                        
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should throw exception when category name already exists (Create)")
    void shouldThrowExceptionWhenCategoryNameExists() {

        // Arrange
        CategoryCreateDTO dto = new CategoryCreateDTO("Leisure");
        
        // Simulating that it ALREADY EXISTS
        when(categoryRepository.existsByName(dto.name())).thenReturn(true);

        // Act e Assert
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.createCategory(dto));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when category not found (FindById)")
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(99L));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent category")
    void shouldThrowExceptionWhenDeletingNonExistentCategory() {
        when(categoryRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(99L));
        verify(categoryRepository, never()).deleteById(99L);
    }
}