package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;

public interface VehicleRepository {

    CompositeVehicleData getVehicleData(String vin);

}
