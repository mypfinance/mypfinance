package com.project.mypfinance.repository;

import com.project.mypfinance.entities.ExpenseTransaction;
import com.project.mypfinance.entities.StockPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<StockPosition, Long> {

    @Query("SELECT s "
            + "FROM StockPosition s "
            + "WHERE "
            + "lower(s.user.username)"
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} ")
    Page<ExpenseTransaction> getAllUserStockPositions(Pageable pageable, String username);
}
