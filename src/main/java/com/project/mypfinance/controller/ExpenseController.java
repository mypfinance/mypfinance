package com.project.mypfinance.controller;

import com.project.mypfinance.entities.ExpenseCategory;
import com.project.mypfinance.entities.ExpenseTransaction;
import com.project.mypfinance.entities.User;
import com.project.mypfinance.service.UserService;
import com.project.mypfinance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
public class ExpenseController extends ControlHelper {
    private final UserService userService;
    private final TransactionService service;

    @Autowired
    public ExpenseController(@Lazy UserService userService, TransactionService service){
        this.userService = userService;
        this.service = service;
    }

    @GetMapping("/expense/transactions")
    public HashMap<String, Object>  fetchAllExpenseTransactions(@Nullable Integer currentPage, @Nullable Integer perPage) {
        return service.getAllUserTransactions(createPagination(currentPage, perPage, userService.numberOfUsers()),"expense");
    }

    @GetMapping("/expense/categories")
    public HashMap<String, Object> fetchAllUserExpenseCategories() {
        return service.getCategories("expense");
    }

    @GetMapping("/expense/transaction/{id}")
    public Optional<?> fetchTransactionById(@PathVariable Long id) {
        return service.getTransactionById("expense", id);
    }

    @GetMapping("/expense/transactions/date")
    public HashMap<String, Object> fetchTransactionsByDate(@RequestHeader String Authorization, String date, @Nullable Integer currentPage, @Nullable Integer perPage) {
        return service.getTransactionByDate(createPagination(currentPage, perPage, userService.numberOfUsers()), date, "expense");
    }

    @GetMapping("/expense/transactions/year/{year}")
    public HashMap<String, Object> fetchTransactionsByYear(@PathVariable Integer year) {
        return service.getTransactionByYear(year, "expense");
    }

    @GetMapping("/expense/transactions/current/{year}/{month}")
    public HashMap<String, Object> fetchTransactionsByYear(@PathVariable Integer year, @PathVariable Integer month) {
        return service.getTransactionForCurrentMonth(year, month, "expense");
    }

    @GetMapping("/expense/transactions/current/year")
    public HashMap<String, Object> fetchTransactionsByCurrentYear() {
        return service.getTransactionByCurrentYear("expense");
    }

    @GetMapping("/expense/transactions/year/{year}/{month}")
    public HashMap<String, Object> fetchTransactionsByYearMonthAndCategory(@PathVariable Integer year,
                                                                           @PathVariable Integer month) {
        return service.getTransactionByYearMonthAndCategory(year, month, "expense");
    }

    @GetMapping("/expense/transactions/category")
    public HashMap<String, Object> fetchTransactionsByCategory(@RequestParam String categoryName, @Nullable Integer currentPage, @Nullable Integer perPage) {
        return service.getTransactionsByCategoryAndUsername(createPagination(currentPage, perPage, userService.numberOfUsers()),"expense", categoryName);
    }

    @PostMapping(value = "/add/expense/category", consumes = "application/json")
    public ResponseEntity<String> addExpenseCategory(@RequestBody ExpenseCategory category) {
        if(category.getColor() != null){
            service.addCategoryWithColor(category.getCategoryName(), "expense", category.getColor());
        } else {
            service.addCategory(category.getCategoryName(), "expense");
        }
        return ResponseEntity.ok("Expense category has been saved successfully!");
    }

    @PostMapping(value = "/add/expense/transaction", consumes = "application/json")
    public ResponseEntity<String> addExpenseTransaction(@RequestBody ExpenseTransaction transaction) {
        service.addTransaction("expense",transaction.getDate().toString(), transaction.getExpenseAmount(), transaction.getCategoryName().toLowerCase(), transaction.getDescription());
        return ResponseEntity.ok().body("Transaction added successfully!");
    }

    @PutMapping("/modify/expense/category/{categoryId}")
    public ResponseEntity<?> modifyExpenseCategory(@PathVariable Long categoryId, @RequestBody ExpenseCategory modifiedCategory) {
        if(!service.categoryExists("expense", categoryId)) {
            throw new ResponseStatusException(NOT_FOUND, "Expense category with this name doesn't exist in the DB.");
        }

        if(modifiedCategory.getExpenseCategoryId() != null) {
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Don't provide an id for the new category, because you cannot modify it.");
        }

        Optional<ExpenseCategory> category = ((Optional<ExpenseCategory>)service.getCategory("expense", categoryId));


        return category.map(c -> {
            modifiedCategory.setCategoryName(modifiedCategory.getCategoryName() == null ? c.getCategoryName() : modifiedCategory.getCategoryName().toLowerCase());
            modifiedCategory.setUser(c.getUser());
            modifiedCategory.setColor(c.getColor() == null ? c.getColor() : modifiedCategory.getColor());

            service.deleteCategory(categoryId, "expense");
            service.saveCategoryToDB(Optional.of(modifiedCategory), "expense");

            return ResponseEntity.ok().body(modifiedCategory);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/modify/expense/transaction/{transactionId}")
    public ResponseEntity<?> modifyExpenseTransaction(@PathVariable Long transactionId, @RequestBody ExpenseTransaction modifiedTransaction){
        Optional<ExpenseTransaction> transaction = ((Optional<ExpenseTransaction>)service.getTransactionById("expense", transactionId));

        if(transaction.isEmpty())
            throw new ResponseStatusException(NOT_FOUND,"There is no transaction with id: " + transactionId);

        if(modifiedTransaction.getExpenseTransactionId() != null)
            throw new ResponseStatusException(NOT_ACCEPTABLE, "Don't provide an id for the new transaction, because you cannot modify it.");

        if (modifiedTransaction.getCategoryName() != null) {
            if (service.categoryExistsByName("expense", modifiedTransaction.getCategoryName())) {
                modifiedTransaction.setExpenseCategory((ExpenseCategory) service
                        .getCategoryByName("expense", modifiedTransaction.getCategoryName()).get());
            } else {
                Optional<ExpenseCategory> newCategory = Optional.of(
                        new ExpenseCategory(modifiedTransaction.getCategoryName(), userService.getUser()));
                service.saveCategoryToDB(newCategory, "expense");
                modifiedTransaction.setExpenseCategory(newCategory.get());
            }
        }

        return transaction.map(t -> {
            modifiedTransaction.setCategoryName(modifiedTransaction.getCategoryName() == null ? t.getCategoryName().toLowerCase() : modifiedTransaction.getCategoryName().toLowerCase());
            modifiedTransaction.setDate(modifiedTransaction.getDate() == null ? t.getDate() : modifiedTransaction.getDate());
            modifiedTransaction.setDescription(modifiedTransaction.getDescription() == null ? t.getDescription() : modifiedTransaction.getDescription());
            modifiedTransaction.setUser(t.getUser());

            setBudgetOfUser(t,modifiedTransaction);

            service.deleteTransactionById("expense", transactionId);
            service.saveTransactionToDB(Optional.of(modifiedTransaction),"expense");
            return ResponseEntity.ok().body(modifiedTransaction);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/expense/category/{categoryId}")
    public ResponseEntity<String> deleteExpenseCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId, "expense");
        return ResponseEntity.ok().body("Expense category has been deleted successfully!");
    }

    @DeleteMapping("/delete/expense/transactions/category")
    public ResponseEntity<String> deleteAllUserExpenseTransactionsByCategory(String categoryName) {
        service.deleteTransactionsByCategory("expense", categoryName);
        return ResponseEntity.ok().body("All transactions in category - " + categoryName + " have been deleted successfully!");
    }

    @DeleteMapping("/delete/expense/transactions")
    public ResponseEntity<String> deleteAllUserExpenseTransactions() {
        service.deleteAllUserTransactions("expense");
        return ResponseEntity.ok().body("All expense transactions have been deleted successfully!");
    }

    @DeleteMapping("/delete/expense/transaction/{id}")
    ResponseEntity<String> deleteExpenseTransactionById(@PathVariable Long id) {
        service.deleteTransactionById("expense", id);
        return ResponseEntity.ok().body("The transaction has been deleted successfully!");
    }

    private void setBudgetOfUser(ExpenseTransaction transaction, ExpenseTransaction modTransaction){
        if(modTransaction.getExpenseAmount() != null) {
            Double change = modTransaction.getExpenseAmount() - transaction.getExpenseAmount();
            User user = transaction.getUser();
            user.setCurrentBudget(user.getCurrentBudget() - change);
        } else{
            modTransaction.setExpenseAmount(transaction.getExpenseAmount());
        }
    }
}
