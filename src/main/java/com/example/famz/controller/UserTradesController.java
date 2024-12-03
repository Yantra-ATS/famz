package com.example.famz.controller;

import com.example.famz.DTO.CreateTradeRequest;
import com.example.famz.entity.UserTrades;
import com.example.famz.services.UserTradesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/trades")
public class UserTradesController {

    private final UserTradesService service;

    public UserTradesController(UserTradesService service) {
        this.service = service;
    }

    @GetMapping("/getUserTrades")
    public ResponseEntity<List<UserTrades>> getUserTrades() {
        List<UserTrades> userTrades = service.getUserTrades();
        return ResponseEntity.ok(userTrades);
    }

    @PostMapping("/createTrade")
    public ResponseEntity<UserTrades> createUserTrades(@RequestBody CreateTradeRequest tradeRequest) {
        UserTrades newUserTrades = service.createUserTrades(tradeRequest);
        return ResponseEntity.ok(newUserTrades);
    }

    // DELETE endpoint to remove a trade by tradeId
    @DeleteMapping("/delete/{tradeId}")
    public ResponseEntity<Boolean> deleteTrade(@PathVariable("tradeId") Long tradeId) {
        // Call service to delete the trade
        boolean isDeleted = service.deleteTrade(tradeId);

        // If deletion was successful, return a success message

            return ResponseEntity.ok(isDeleted);

    }
}
