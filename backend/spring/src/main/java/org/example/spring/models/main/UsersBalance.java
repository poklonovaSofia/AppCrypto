package org.example.spring.models.main;

import jakarta.persistence.*;
import lombok.Data;
import org.example.spring.models.auth.User;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "users_balance")
public class UsersBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "coin_id", referencedColumnName = "id", nullable = false)
    private Coin coin;

    @Column(precision = 19, scale = 4, nullable = false)
    private BigDecimal balance;
}
