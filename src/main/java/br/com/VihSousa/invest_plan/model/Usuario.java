package br.com.VihSousa.invest_plan.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;

import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
@SQLDelete(sql = "UPDATE usuarios SET deletado_em = NOW() WHERE id = ?") // Troca o comando DELETE por um UPDATE
@SQLRestriction("deletado_em IS NULL") // Forma nova de fazer o mesmo que o @Where
// @Where(clause = "deletado_em IS NULL") - Forma antiga do mesmo método
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime CriadoEm;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime AtualizadoEm;

    @Column (name = "deletado_em")
    @JsonIgnore // Evitar que esse campo seja exposto em APIs
    private LocalDateTime deletadoEm;

    @JsonIgnore // Evitar loops
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Transacao> transacoes; // Uma lista de transações associadas ao usuário


}
