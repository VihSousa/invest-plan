package br.com.VihSousa.invest_plan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.VihSousa.invest_plan.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    boolean existsByNome(String nome);

    Optional<Categoria> findByNome(String nome);

}
