package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.PaymentStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long paymentId;
    private double amount;
    private String paymentDate;
    private PaymentStatus paymentStatus;
    private Parcel parcel;

}