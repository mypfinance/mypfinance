package com.project.mypfinance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="users")
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "budget")
    private Double currentBudget;

    @ManyToMany(fetch = FetchType.LAZY, cascade =  {PERSIST, MERGE, REFRESH, DETACH})
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")})
    @JsonIgnore
    private Set<Role> roles;

    public User() {
    }

    public User(Long userId, String username, String password, String firstName, String lastName, String email, Double currentBudget) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentBudget = currentBudget;
    }

    public User(String username, String password, String firstName, String lastName, String email, Double currentBudget) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.currentBudget = currentBudget;
    }

    public void addRoleToUser(Role role){
        Set<Role> newRoles = new HashSet<>();
        if(this.roles != null) {
            newRoles.add(role);
            newRoles.addAll(this.roles);
        }
        else
            newRoles.add(role);

        setRoles(newRoles);
    }

}
