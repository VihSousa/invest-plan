package br.com.VihSousa.invest_plan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.VihSousa.invest_plan.model.Transacao;
import br.com.VihSousa.invest_plan.service.TransacaoService;

public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna o status 201 Created, que é a boa prática para criação.
    public Transacao criarNovaTransacao(
            @PathVariable Long usuarioId, // 2. @PathVariable pega o ID da URL.
            @RequestBody Transacao transacao  // 3. @RequestBody pega o JSON do corpo da requisição.
    ) {
        // 4. A única responsabilidade do controller: delegar para o service.
        return transacaoService.registrarNovaTransacao(usuarioId, transacao);
    }

    // Adicionar um @GetMapping para listar as transações do usuário.

}
