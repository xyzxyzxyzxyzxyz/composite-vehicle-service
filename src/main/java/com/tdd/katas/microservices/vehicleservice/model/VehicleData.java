package com.tdd.katas.microservices.vehicleservice.model;

import java.util.List;

public class VehicleData {
    private CustomerData customerData;
    private CarData carData;
    private List<PartData> partDataList;

    public VehicleData(CustomerData customerData, CarData carData, List<PartData> partDataList) {
        this.customerData = customerData;
        this.carData = carData;
        this.partDataList = partDataList;
    }

    public CustomerData getCustomerData() {
        return customerData;
    }

    public void setCustomerData(CustomerData customerData) {
        this.customerData = customerData;
    }

    public CarData getCarData() {
        return carData;
    }

    public void setCarData(CarData carData) {
        this.carData = carData;
    }

    public List<PartData> getPartDataList() {
        return partDataList;
    }

    public void setPartDataList(List<PartData> partDataList) {
        this.partDataList = partDataList;
    }
}
