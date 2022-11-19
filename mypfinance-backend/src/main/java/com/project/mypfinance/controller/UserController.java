package com.project.mypfinance.controller;

import com.project.mypfinance.entities.Role;
import com.project.mypfinance.entities.User;
import com.project.mypfinance.service.UserService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Lazy UserService userService){
        this.userService = userService;
    }

    /**
     * Shows all users of the expense tracker app.
     * @return all users in the app.
     */
    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo(){
        User user = userService.getUser();
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/user/id")
    public User getUserById(Long id){
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(@Nullable Integer currentPage, @Nullable Integer perPage){

        Pageable pageable = createPagination(currentPage, perPage, userService.numberOfUsers());

        Page<User> users = userService.getUsers(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("totalUsers", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());
        response.put("users", users.getContent());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/user/save/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/user/add/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody String roleName){
        userService.addRoleToUser(roleName);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/modify")
    public Optional<User> modifyUserInfo(@RequestBody User updatedUser) {
        if (!userService.usernameExists()) {
            throw new ResponseStatusException(NOT_FOUND, "User with this username doesn't exists.");
        }

        return userService.getOptionalUser()
                .map(user -> {
                    if(updatedUser.getUsername() != null && !Objects.equals(updatedUser.getUsername(), user.getUsername())) {
                        throw new ResponseStatusException(NOT_ACCEPTABLE, "You aren't allowed to change your username!");
                    }
                    user.setFirstName(updatedUser.getFirstName() == null ? user.getFirstName() : updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName() == null ? user.getLastName() : updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail() == null ? user.getEmail() : updatedUser.getEmail());
                    user.setCurrentBudget(updatedUser.getCurrentBudget() == null ? user.getCurrentBudget() : updatedUser.getCurrentBudget());
                    userService.clearSave(user);
                    return user;
                });
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User was deleted successfully!");
    }

    private static Pageable createPagination(Integer currentPage, Integer perPage, int size) {
        Pageable pageable;
        if((currentPage != null && perPage != null) && (currentPage > 0 && perPage > 0)){
            pageable = PageRequest.of(currentPage - 1, perPage);
        } else if (currentPage == null && perPage == null){
            pageable = PageRequest.of(0, size);
        } else {
            throw new ResponseStatusException(BAD_REQUEST, "The value of currentPage and/or perPage parameters cannot be under or equal to 0.");
        }
        return pageable;
    }
}
