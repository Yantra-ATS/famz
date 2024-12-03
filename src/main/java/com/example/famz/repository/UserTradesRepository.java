package com.example.famz.repository;

import com.example.famz.entity.UserTrades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTradesRepository extends JpaRepository<UserTrades, Long> {
    List<UserTrades> findByCognitoUserIdOrderByLastTradeDate(String cognitoUserId);
}
