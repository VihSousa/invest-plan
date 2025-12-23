package br.com.vihsousa.invest_plan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vihsousa.invest_plan.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    boolean existsByName(String name);

    Optional<Category> findByName(String name);

}
