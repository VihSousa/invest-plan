package br.com.VihSousa.invest_plan.service.exception;

public class InsufficientFundsException extends RuntimeException {
    
    public InsufficientFundsException(String mensagem) {
        super(mensagem);
    }
    
}
