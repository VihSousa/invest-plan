package br.com.vihsousa.invest_plan.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Returns 409 (Conflict) instead of 500
@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
    
}
