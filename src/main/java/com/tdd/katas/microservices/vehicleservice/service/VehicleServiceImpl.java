package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public VehicleData getVehicleData(String vin) {
        return vehicleRepository.getVehicleData(vin);
    }

}
