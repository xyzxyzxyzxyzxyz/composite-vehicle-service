package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{

    private Map<String, VehicleData> map = new HashMap<>();

    void store(String vin, VehicleData vehicleData) {
        map.put(vin, vehicleData);
    }

    @Override
    public VehicleData getVehicleData(String vin) {
        return map.get(vin);
    }
}
