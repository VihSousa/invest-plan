package br.com.VihSousa.invest_plan.service.exception;

public class SaldoInsuficienteException extends RuntimeException {
    
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
    
}
