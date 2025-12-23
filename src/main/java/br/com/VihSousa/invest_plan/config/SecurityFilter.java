package br.com.vihsousa.invest_plan.config;

import br.com.vihsousa.invest_plan.model.User;
import br.com.vihsousa.invest_plan.repository.UserRepository;
import br.com.vihsousa.invest_plan.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // Lombok creates the constructor for final fields automatically
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Recover the token from the header
        var token = this.recoverToken(request);

        if (token != null) {
            // 2. Validate the token (returns the email)
            var login = tokenService.validateToken(token);

            // 3. Find the user in the database
            // Since findByEmail returns Optional<User>, we use .orElse(null) to unwrap it safely
            User user = userRepository.findByEmail(login).orElse(null);

            // 4. If user exists, force authentication in Spring Context
            if (user != null) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // 5. Continue to the next filter in the chain
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        // Removes the prefix "Bearer " to retrieve only the token string
        return authHeader.replace("Bearer ", "");
    }
}