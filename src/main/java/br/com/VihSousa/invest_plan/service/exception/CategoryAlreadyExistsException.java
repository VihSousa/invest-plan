package br.com.VihSousa.invest_plan.service.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    
    public CategoryAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
    
}
