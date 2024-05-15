package org.example.spring.controllers;

import org.example.spring.models.main.Coin;
import org.example.spring.models.main.Coins_rates;
import org.example.spring.services.CoinService;
import org.example.spring.services.user.auth.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;
    private final JwtUserService authService;

    public CoinController(JwtUserService authService) {
        this.authService = authService;
    }

    @GetMapping("/{coinId}/rate")
    public ResponseEntity<?> getCoinRate(@PathVariable Long coinId) {
        try {
            // Отримати тариф за coinId
            Coins_rates coinRate = coinService.getCoinRateByCoinId(coinId);
            if (coinRate != null) {
                return ResponseEntity.ok(coinRate);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
