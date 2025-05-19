package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.VehicleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Truck extends Vehicle {


    private double truckCapacity;

    public Truck(String model, String licenseNumber, VehicleStatus status, Double cargoCapacity) {
        super(model,licenseNumber,status);
        this.truckCapacity = cargoCapacity;
    }
}
