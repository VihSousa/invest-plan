package br.com.VihSousa.invest_plan.service;

import br.com.VihSousa.invest_plan.model.User;
import br.com.VihSousa.invest_plan.repository.UserRepository;
import br.com.VihSousa.invest_plan.service.exception.EmailAlreadyExistsException;
import br.com.VihSousa.invest_plan.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    // UserRepository
    private final UserRepository userRepository; // Spring injects this automatically

    public User findUserById(Long id) { // Helper method to avoid repetition
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    public User createUser(User userToCreate) {

        if (userRepository.existsByEmail(userToCreate.getEmail())) {
            throw new EmailAlreadyExistsException("This email is already in use.");
        }

        // Did it pass? Action goes to the repository.
        return userRepository.save(userToCreate);
    }

    // Delete indirectly (soft delete)
    @Transactional
    public User deleteUser(long id) {
        User user = findUserById(id);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    /* (Forma direta)
    public Usuario deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
        return usuario;
    }
    */

    @Transactional
    public User updatedUser(Long id, User updatedData) {
        User user = findUserById(id);

        Optional<User> userWithNewEmail = userRepository.findByEmail(updatedData.getEmail());
        if (userWithNewEmail.isPresent() && !userWithNewEmail.get().getId().equals(id)) {
             throw new EmailAlreadyExistsException("This email is already in use.");
        }

        // Update fields allowed
        user.setName(updatedData.getName());
        user.setEmail(updatedData.getEmail());

        return userRepository.save(user);
    }

}
