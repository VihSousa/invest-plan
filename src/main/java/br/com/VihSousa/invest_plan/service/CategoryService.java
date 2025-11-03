package br.com.VihSousa.invest_plan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Category createCategory(Category categoryToSave) {

        if (categoryRepository.existsByName(categoryToSave.getName())) {
            throw new CategoryAlreadyExistsException("This name is already in use.");
        }
        // DELEGATION: Calls the repository to persist the new category.
        return categoryRepository.save(categoryToSave);

    }

    @Transactional
    public void deleteCategory(Long id) {
        // verify if the category exists before trying to delete it
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found!");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category updateCategory(Long id, Category categoryToUpdate) {
        Category existingCategory = this.findById(id);
        String newName = categoryToUpdate.getName();
        Optional<Category> categoryWithNewName = categoryRepository.findByName(newName);

        if (categoryWithNewName.isPresent() && !categoryWithNewName.get().getId().equals(id)) {
            // If the name exists AND the ID is different from the ID of the category we are updating...
            throw new CategoryAlreadyExistsException("This name is already in use by another category.");
        }

        existingCategory.setName(newName);
        return categoryRepository.save(existingCategory);
    }

    public Category findById(Long id) {
        // DELEGATION WITH ERROR HANDLING:
        // Asks the tool to search by ID. If not found, throws an exception.
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
