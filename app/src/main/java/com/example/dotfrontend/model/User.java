package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class User {
    private Long userId;           // optional — will be set by backend
    private String name;
    private String email;
    private String phone;
    private String password;

    // then change your field to:
    private AccountType accountType;    // "Customer" or "Rider"

    // Empty constructor required for Retrofit/serialization


    // Full constructor
    public User(String name, String email, String phone, String password, AccountType accountType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.accountType = accountType;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}

