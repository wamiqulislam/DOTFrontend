package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.VehicleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter


public class Vehicle {

    private String model;
    private String licenseNumber;
    private VehicleStatus status;

    public Vehicle(String model, String licenseNumber, VehicleStatus status) {
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.status = status;
    }

    public Vehicle() {
    }
}
