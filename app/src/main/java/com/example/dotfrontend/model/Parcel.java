package com.example.dotfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Parcel{

    private long parcelId;
    private String type;
    private float weight;
    private Location originId;
    private Location destinationId;
    String address;
    String sendAddress;
    private Customer customerId;
    private Batch batch;


    public Parcel(String type, float weight, Location origin, Location destination, Customer customer, Object o, String address, String sendAddress, Batch batch) {

        this.type = type;
        this.weight = weight;
        this.destinationId = destination;
        this.customerId = customer;
        this.address = address;
        this.originId = origin;
        this.sendAddress = sendAddress;
        this.batch = batch;
    }
}