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

import br.com.VihSousa.invest_plan.model.Category;
import br.com.VihSousa.invest_plan.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController //Just receives HTTP requests 
@RequestMapping("/categories") //Base address
@RequiredArgsConstructor
public class CategoryController {

    //Controllers need a Service to perform actions.
    private final CategoryService categoryService;

    // ============================== ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
        ) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/{id}")
    // Does not need to return the deleted object, just a success status
    public ResponseEntity<Void> deleteCategory(
        @PathVariable Long id
    ) {
        categoryService.deleteCategory(id); 
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    // List all categories
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // Search for a single category
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
 