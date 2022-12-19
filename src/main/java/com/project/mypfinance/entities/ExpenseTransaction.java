package com.project.mypfinance.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "expense_transaction")
@Getter
@Setter
public class ExpenseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_transaction_id", insertable = false, updatable = false)
    private Long expenseTransactionId;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "expense_amount")
    private Double expenseAmount;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "expense_category_transaction_id", referencedColumnName = "expense_category_id")
    @JsonIgnore
    private ExpenseCategory expenseCategory;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "user_expense_transaction_id", referencedColumnName = "user_id")
    @JsonIgnore
    private User user;

    public ExpenseTransaction() {
    }

    public ExpenseTransaction(Long expenseTransactionId, LocalDate date,
                              Double expenseAmount, String categoryName,
                              String description, User user, ExpenseCategory expenseCategory) {
        this.expenseTransactionId = expenseTransactionId;
        this.date = date;
        this.expenseAmount = expenseAmount;
        this.categoryName = categoryName;
        this.description = description;
        this.user = user;
        this.expenseCategory = expenseCategory;
    }

    public ExpenseTransaction(Double expenseAmount, String categoryName,
                              String description, User user) {
        this.expenseAmount = expenseAmount;
        this.categoryName = categoryName;
        this.description = description;
        this.user = user;
    }

    public ExpenseTransaction(LocalDate date, Double expenseAmount, String categoryName, String description, User user) {
        this.date = date;
        this.expenseAmount = expenseAmount;
        this.categoryName = categoryName;
        this.description = description;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseTransaction that = (ExpenseTransaction) o;
        return Objects.equals(expenseTransactionId, that.expenseTransactionId) && Objects.equals(date, that.date) && Objects.equals(expenseAmount, that.expenseAmount) && Objects.equals(categoryName, that.categoryName) && Objects.equals(description, that.description) && Objects.equals(expenseCategory, that.expenseCategory) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseTransactionId, date, expenseAmount, categoryName, description, expenseCategory, user);
    }
}
