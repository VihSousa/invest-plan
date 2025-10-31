package br.com.VihSousa.invest_plan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.VihSousa.invest_plan.model.Transacao;

// Define a interface
// Deve Salvar, Buscar, Atualizar e Deletar Transações APENAS
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Cria uma query "SELECT * FROM transacoes WHERE usuario_id = ?"
    List<Transacao> findByUsuarioId(Long usuarioId);
}
