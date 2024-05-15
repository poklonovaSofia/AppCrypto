package org.example.spring.services;

import org.example.spring.models.main.UsersBalance;
import org.example.spring.repositories.main.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    public UsersBalance getBalanceForUser(Long userId) {
        UsersBalance usersBalance= userBalanceRepository.findByUserIdAndCoinId(userId, 1L);
        return usersBalance;
    }
}
