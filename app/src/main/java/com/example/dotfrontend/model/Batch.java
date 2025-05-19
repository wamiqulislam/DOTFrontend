package com.example.dotfrontend.model;

import android.os.Parcel;

import com.example.dotfrontend.extras.BatchStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Batch {

    private Long batchId;
    private float weight;
    private Location destination;
    private Location currentLocation;
    private BatchStatus status;
    private Rider rider;

}