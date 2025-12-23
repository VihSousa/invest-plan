package br.com.vihsousa.invest_plan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vihsousa.invest_plan.model.User;

@Repository // Indicates that this interface is a Spring repository
public interface UserRepository extends JpaRepository<User, Long> {

    // creates the SQL query: "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM users WHERE email = ?"
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
