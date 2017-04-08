package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;

public interface VehicleService {
    CompositeVehicleData getVehicleData(String vin);
}
