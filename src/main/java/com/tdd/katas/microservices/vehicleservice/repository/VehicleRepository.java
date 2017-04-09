package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;

public interface VehicleRepository {

    void createVehicleData(String vin, VehicleData vehicleData) throws IllegalStateException;

    VehicleData getVehicleData(String vin);

    void deleteAllVehicleData();
}
