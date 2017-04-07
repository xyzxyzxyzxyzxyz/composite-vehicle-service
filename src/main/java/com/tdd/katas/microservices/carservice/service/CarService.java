package com.tdd.katas.microservices.carservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CarData;

public interface CarService {
    CarData getCarData(String vin);
}
