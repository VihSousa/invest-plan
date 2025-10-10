package br.com.VihSousa.invest_plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.VihSousa.invest_plan.model.Usuario;

@Repository // Indica que esta interface é um repositório Spring
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // O Spring Data JPA lê o nome deste método e automaticamente
    // cria a query SQL: "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM usuarios WHERE email = ?"
    boolean existsByEmail(String email);

    Usuario findByEmail(String email);
    

    
}
