package br.com.VihSousa.invest_plan.service;

import br.com.VihSousa.invest_plan.dto.user.UserCreateDTO;
import br.com.VihSousa.invest_plan.dto.user.UserResponseDTO;
import br.com.VihSousa.invest_plan.dto.user.UserUpdateDTO;
import br.com.VihSousa.invest_plan.model.User;
import br.com.VihSousa.invest_plan.repository.UserRepository;
import br.com.VihSousa.invest_plan.service.exception.EmailAlreadyExistsException;
import br.com.VihSousa.invest_plan.service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    // UserRepository
    private final UserRepository userRepository; // Spring injects this automatically

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("This email is already in use.");
        }

        // Transform DTO to Entity
        User newUser = new User();
        newUser.setName(dto.name());
        newUser.setEmail(dto.email());

        // Saving Entity
        User savedUser = userRepository.save(newUser);

        // Did it pass? Action goes to the repository.
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    @Transactional
    public UserResponseDTO updateUser(long id, UserUpdateDTO dto) {
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        // Fetch existing user details
        String newName = dto.name();
        String newEmail = dto.email();

        // Check if the new email is already in use by another user
        Optional<User> userWithNewEmail = userRepository.findByEmail(newEmail);
        if (userWithNewEmail.isPresent() && !userWithNewEmail.get().getId().equals(id)) {
            throw new EmailAlreadyExistsException("This email is already in use.");
        }

        // Update fields allowed
        existingUser.setName(newName);
        existingUser.setEmail(newEmail);

        User savedUser = userRepository.save(existingUser);

        // Return the updated user as DTO
        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());

    }

    // Delete indirectly (soft delete)
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found!");
        }
        userRepository.deleteById(id);
    }

    /* (Forma direta)
    public Usuario deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
        return usuario;
    }
    */

    public UserResponseDTO findUserById(long id) { 
        
        // Service searches for the user entity by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        // Transform DTO to Entity
        UserResponseDTO responseDto = new UserResponseDTO(
            user.getId(), 
            user.getName(), 
            user.getEmail()
        );

        // Return the DTO
        return responseDto;
    }

}
