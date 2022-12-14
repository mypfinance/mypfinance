package com.project.mypfinance.repository;

import com.project.mypfinance.entities.IncomeTransaction;
import com.project.mypfinance.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IncomeTransactionRepository extends JpaRepository<IncomeTransaction, Long> {

    @Query("SELECT i "
            + "FROM IncomeTransaction i "
            + "WHERE i.categoryName = ?1")
    List<IncomeTransaction> fetchTransactionsByCategory(String name);

    @Query("SELECT i "
            + "FROM IncomeTransaction i")
    Page<IncomeTransaction> filteredTransactions(Pageable pageable);

    @Query("SELECT i "
            + "FROM IncomeTransaction i "
            + "WHERE "
            + "lower(i.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} ")
    Page<IncomeTransaction> filterTransactionsByUsername(Pageable pageable, String username);

    @Query("SELECT i "
            + "FROM IncomeTransaction i "
            + "WHERE "
            + "lower(i.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} ")
    List<IncomeTransaction> getAllTransactionsByUsername( String username);

    @Query("SELECT i "
            + "FROM IncomeTransaction i "
            + "WHERE "
            + "lower(i.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} "
            + "AND "
            + "lower(i.categoryName) "
            + "LIKE :#{#categoryName == null || #categoryName.isEmpty()? '%' : #categoryName + '%'} ")
    Page<IncomeTransaction> filterTransactionsByUsernameAndCategory(Pageable pageable, String username,  String categoryName);

    @Query("SELECT i "
            + "FROM IncomeTransaction i "
            + "WHERE i.user.username = ?1 "
            + "AND i.date = ?2")
    Page<IncomeTransaction> filteredTransactionsByDate(Pageable pageable, String username, LocalDate date);

    @Query(value = "SELECT i FROM IncomeTransaction i WHERE  EXTRACT(YEAR FROM i.date) = ?2 AND i.user.username = ?1")
    List<IncomeTransaction> filterTransactionsByYear(String username, Integer year);

    @Query(value = "SELECT i FROM IncomeTransaction i " +
            "WHERE  EXTRACT(YEAR FROM i.date) = ?2 " +
            "AND  EXTRACT(MONTH FROM i.date) = ?3 " +
            "AND i.user.username = ?1")
    List<IncomeTransaction> filterTransactionsForCurrentMonth(String username, Integer year, Integer month);

    @Query(value = "SELECT i FROM IncomeTransaction i " +
            "WHERE  EXTRACT(YEAR FROM i.date) = ?2 " +
            "AND  EXTRACT(MONTH FROM i.date) = ?3 " +
            "AND i.user.username = ?1 " +
            "AND i.categoryName = ?4")
    List<IncomeTransaction> filterTransactionsByCategoryYearAndMonth(String username, Integer year, Integer month, String categoryName);

    List<IncomeTransaction> findIncomeTransactionsByCategoryName(String categoryName);

    boolean existsIncomeTransactionByCategoryNameAndUser(String categoryName, User user);

    boolean existsIncomeTransactionByUserAndIncomeTransactionId(User user, Long id);

    void deleteIncomeTransactionsByCategoryNameAndUser(String category, User user);

    void deleteIncomeTransactionsByUser(User user);
}
