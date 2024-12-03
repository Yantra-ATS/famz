package com.example.famz.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTradeRequest {

    private int tradeCount; // The number of trades to create or the count

}
