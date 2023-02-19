package com.project.mypfinance.service;

import com.project.mypfinance.entities.StockPosition;
import com.project.mypfinance.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    private final StockRepository stockRepo;
    private final UserService userService;

    @Override
    public HashMap<String, Object> getPortfolioPositions(Pageable pageable) {
        Page<?> transactions;
        String username = userService.getUsernameByAuthentication();
        HashMap<String, Object> result = new LinkedHashMap<>();

        transactions = stockRepo.getAllUserStockPositions(pageable, username);

        if(transactions.isEmpty())
            throw new ResponseStatusException(NOT_FOUND, "No positions found in the DB.");

        result.put("username", username);
        result.put("totalPositions", transactions.getTotalElements());
        result.put("positions", transactions.getContent());
        result.put("totalPages", transactions.getTotalPages());

        return result;
    }

    @Transactional
    @Override
    public void saveStockPositionToDB(StockPosition stockPosition)  {

        stockRepo.save(stockPosition);
        log.info("Successfully saved the stock position.");
    }
}
