package com.example.dotfrontend.response;

import com.example.dotfrontend.extras.ParcelStatus;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChangeParcelPropertiesResponse {
    private Long parcelId;
    private ParcelStatus status;        // for changeStatus
    private String location;      // for changeLocation
    private Long riderId;         // for changeCurrentRider
    private String deliveredDate; // for changeDeliveredDate (YYYY-MM-DD)
}
