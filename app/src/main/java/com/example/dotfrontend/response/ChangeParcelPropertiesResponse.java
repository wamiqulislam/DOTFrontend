package com.example.dotfrontend.response;

public class ChangeParcelPropertiesResponse {
    private Long parcelId;
    private String status;        // for changeStatus
    private String location;      // for changeLocation
    private Long riderId;         // for changeCurrentRider
    private String deliveredDate; // for changeDeliveredDate (YYYY-MM-DD)

    public ChangeParcelPropertiesResponse() {}

    // You can also add an all-args constructor if you like.

    // getters & setters

    public Long getParcelId() {
        return parcelId;
    }
    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Long getRiderId() {
        return riderId;
    }
    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }
    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}
