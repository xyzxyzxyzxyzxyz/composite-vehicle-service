package com.tdd.katas.microservices.vehicleservice.model;

import java.util.List;

public class CompositeVehicleData {
    private CustomerData customerData;
    private CarData carData;
    private List<PartData> partDataList;

    public CompositeVehicleData(CustomerData customerData, CarData carData, List<PartData> partDataList) {
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
                    partDataList.size() == ((CompositeVehicleData) obj).getPartDataList().size()
                    && partDataList.containsAll(((CompositeVehicleData) obj).getPartDataList())
                    && ((CompositeVehicleData) obj).getPartDataList().containsAll(partDataList)
                );

    }

}
