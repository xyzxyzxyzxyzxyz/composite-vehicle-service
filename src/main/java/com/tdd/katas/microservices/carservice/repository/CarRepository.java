package com.tdd.katas.microservices.carservice.repository;


import com.tdd.katas.microservices.vehicleservice.model.CarData;

public interface CarRepository {
    CarData getCarData(String vin);
}
