package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private AccountType accountType;

}

