package org.example.spring.services;

import org.example.spring.models.main.Coin;
import org.example.spring.models.main.Coins_rates;
import org.example.spring.repositories.main.CoinRepository;
import org.example.spring.repositories.main.CoinsRatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    @Autowired
    CoinRepository coinRepository;
    @Autowired
    CoinsRatesRepository coinsRatesRepository;
    public Coin getCoinById(Long coinId) {
        return coinRepository.findById(coinId).orElse(null);
    }

    public Coins_rates getCoinRateByCoinId(Long coinId) {
        return coinsRatesRepository.findByCoinId(coinId).orElse(null);
    }
}
