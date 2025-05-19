package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.VehicleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Ship extends Vehicle {
    private double cargoCapacity;

    public Ship(String model, String licenseNumber, VehicleStatus status, Double cargoCapacity) {
        super(model,licenseNumber,status);
        this.cargoCapacity = cargoCapacity;
    }
}
