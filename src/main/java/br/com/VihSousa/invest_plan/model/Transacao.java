package br.com.VihSousa.invest_plan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;  
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;

import lombok.Data;

@Data
@Entity
@Table(name = "transacoes")
public class Transacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING) // Salva o texto do enum ("RECEITA") no banco, não o número.
    @Column(nullable = false)
    private TipoTransacao tipo;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime data;

    // --- RELACIONAMENTOS ---

    @ManyToOne // Muitas transações para UM usuário.
    @JoinColumn(name = "usuario_id", nullable = false) // Cria a coluna de chave estrangeira 'usuario_id'
    private Usuario usuario;

    @ManyToOne // Muitas transações para UMA categoria.
    @JoinColumn(name = "categoria_id", nullable = false) // Cria a coluna de chave estrangeira 'categoria_id'
    private Categoria categoria;

}
