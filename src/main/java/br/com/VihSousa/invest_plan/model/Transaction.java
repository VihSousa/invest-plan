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
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING) // // Saves the enum name as text ("RECEITA"), not the default number (0, 1...)
    @Column(nullable = false)
    private TransactionType type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    // ============================== RELATIONSHIPS ==============================

    @ManyToOne // Many transactions to ONE user.
    @JoinColumn(name = "user_id", nullable = false) // Defines the foreign key column as 'user_id'
    private User user;

    @ManyToOne // Many transactions to ONE category.
    @JoinColumn(name = "category_id", nullable = false) // Defines the foreign key column as 'category_id'
    private Category category;

}
