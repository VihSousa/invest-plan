package br.com.VihSousa.invest_plan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.VihSousa.invest_plan.dto.category.CategoryCreateDTO;
import br.com.VihSousa.invest_plan.dto.category.CategoryResponseDTO;
import br.com.VihSousa.invest_plan.dto.category.CategoryUpdateDTO;

import br.com.VihSousa.invest_plan.model.Category;
import br.com.VihSousa.invest_plan.repository.CategoryRepository;
import br.com.VihSousa.invest_plan.service.exception.CategoryAlreadyExistsException;
import br.com.VihSousa.invest_plan.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    // The Service needs the Repository to save things.
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryCreateDTO dto) {

        if (categoryRepository.existsByName(dto.name())) {
            throw new CategoryAlreadyExistsException("This name is already in use.");
        }

        //Transforming DTO to Entity
        Category newCategory = new Category();
        newCategory.setName(dto.name());

        //Saving Entity
        Category savedCategory = categoryRepository.save(newCategory);

        //Transforming Entity to Response DTO
        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName());

    }

    @Transactional
    public void deleteCategory(long id) {
        // verify if the category exists before trying to delete it
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found!");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(long id, CategoryUpdateDTO dto) {
        
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
                
        // Fetch the existing category
        String newName = dto.name();
        Optional<Category> categoryWithNewName = categoryRepository.findByName(newName);

        // Check if the new name is already taken by another category
        if (categoryWithNewName.isPresent() && !categoryWithNewName.get().getId().equals(id)) {
            // If the name exists AND the ID is different from the ID of the category we are updating...
            throw new CategoryAlreadyExistsException("This name is already in use by another category.");
        }

        // Update the category's name
        existingCategory.setName(newName);

        Category updatedCategory = categoryRepository.save(existingCategory);

        return new CategoryResponseDTO(updatedCategory.getId(), updatedCategory.getName());
    }

    public CategoryResponseDTO findById(long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        CategoryResponseDTO responseDTO = new CategoryResponseDTO(
            category.getId(), 
            category.getName());

        return responseDTO;

        /*/ DELEGATION WITH ERROR HANDLING:
        // Asks the tool to search by ID. If not found, throws an exception.
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        */
        
    }
    
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        List<Category> categoryEntities = categoryRepository.findAll();

        // Transforming Entities to Response DTOs
        return categoryEntities.stream()
                .map(category -> new CategoryResponseDTO(category.getId(), category.getName()))
                .toList();
    }

}
