package br.com.vihsousa.invest_plan.controller;

import br.com.vihsousa.invest_plan.dto.auth.LoginRequestDTO;
import br.com.vihsousa.invest_plan.dto.auth.LoginResponseDTO;
import br.com.vihsousa.invest_plan.model.User;
import br.com.vihsousa.invest_plan.service.TokenService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager; // Dependency provided by SecurityConfig
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        // Converts our DTO to the Spring Security credential object
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        // Verifies if the user exists and password is correct (Magic happens here!)
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        // Extract the User object from the authentication result
        User user = (User) auth.getPrincipal();

        // Generate the Token
        String token = tokenService.generateToken(user);

        // Return the token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}