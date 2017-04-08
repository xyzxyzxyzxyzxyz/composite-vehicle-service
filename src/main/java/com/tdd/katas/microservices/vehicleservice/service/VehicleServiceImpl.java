package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.*;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        Map<String,Object> customerDataProxyOutput = customerRestServiceProxy.getCustomerData(vehicleData.getCustomerId());
        CustomerData customerData = new CustomerData(
                customerDataProxyOutput.get("customerId").toString(),
                customerDataProxyOutput.get("name").toString(),
                customerDataProxyOutput.get("surnames").toString()
        );

        Map<String,Object> carDataProxyOutput = carRestServiceProxy.getCarData(vin);
        CarData carData = new CarData(
                carDataProxyOutput.get("plateNumber").toString(),
                carDataProxyOutput.get("model").toString(),
                carDataProxyOutput.get("color").toString()
        );

        List<Map<String,Object>> partDataProxyOutput = partRestServiceProxy.getPartData(vin);
        List<PartData> partDataList = new ArrayList<>();
        for (Map<String,Object> partItem : partDataProxyOutput) {
            PartData part = new PartData(
                    partItem.get("partId").toString(),
                    partItem.get("description").toString()
            );
            partDataList.add(part);
        }

        CompositeVehicleData compositeVehicleData = new CompositeVehicleData(customerData, carData, partDataList);

        return compositeVehicleData;
    }

}
