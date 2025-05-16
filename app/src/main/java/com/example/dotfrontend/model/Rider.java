package com.example.dotfrontend.model;

import com.example.dotfrontend.model.User;
import com.example.dotfrontend.model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rider extends User {

    private Vehicle vehicleId;
    private int accoundNo;
    private float dueAmount = 0;

}
