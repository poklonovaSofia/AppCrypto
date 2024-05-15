package org.example.spring.repositories.main;

import org.example.spring.models.main.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<Savings, Long> {
    Savings findByUserIdAndCoinId(Long userId, Long coinId);
}
