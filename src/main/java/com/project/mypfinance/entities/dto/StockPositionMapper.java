package com.project.mypfinance.entities.dto;

import com.project.mypfinance.entities.StockPosition;
import org.springframework.stereotype.Component;

@Component
public class StockPositionMapper {

    public static StockPosition toDtoStockPosition(StockPositionRequest domain){

        return StockPosition.builder()
                .stockName(domain.getStockName())
                .stockSymbol(domain.getStockSymbol())
                .stockUnits(domain.getStockUnits())
                .stockPrice(domain.getStockPrice())
                .build();
    }

    public static StockPositionRequest toDomainStockPosition(StockPosition dto){

        return StockPositionRequest.builder()
                .stockName(dto.getStockName())
                .stockSymbol(dto.getStockSymbol())
                .stockUnits(dto.getStockUnits())
                .stockPrice(dto.getStockPrice())
                .build();
    }
}
