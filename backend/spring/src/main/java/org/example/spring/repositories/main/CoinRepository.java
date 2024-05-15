package org.example.spring.repositories.main;


import org.example.spring.models.main.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Long> {
}
