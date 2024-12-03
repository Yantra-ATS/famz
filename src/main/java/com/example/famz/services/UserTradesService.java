package com.example.famz.services;

import com.example.famz.DTO.CreateTradeRequest;
import com.example.famz.entity.UserTrades;
import com.example.famz.repository.UserTradesRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserTradesService {

    private final UserTradesRepository repository;

    public UserTradesService(UserTradesRepository repository) {
        this.repository = repository;
    }

    public List<UserTrades> getUserTrades() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String sub = jwt.getClaimAsString("sub");
        return repository.findByCognitoUserIdOrderByLastTradeDate(sub);
    }



    public UserTrades createUserTrades(CreateTradeRequest createTradeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String sub = jwt.getClaimAsString("sub");

        UserTrades userTrades = new UserTrades();
        userTrades.setCognitoUserId(sub);
        userTrades.setTradeCount(createTradeRequest.getTradeCount());
        userTrades.setLastTradeDate(LocalDateTime.now());
        userTrades.setCreatedAt(LocalDateTime.now());
        return repository.save(userTrades);
    }

    public boolean deleteTrade(Long tradeId) {
        Optional<UserTrades> trade = repository.findById(tradeId);

        if (trade.isPresent()) {
            // If the trade is found, delete it
            repository.delete(trade.get());
            return true;
        } else {
            // If the trade is not found, return false
            return false;
        }
    }
}
