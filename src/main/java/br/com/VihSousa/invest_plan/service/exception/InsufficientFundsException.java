package br.com.VihSousa.invest_plan.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Returns 422 (Unprocessable Entity) instead of 500
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientFundsException extends RuntimeException {
    
    public InsufficientFundsException(String mensagem) {
        super(mensagem);
    }
    
}
