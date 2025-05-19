package com.example.dotfrontend.response;

import com.example.dotfrontend.extras.ParcelStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendParcelResponse {
    private long customerId;
    private String destinationCity;
    private String destinationCountry;
    private String address;
    private String originCity;
    private String originCountry;
    private String sendAddress;
    private String placementDate;
    private String type;
    private float weight;
}
