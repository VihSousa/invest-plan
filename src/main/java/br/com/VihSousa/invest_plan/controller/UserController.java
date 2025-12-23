package br.com.vihsousa.invest_plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vihsousa.invest_plan.service.UserService;
import br.com.vihsousa.invest_plan.dto.user.UserCreateDTO;
import br.com.vihsousa.invest_plan.dto.user.UserResponseDTO;
import br.com.vihsousa.invest_plan.dto.user.UserUpdateDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users") 
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    // ============================== ENDPOINTS ============================== \\

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserResponseDTO savedUser = userService.createUser(userCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
        @PathVariable long id,
        @Valid @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        UserResponseDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
        @PathVariable long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }


}
