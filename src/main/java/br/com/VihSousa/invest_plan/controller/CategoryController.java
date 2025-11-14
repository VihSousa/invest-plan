package br.com.VihSousa.invest_plan.controller;

import java.util.List; 

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.dto.category.CategoryCreateDTO;
import br.com.VihSousa.invest_plan.dto.category.CategoryResponseDTO;
import br.com.VihSousa.invest_plan.dto.category.CategoryUpdateDTO;
import br.com.VihSousa.invest_plan.service.CategoryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController //Just receives HTTP requests 
@RequestMapping("/categories") //Base address
@RequiredArgsConstructor
public class CategoryController {

    //Controllers need a Service to perform actions.
    private final CategoryService categoryService;

    // ============================== ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryResponseDTO savedDto = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable long id,
            @Valid @RequestBody CategoryUpdateDTO dto
        ) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/{id}")
    // Does not need to return the deleted object, just a success status
    public ResponseEntity<Void> deleteCategory(
        @PathVariable long id
    ) {
        categoryService.deleteCategory(id); 
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    // List all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // Search for a single category
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
 