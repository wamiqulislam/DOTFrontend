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

public abstract class Vehicle {

    private Long vehicleId;
    private String model;
    private String licenseNumber;
    private VehicleStatus status;
}
