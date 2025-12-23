package br.com.vihsousa.invest_plan.service;

import br.com.vihsousa.invest_plan.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void setup() {
        // Since we are not loading Spring Context, @Value won't work automatically.
        // We use ReflectionTestUtils to inject the secret key manually for testing.
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret-key");
    }

    @Test
    void shouldGenerateAndValidateToken() {
        // 1. Arrange (Prepare the User)
        User user = new User();
        user.setEmail("test@example.com");

        // 2. Act (Generate the Token)
        String token = tokenService.generateToken(user);

        // 3. Assert (Check if token is created)
        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());

        // 4. Act Again (Validate the Token)
        String subject = tokenService.validateToken(token);

        // 5. Assert (Check if the token contains the correct email)
        Assertions.assertEquals("test@example.com", subject);
    }

    @Test
    void shouldReturnEmptyStringForInvalidToken() {
        String result = tokenService.validateToken("invalid-token-string");
        Assertions.assertEquals("", result);
    }
}