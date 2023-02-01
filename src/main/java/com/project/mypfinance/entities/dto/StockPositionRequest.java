package com.project.mypfinance.entities.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockPositionRequest {

    private String stockSymbol;

    private String stockName;

    private BigDecimal stockPrice;

    private Long stockUnits;
}
