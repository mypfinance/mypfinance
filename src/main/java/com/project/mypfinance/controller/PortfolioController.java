package com.project.mypfinance.controller;

import com.project.mypfinance.entities.dto.StockPositionMapper;
import com.project.mypfinance.entities.dto.StockPositionRequest;
import com.project.mypfinance.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
public class PortfolioController {

    private final StockService stockService;

    private final StockPositionMapper stockPositionMapper;

    @GetMapping("/stock/info")
    public ResponseEntity<?> getCurrentStockInfo(@RequestParam String stockSymbol) throws IOException {

        Stock stock = YahooFinance.get(stockSymbol.toUpperCase());
        log.info("Getting info for th3is stock: " + stock.toString());
        return ResponseEntity.of(Optional.of(stock));
    }

    @PostMapping("/stock")
    public ResponseEntity<?> saveStockPosition(@RequestBody StockPositionRequest stockPositionRequest) throws IOException {

        Stock stock = YahooFinance.get(stockPositionRequest.getStockSymbol().toUpperCase());
        log.info(stock.toString());
        stockService.saveStockPositionToDB(StockPositionMapper.toDtoStockPosition(stockPositionRequest));
        return ResponseEntity.ok("The stock position has been saved successfully!");
    }

}
