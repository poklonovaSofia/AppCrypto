package org.example.spring.repositories.main;

import org.example.spring.models.main.UsersBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UsersBalance, Long> {
    UsersBalance findByUserIdAndCoinId(Long userId, Long coinId);
}
