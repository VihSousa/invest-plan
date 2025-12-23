package br.com.vihsousa.invest_plan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.vihsousa.invest_plan.dto.user.UserCreateDTO;
import br.com.vihsousa.invest_plan.dto.user.UserResponseDTO;
import br.com.vihsousa.invest_plan.dto.user.UserUpdateDTO;
import br.com.vihsousa.invest_plan.model.User;
import br.com.vihsousa.invest_plan.repository.UserRepository;
import br.com.vihsousa.invest_plan.service.exception.EmailAlreadyExistsException;
import br.com.vihsousa.invest_plan.service.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // =========================================================================
    //                              SUCCESS PATH TESTS                                 
    // =========================================================================


    @SuppressWarnings("null")
    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {

        // Arrange
        UserCreateDTO dto = new UserCreateDTO("Vih Sousa", "vih@email.com", "12345678");
        
        User userSaved = new User();
        userSaved.setId(1L);
        userSaved.setName("Vih Sousa");
        userSaved.setEmail("vih@email.com");
        
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        // Act
        UserResponseDTO response = userService.createUser(dto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should find user by ID successfully")
    void shouldFindUserByIdSuccessfully() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("Vih");
        user.setEmail("vih@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO response = userService.findUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Vih", response.name());
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        UserUpdateDTO dto = new UserUpdateDTO("New Name", "new@email.com", "123");
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@email.com");

        // Simulate that the user exists
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Simulate that the new email is NOT being used by anyone
        when(userRepository.findByEmail("new@email.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserResponseDTO response = userService.updateUser(userId, dto);

        // Assert
        assertEquals("New Name", response.name()); // Verify name update
        assertEquals("new@email.com", response.email());
        
        // Verify if the user object was changed before saving
        assertEquals("New Name", existingUser.getName()); 
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    // =========================================================================
    //                              ERROR PATH TESTS                                 
    // =========================================================================

    @SuppressWarnings("null")
    @Test
    @DisplayName("Should not create user if email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        UserCreateDTO dto = new UserCreateDTO("Teste", "duplicado@email.com", "123");
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw error when searching for non-existent ID")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findUserById(99L));
    }

    @Test
    @DisplayName("Should not delete non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(99L));
        verify(userRepository, never()).deleteById(99L);
    }

}