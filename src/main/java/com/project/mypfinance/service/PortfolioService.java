package com.project.mypfinance.service;

import com.project.mypfinance.entities.StockPosition;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

public interface PortfolioService {

    HashMap<String, Object> getPortfolioPositions(Pageable pageable);

    void saveStockPositionToDB(StockPosition stockPosition);

}
