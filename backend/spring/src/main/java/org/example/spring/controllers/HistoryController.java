package org.example.spring.controllers;

import org.example.spring.models.main.Savings;
import org.example.spring.requests.AddSavingsRequest;
import org.example.spring.requests.SavingsRequest;
import org.example.spring.requests.UpdateSavingsRequest;
import org.example.spring.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("/start/savings")
    public ResponseEntity<?> addSavings(@RequestBody AddSavingsRequest addSavingsRequest)
    {
        try{
            Savings savings = historyService.addSavings(addSavingsRequest);
            return ResponseEntity.ok(savings);
        }catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");

        }
    }
    @PostMapping("/update/savings")
    public ResponseEntity<?> updateSavings(@RequestBody UpdateSavingsRequest updateSavingsRequest)
    {
        try{
            Long id = historyService.updateSavings(updateSavingsRequest);
           if(id != null)
               return ResponseEntity.ok("success");
           else
               return ResponseEntity.ok("bad");

        }catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");

        }
    }
    @PostMapping("/savings")
    public ResponseEntity<?> getSavings(@RequestBody SavingsRequest savingsRequest)
    {
        try {
            Savings savings = historyService.getSavings(savingsRequest);
            if(savings == null)
                return ResponseEntity.ok("null");
            else
                return ResponseEntity.ok(savings);

        }catch(Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
 }
