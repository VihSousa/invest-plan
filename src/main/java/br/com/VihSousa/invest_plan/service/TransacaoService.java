package br.com.VihSousa.invest_plan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante!

import br.com.VihSousa.invest_plan.model.Categoria;
import br.com.VihSousa.invest_plan.model.Transacao;
import br.com.VihSousa.invest_plan.model.Usuario;
import br.com.VihSousa.invest_plan.repository.CategoriaRepository;
import br.com.VihSousa.invest_plan.repository.TransacaoRepository;
import br.com.VihSousa.invest_plan.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class TransacaoService {
    
    // O Service precisa de TODOS os repositórios com os quais vai interagir.
    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /* @Transactional: A anotação MÁGICA.
        Garante que todas as operações com o banco de dados dentro deste método
        sejam uma única "transação atômica". Se algo der errado no meio do caminho
        (ex: o usuário não tem saldo), o Spring desfaz TUDO o que foi feito. */
    @Transactional
    public Transacao registrarNovaTransacao(Long usuarioId, Transacao dadosDaTransacao) {
        
        // Busca as entidades relacionadas ou lança um erro se não existirem.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!")); //Criar exceção customizada

        Categoria categoria = categoriaRepository.findById(dadosDaTransacao.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        // A LÓGICA DE NEGÓCIO PRINCIPAL: Atualizar o saldo.
        BigDecimal valor = dadosDaTransacao.getValor();
        switch (dadosDaTransacao.getTipo()) {
            case RECEITA:
                usuario.setSaldo(usuario.getSaldo().add(valor));
                break;
            case DESPESA:
                // Adicionar lógica para verificar se o saldo é suficiente antes de subtrair
                if (usuario.getSaldo().compareTo(valor) < 0) {
                    throw new RuntimeException("Saldo insuficiente!");
                }
                usuario.setSaldo(usuario.getSaldo().subtract(valor));
                break;
        }
        
        // 5. Salva o usuário com o saldo atualizado.
        usuarioRepository.save(usuario);

        // 6. Prepara e salva a nova transação.
        dadosDaTransacao.setUsuario(usuario);
        dadosDaTransacao.setCategoria(categoria);
        dadosDaTransacao.setData(LocalDateTime.now()); // Garante que a data é a do servidor

        return transacaoRepository.save(dadosDaTransacao);
    }

}
