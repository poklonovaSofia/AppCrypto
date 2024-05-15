package org.example.spring.services;

import org.example.spring.models.auth.User;
import org.example.spring.models.main.Coin;
import org.example.spring.models.main.Savings;
import org.example.spring.repositories.main.HistoryRepository;
import org.example.spring.requests.AddSavingsRequest;
import org.example.spring.requests.SavingsRequest;
import org.example.spring.requests.UpdateSavingsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    public Savings getSavings(SavingsRequest savingsRequest) {
        return historyRepository.findByUserIdAndCoinId(savingsRequest.getUser_id(), savingsRequest.getCoin_id());
    }

    public Long addSavings(AddSavingsRequest addSavingsRequest) {
        Savings savings = new Savings();
        Coin coin = new Coin();
        coin.setId(addSavingsRequest.getCoin_id());
        savings.setCoin(coin);
        User user =new User();
        user.setId(addSavingsRequest.getUser_id());
        savings.setUser(user);
        savings.setDuration(addSavingsRequest.getDuration());
        savings.setTempBalance(addSavingsRequest.getTempBalance());
        savings.setCreatedAt(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(savings.getCreatedAt());
        calendar.add(Calendar.HOUR_OF_DAY, addSavingsRequest.getDuration());
        savings.setEnd_at(calendar.getTime());
        historyRepository.save(savings);
        return savings.getId();
    }

    public Long updateSavings(UpdateSavingsRequest updateSavingsRequest) {
        Long savingsId = updateSavingsRequest.getId();
        BigDecimal newBalance = updateSavingsRequest.getTempBalance();

        Savings savings = historyRepository.findById(savingsId).orElse(null);
        if (savings != null) {
            savings.setTempBalance(newBalance);
            historyRepository.save(savings);

            return savingsId;
        } else {
            return null;
        }
    }
}
