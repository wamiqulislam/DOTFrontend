package com.example.dotfrontend.response;

import com.example.dotfrontend.extras.VehicleStatus;
import com.example.dotfrontend.extras.VehicleType;

public class RegisterVehicleResponse {
    private Long riderId;

    private VehicleType vehicleType; // "Truck", "Ship", "Airplane"
    private String model;
    private String licenseNumber;

    private VehicleStatus status;

    // Optional fields depending on vehicle type
    private Double truckCapacity;
    private Double cargoCapacity;
    private Double maxAltitude;  // truckCapacity, cargoCapacity or maxAltitude

    public RegisterVehicleResponse() {
    }

    public RegisterVehicleResponse(Long riderId,
                                   VehicleType vehicleType,
                                   String model,
                                   String licenseNumber,
                                   VehicleStatus status,
                                   Double capacity) {
        this.riderId = riderId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.status = status;
        this.truckCapacity = capacity;
        this.cargoCapacity = capacity;
        this.maxAltitude = capacity;
    }

}