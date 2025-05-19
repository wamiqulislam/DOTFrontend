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

public class Airplane extends Vehicle {
    private double maxLoad;

    public Airplane(String model, String licenseNumber, VehicleStatus status, Double cargoCapacity) {
        super(model,licenseNumber,status);
        this.maxLoad = cargoCapacity;
    }
}
