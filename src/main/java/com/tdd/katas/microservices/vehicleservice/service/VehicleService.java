package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;

public interface VehicleService {
    VehicleData getVehicleData(String vin);
}
