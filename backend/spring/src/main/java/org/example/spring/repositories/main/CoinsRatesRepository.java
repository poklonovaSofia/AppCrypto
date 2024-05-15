package org.example.spring.repositories.main;

import org.example.spring.models.main.Coins_rates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinsRatesRepository extends JpaRepository<Coins_rates, Long> {
    Optional<Coins_rates> findByCoinId(Long coinId);
}
