package com.tdd.katas.microservices.vehicleservice.model;

public class VehicleData {
    private String customerId;

    public VehicleData(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
