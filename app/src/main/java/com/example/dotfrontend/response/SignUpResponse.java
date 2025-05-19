package com.example.dotfrontend.response;

import com.example.dotfrontend.extras.AccountType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter


public class SignUpResponse {

    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private AccountType accountType;
    private String address;
    private String accountNo;


}
