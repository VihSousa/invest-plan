package br.com.VihSousa.invest_plan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.VihSousa.invest_plan.model.Transacao;
import br.com.VihSousa.invest_plan.service.TransacaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Transacao> criarNovaTransacao(
            @PathVariable Long usuarioId,
            @RequestBody Transacao transacao
    ) {
        Transacao transacaoSalva = transacaoService.registrarNovaTransacao(usuarioId, transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSalva);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTransacoesDoUsuario(@PathVariable Long usuarioId) {
        List<Transacao> transacoes = transacaoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(transacoes);
    }

}
