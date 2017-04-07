package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import org.springframework.stereotype.Component;

@Component
public class VehicleRepositoryImpl implements VehicleRepository{
    void store(String vin, VehicleData expectedVehicleData) {

    }

    @Override
    public VehicleData getVehicleData(String vin) {
        return null;
    }
}
