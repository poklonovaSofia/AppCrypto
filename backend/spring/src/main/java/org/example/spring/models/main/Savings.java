package org.example.spring.models.main;

import jakarta.persistence.*;
import lombok.Data;
import org.example.spring.models.auth.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "savings_history")
public class Savings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal tempBalance;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coin_id", referencedColumnName = "id", nullable = false)
    private Coin coin;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_at", nullable = false)
    private Date end_at;

    @Column(name = "duration", nullable = false)
    private int duration;


}
