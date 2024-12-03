package com.ttd.toptrades.repository;

import com.ttd.toptrades.entity.UserTrades;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTradesRepository extends JpaRepository<UserTrades, Long> {
    List<UserTrades> findByCognitoUserIdOrderByLastTradeDate(String cognitoUserId);
}
