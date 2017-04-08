package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;

public interface VehicleRepository {

    VehicleData getVehicleData(String vin);

}
