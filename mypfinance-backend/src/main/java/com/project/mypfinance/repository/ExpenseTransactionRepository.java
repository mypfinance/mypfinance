package com.project.mypfinance.repository;

import com.project.mypfinance.entities.User;
import com.project.mypfinance.entities.ExpenseCategory;
import com.project.mypfinance.entities.ExpenseTransaction;
import com.project.mypfinance.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
public interface ExpenseTransactionRepository extends JpaRepository<ExpenseTransaction, Long>{@Query("SELECT e "
        + "FROM ExpenseTransaction e")
Page<ExpenseTransaction> filteredTransactions(Pageable pageable);

    @Query("SELECT e "
            + "FROM ExpenseTransaction e "
            + "WHERE "
            + "lower(e.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} ")
    Page<ExpenseTransaction> filterTransactionsByUsername(Pageable pageable, String username);

    @Query("SELECT e "
            + "FROM ExpenseTransaction e "
            + "WHERE "
            + "lower(e.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} ")
    List<ExpenseTransaction> getAllTransactionsByUsername(String username);

    @Query("SELECT e "
            + "FROM ExpenseTransaction e "
            + "WHERE "
            + "lower(e.user.username) "
            + "LIKE :#{#username == null || #username.isEmpty()? '%' : #username + '%'} "
            + "AND "
            + "lower(e.categoryName) "
            + "LIKE :#{#categoryName == null || #categoryName.isEmpty()? '%' : #categoryName + '%'} ")
    Page<ExpenseTransaction> filterTransactionsByUsernameAndCategory(Pageable pageable, String username,  String categoryName);

    @Query("SELECT e "
            + "FROM ExpenseTransaction e "
            + "WHERE e.date = ?2 "
            + "AND e.user.username = ?1")
    Page<ExpenseTransaction> filterTransactionsByDate(Pageable pageable, String username, LocalDate date);

    @Query(value = "SELECT e FROM ExpenseTransaction e WHERE  EXTRACT(YEAR FROM e.date) = ?2 AND e.user.username = ?1")
    List<ExpenseTransaction> filterTransactionsByYear(String username, Integer year);

    @Query(value = "SELECT e FROM ExpenseTransaction e " +
            "WHERE  EXTRACT(YEAR FROM e.date) = ?2 " +
            "AND  EXTRACT(MONTH FROM e.date) = ?3 " +
            "AND e.user.username = ?1")
    List<ExpenseTransaction> filterTransactionsForCurrentMonth(String username, Integer year, Integer month);

    @Query(value = "SELECT e FROM ExpenseTransaction e " +
            "WHERE  EXTRACT(YEAR FROM e.date) = ?2 " +
            "AND  EXTRACT(MONTH FROM e.date) = ?3 " +
            "AND e.user.username = ?1 " +
            "AND e.categoryName = ?4")
    List<ExpenseTransaction> filterTransactionsByCategoryYearAndMonth(String username, Integer year, Integer month, String categoryName);

    List<ExpenseTransaction> findExpenseTransactionsByCategoryName(String categoryName);

    List<ExpenseTransaction> findExpenseTransactionsByCategoryNameAndUser(String categoryName, User user);

    boolean existsExpenseTransactionsByCategoryNameAndUser(String categoryName, User user);

    boolean existsExpenseTransactionByUserAndExpenseTransactionId(User user, Long id);

    void deleteExpenseTransactionsByCategoryNameAndUser(String category, User user);
    void deleteExpenseTransactionsByCategoryNameAndUser_UserId(String category, Long id);

    void deleteExpenseTransactionsByUser(User user);

}
