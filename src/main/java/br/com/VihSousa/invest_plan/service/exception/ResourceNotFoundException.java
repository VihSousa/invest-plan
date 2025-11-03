package br.com.VihSousa.invest_plan.service.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
    
}
