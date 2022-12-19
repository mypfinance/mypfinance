package com.project.mypfinance.service;

import com.project.mypfinance.entities.User;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
public interface TransactionService {

    void saveCategoryToDB(Optional<?> category, String type);

    void saveTransactionToDB(Optional<?> transaction, String categoryType);

    int numberOfTransactionsByCategory(String type, String categoryName);

    Optional<?> getCategory(String type, Long categoryId);

    Optional<?> getCategoryByName(String type, String categoryName);

    HashMap<String, Object> getCategories(String type);

    Optional<?> getTransactionById(String type, Long transactionId);

    HashMap<String, Object> getTransactionsByCategoryAndUsername(Pageable pageable, String type, String categoryName);

    HashMap<String, Object> getTransactionByDate(Pageable pageable, String date, String type);

    HashMap<String, Object> getTransactionByYear(Integer year, String type);

    HashMap<String, Object> getTransactionForCurrentMonth(Integer year, Integer month, String type);

    HashMap<String, Object> getTransactionByCurrentYear(String type);

    HashMap<String, Object> getTransactionByYearMonthAndCategory(Integer year, Integer month, String type);

    HashMap<String, Object> getAllUserTransactions(Pageable pageable, String type);

    void addCategory(String categoryName, String type);

    void addCategoryWithColor(String categoryName, String type, String color);

    void addTransaction(String categoryType,String date, Double TransactionAmount, String categoryName, String description);

    boolean transactionExists(String type, Long transactionId);

    boolean categoryExists(String type, Long categoryId);

    boolean categoryExistsByName(String type, String categoryName);

    void deleteAllUserTransactions(String type);

    void deleteTransactionById(String type, Long transactionId);

    void deleteTransactionsByCategory(String type, String categoryName);

    void deleteCategory(Long categoryId, String type);
}
