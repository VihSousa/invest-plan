package br.com.VihSousa.invest_plan.service.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
    
}
