package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.ParcelStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Orders {

    long orderid;

    private ParcelStatus status;
    String placementDate;
    private Customer customerId;
    private Payment paymentId;

    public Orders( ParcelStatus parcelStatus, String placementDate, Customer customerId) {
        this.status = parcelStatus;
        this.placementDate = placementDate;
        this.customerId = customerId;
    }
}




