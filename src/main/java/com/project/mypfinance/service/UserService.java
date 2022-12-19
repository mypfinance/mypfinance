package com.project.mypfinance.service;

import com.project.mypfinance.entities.Role;
import com.project.mypfinance.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    void clearSave(User user);

    Role saveRole(Role role);

    void addRoleToUser(String roleName);

    User getUser();

    Optional<User> getOptionalUser();

    int numberOfUsers();

    User getUserById(Long userId);

    Page<User> getUsers(Pageable pageable);

    List<User> getAllDBUsers();

    boolean usernameExists();

    void deleteUser();

    void saveUserDataAndFlush(User user);
    String getUsernameByAuthentication();
}
