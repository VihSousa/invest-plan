package br.com.VihSousa.invest_plan.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Returns 409 (Conflict) instead of 500
@ResponseStatus(HttpStatus.CONFLICT)
public class CategoryAlreadyExistsException extends RuntimeException {
    
    public CategoryAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
    
}
