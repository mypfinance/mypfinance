package com.project.mypfinance.service;

import com.project.mypfinance.entities.StockPosition;
import com.project.mypfinance.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface StockService {

    void saveStockPositionToDB(StockPosition stockPosition);

}
