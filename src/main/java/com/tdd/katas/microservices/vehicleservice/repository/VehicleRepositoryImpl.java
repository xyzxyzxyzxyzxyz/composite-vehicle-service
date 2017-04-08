package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository{

    private Map<String, CompositeVehicleData> map = new HashMap<>();

    void store(String vin, CompositeVehicleData compositeVehicleData) {
        map.put(vin, compositeVehicleData);
    }

    @Override
    public CompositeVehicleData getVehicleData(String vin) {
        return map.get(vin);
    }
}
