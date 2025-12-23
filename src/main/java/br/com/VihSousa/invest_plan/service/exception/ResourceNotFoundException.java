package br.com.vihsousa.invest_plan.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Returns 404 (Not Found) instead of 500
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
    
}
