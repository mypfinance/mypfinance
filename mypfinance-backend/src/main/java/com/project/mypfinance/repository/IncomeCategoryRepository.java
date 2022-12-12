package com.project.mypfinance.repository;

import com.project.mypfinance.entities.IncomeCategory;
import com.project.mypfinance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    Set<IncomeCategory> findIncomeCategoriesByUser(User user);

    Optional<IncomeCategory> findIncomeCategoryByIncomeCategoryIdAndUser(Long incomeCategoryId, User user);

    Optional<IncomeCategory> findIncomeCategoryByCategoryNameAndUser(String name, User user);

    boolean existsIncomeCategoryByIncomeCategoryIdAndUser(Long incomeCategoryId, User user);

    boolean existsIncomeCategoryByCategoryNameAndUser(String categoryName, User user);

    void deleteIncomeCategoryByUserAndIncomeCategoryId(User user, Long incomeCategoryId);
}
