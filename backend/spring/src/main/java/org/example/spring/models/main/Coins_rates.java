package org.example.spring.models.main;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "coins_rates")
public class Coins_rates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coin_id", referencedColumnName = "id", nullable = false)
    private Coin coin;
    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal rate;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

}
