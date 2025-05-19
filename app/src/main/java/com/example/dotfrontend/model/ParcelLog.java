package com.example.dotfrontend.model;

import com.example.dotfrontend.extras.ParcelStatus;
import com.example.dotfrontend.model.Parcel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelLog {

    private Long logId;
    private Parcel parcelId;
    private ParcelStatus status;
    private String placementDate;
    private Location location;
    private String deliveredDate;


}
