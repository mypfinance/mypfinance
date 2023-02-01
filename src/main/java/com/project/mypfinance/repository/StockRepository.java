package com.project.mypfinance.repository;

import com.project.mypfinance.entities.StockPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockPosition, Long> {
}
