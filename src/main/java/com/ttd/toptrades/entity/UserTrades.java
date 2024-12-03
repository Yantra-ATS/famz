package com.ttd.toptrades.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_trades")
@Data // Lombok annotation to generate getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class UserTrades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cognito_user_id", nullable = false)
    private String cognitoUserId;

    @Column(name = "trade_count", nullable = false)
    private Integer tradeCount;

    @Column(name = "last_trade_date")
    private LocalDateTime lastTradeDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
