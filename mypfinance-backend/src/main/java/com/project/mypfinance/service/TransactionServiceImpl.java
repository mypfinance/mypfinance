package com.project.mypfinance.service;

import com.project.mypfinance.entities.*;
import com.project.mypfinance.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
@Service
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepo;
    private final ExpenseCategoryRepository expenseCategoryRepo;


    @Autowired
    public TransactionServiceImpl(@Lazy UserRepository userRepo, ExpenseCategoryRepository expenseCategoryRepo) {
        this.userRepo = userRepo;
        this.expenseCategoryRepo = expenseCategoryRepo;

    }

    @Override
    public void saveCategoryToDB(Optional<?> category, String type) {
        if(type.equals("expense"))
            expenseCategoryRepo.save((ExpenseCategory) category.get());
        else
        log.info("Category has been saved successfully!");
    }


    @Override
    public Optional<?> getCategory(String type, Long categoryId) {
        Optional<?> category = null;

        if(type.equals("expense"))
            category = expenseCategoryRepo.findExpenseCategoryByExpenseCategoryIdAndUser(categoryId, getUser());

        if(category.isEmpty()) {
            log.error("Category: " + categoryId + " doesn't exist in the DB!");
            throw new ResponseStatusException(NOT_FOUND, "Category with this id doesn't exist.");
        }
        return category;
    }

    @Override
    public Optional<?> getCategoryByName(String type, String categoryName) {
        Optional<?> category = null;

        if(type.equals("expense"))
            category = expenseCategoryRepo.findExpenseCategoryByCategoryNameAndUser(categoryName, getUser());

        if(category.isEmpty()) {
            log.error("Category: " + categoryName + " doesn't exist in the DB!");
            throw new ResponseStatusException(NOT_FOUND, "Category with this id doesn't exist.");
        }
        return category;
    }

    @Override
    public HashMap<String, Object> getCategories(String type) throws ResponseStatusException{
        Set<?> categories = null;
        User user = getUser();

        if(type.equals("expense"))
            categories = expenseCategoryRepo.findExpenseCategoriesByUser(user);


        if(categories.size() == 0) {
            log.error("There are no registered categories.");
            throw new ResponseStatusException(NOT_FOUND, "There are no registered categories.");
        }

        HashMap<String, Object> result = new LinkedHashMap<>();
        result.put("username", getUsernameByAuthentication());
        result.put("totalCategories", categories);

        return result;
    }

    @Override
    public HashMap<String, Object> getTransactionByDate(Pageable pageable, String date, String type) {
        LocalDate dateTime = LocalDate.parse(date);
        String username = getUsernameByAuthentication();
        Page<?> transactions = null;


        if(transactions.isEmpty()) {
            log.error("There are no registered transactions on this date for the currently logged-in user.");
            throw new ResponseStatusException(NOT_FOUND, "No transactions found on " + date);
        }

        HashMap<String, Object> result = new LinkedHashMap<>();
        result.put("username", username);
        result.put("date", date);
        result.put("totalTransactions", transactions.getTotalElements());
        result.put("totalPages", transactions.getTotalPages());
        result.put("transactions", transactions.getContent());

        return result;
    }


    public HashMap<String, Object> getTransactionByCurrentYear(String type) {
        String username = getUsernameByAuthentication();
        int currentYear = LocalDate.now().getYear();
        HashMap<String, Object> result = new LinkedHashMap<>();
        result.put("username", username);
        result.put("currentYear", currentYear);

        return result;
    }


    @Override
    public void addCategory(String categoryName, String type) {
        String dbName = categoryName.toLowerCase();
        User user = getUser();
        addCategoryValidation(dbName, user, type);

        if(type.equals("expense")) {
            ExpenseCategory expenseCategory = new ExpenseCategory(dbName, user);
            expenseCategoryRepo.save(expenseCategory);
        }
        log.info("Category has been added to the user successfully!");
    }

    @Override
    public void addCategoryWithColor(String categoryName, String type, String color) {
        String dbName = categoryName.toLowerCase();
        User user = getUser();
        addCategoryValidation(dbName, user, type);

        if(type.equals("expense")) {
            ExpenseCategory expenseCategory = new ExpenseCategory(dbName, color, user);
            expenseCategoryRepo.save(expenseCategory);
        }
        log.info("Category has been added to the user successfully!");
    }

    @Override
    public void addTransaction(String categoryType, String date,
                               Double transactionAmount, String categoryName,
                               String description) {
        LocalDate curDate;
        User user = getUser();
        try{
            curDate = LocalDate.parse(date);
            if(categoryType.equals("expense")){
                Optional<ExpenseCategory> category = expenseCategoryRepo
                        .findExpenseCategoryByCategoryNameAndUser(categoryName, user);

                if(category.isEmpty()){
                    ExpenseCategory newCategory = new ExpenseCategory(categoryName, user);
                    expenseCategoryRepo.save(newCategory);
                }

            }
            log.info("Transaction has been added to the currently logged-in user successfully!");
        } catch (DateTimeException dte){
            log.error("The date is in incorrect format. PLease provide it in the following one: YYYY-MM-DD");
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Please, provide a correct format for the date of the transaction.(YYYY-MM-DD)");
        }
    }


    @Override
    public boolean categoryExists(String type, Long categoryId) {

        return expenseCategoryRepo.existsExpenseCategoryByExpenseCategoryIdAndUser(categoryId, getUser());

    }

    @Override
    public boolean categoryExistsByName(String type, String categoryName) {

        return expenseCategoryRepo.existsExpenseCategoryByCategoryNameAndUser(categoryName, getUser());
    }


    @Override
    public  void deleteTransactionsByCategory(String type, String categoryName) {
        User user = getUser();
        if(type.equals("expense")){
            Optional<ExpenseCategory> category = expenseCategoryRepo
                    .findExpenseCategoryByCategoryNameAndUser(categoryName.toLowerCase(), user);
            if(category.isEmpty())
                throw new ResponseStatusException(NOT_FOUND, "Category with this name doesn't exist.");

        }
        log.info("Transaction with category : " + categoryName + ", has been deleted successfully!");
    }

    @Override
    public void deleteCategory(Long categoryId, String type) {
        User user = getUser();
        if(type.equals("expense")){
            expenseCategoryRepo.deleteExpenseCategoryByUserAndExpenseCategoryId(
                    user, categoryId);
        }
        log.info("Category has been deleted successfully!");
    }

    private String getUsernameByAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Optional<User> getOptionalUser() {
        Optional<User> user = userRepo.findUserByUsername(getUsernameByAuthentication());

        if (user.isPresent()) {
            log.info("User with username: " + user.get().getUsername() + " exists in the DB!");
            return user;
        }
        else {
            log.error("User doesn't exist in the DB!");
            throw new ResponseStatusException(BAD_REQUEST, "Sorry, something went wrong.");
        }
    }

    private User getUser(){
        return getOptionalUser().get();
    }

    private void addCategoryValidation(String dbName, User user, String type){
        Optional<?> category;
        if(type.equals("expense")) {
            category = expenseCategoryRepo.findExpenseCategoryByCategoryNameAndUser(dbName, getUser());
        }
     else{
            throw new ResponseStatusException(BAD_REQUEST, "Please enter a valid category type. Either income/expense.");
        }

        if(category.isPresent())
            throw new ResponseStatusException(BAD_REQUEST, "Category with name: " + dbName.toUpperCase() + ", already exists.");
    }
}
