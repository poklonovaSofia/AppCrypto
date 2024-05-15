package org.example.spring.controllers;

import org.example.spring.models.main.UsersBalance;
import org.example.spring.services.UserBalanceService;
import org.example.spring.services.user.auth.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserBalanceController {

    @Autowired
    private UserBalanceService userBalanceService;
    private final JwtUserService authService;

    public UserBalanceController(JwtUserService authService) {
        this.authService = authService;
    }

    @GetMapping("/{userId}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        try {

                UsersBalance balance = userBalanceService.getBalanceForUser(userId);
                if (balance != null) {

                    return ResponseEntity.ok(balance);
                } else {

                    return ResponseEntity.ok(0);
                }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
