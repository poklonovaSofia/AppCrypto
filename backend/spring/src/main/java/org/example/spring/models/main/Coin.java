package org.example.spring.models.main;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "coins")
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

}
