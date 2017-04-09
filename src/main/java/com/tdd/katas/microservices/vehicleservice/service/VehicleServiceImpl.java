package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.*;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private CustomerRestServiceProxy customerRestServiceProxy;
    @Autowired
    private CarRestServiceProxy carRestServiceProxy;
    @Autowired
    private PartRestServiceProxy partRestServiceProxy;

    @Override
    public CompositeVehicleData getVehicleData(String vin) {
        VehicleData vehicleData = vehicleRepository.getVehicleData(vin);

        if (vehicleData==null) {
            return null;
        }

        CustomerData customerData = customerRestServiceProxy.getCustomerData(vehicleData.getCustomerId());

        CarData carData = carRestServiceProxy.getCarData(vin);

        PartData[] partDataList = partRestServiceProxy.getPartData(vin);

        CompositeVehicleData compositeVehicleData = new CompositeVehicleData(customerData, carData, partDataList);

        return compositeVehicleData;
    }

}
