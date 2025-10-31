package br.com.VihSousa.invest_plan.service.exception;

public class CategoriaJaExistenteException extends RuntimeException {
    
    public CategoriaJaExistenteException(String mensagem) {
        super(mensagem);
    }
    
}
