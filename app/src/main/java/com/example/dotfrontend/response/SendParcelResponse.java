package com.example.dotfrontend.response;

public class SendParcelResponse {
    private Long customerId;
    private String type;
    private Double weight;
    private String originCountry;
    private String originCity;
    private String destinationCountry;
    private String destinationCity;
    private String placementDate;  // "YYYY-MM-DD"
    private long paymentId;

    public SendParcelResponse() {}

    public SendParcelResponse(Long customerId,
                              String type,
                              Double weight,
                              String originCountry,
                              String originCity,
                              String destinationCountry,
                              String destinationCity,
                              String placementDate) {
        this.customerId = customerId;
        this.type = type;
        this.weight = weight;
        this.originCountry = originCountry;
        this.originCity = originCity;
        this.destinationCountry = destinationCountry;
        this.destinationCity = destinationCity;
        this.placementDate = placementDate;
    }

    // getters & setters

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getOriginCountry() {
        return originCountry;
    }
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginCity() {
        return originCity;
    }
    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getPlacementDate() {
        return placementDate;
    }
    public void setPlacementDate(String placementDate) {
        this.placementDate = placementDate;
    }
}
