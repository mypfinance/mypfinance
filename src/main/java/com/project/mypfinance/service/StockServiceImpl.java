package com.project.mypfinance.service;

import com.project.mypfinance.entities.StockPosition;
import com.project.mypfinance.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepo;

    @Transactional
    @Override
    public void saveStockPositionToDB(StockPosition stockPosition)  {

        stockRepo.save(stockPosition);
        log.info("Successfully saved the stock position.");
    }
}
