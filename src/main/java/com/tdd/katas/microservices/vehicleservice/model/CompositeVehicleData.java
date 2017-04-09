package com.tdd.katas.microservices.vehicleservice.model;

import java.util.Arrays;

public class CompositeVehicleData {
    private CustomerData customerData;
    private CarData carData;
    private PartData[] partDataList;

    public CompositeVehicleData(CustomerData customerData, CarData carData, PartData[] partDataList) {
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

    public PartData[] getPartDataList() {
        return partDataList;
    }

    public void setPartDataList(PartData[] partDataList) {
        this.partDataList = partDataList;
    }

    @Override
    public boolean equals(Object obj) {

        return
                obj != null
                && obj instanceof CompositeVehicleData

                && this.customerData!= null
                && this.carData!= null
                && this.partDataList!= null

                && this.customerData.equals(((CompositeVehicleData) obj).getCustomerData())
                && this.carData.equals(((CompositeVehicleData) obj).getCarData())
                &&
                (
                    partDataList.length == ((CompositeVehicleData) obj).getPartDataList().length
                    &&  Arrays.asList(partDataList).containsAll(Arrays.asList(((CompositeVehicleData) obj).getPartDataList()))
                    && Arrays.asList(((CompositeVehicleData) obj).getPartDataList()).containsAll(Arrays.asList(partDataList))
                );

    }

}
