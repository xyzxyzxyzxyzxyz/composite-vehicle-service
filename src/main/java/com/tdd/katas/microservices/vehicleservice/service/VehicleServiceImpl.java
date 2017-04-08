package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public CompositeVehicleData getVehicleData(String vin) {
        // TODO: Change this implementation!
        // We must start calling the remote services after using the repository
        //return vehicleRepository.getVehicleData(vin);
        return null;
    }

}
