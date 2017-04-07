package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CarData;
import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import com.tdd.katas.microservices.vehicleservice.model.PartData;
import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
@ContextConfiguration(classes = VehicleRepositoryImpl.class)
@RunWith(SpringRunner.class)

public class VehicleRepositoryImplTest {

    @Autowired
    private VehicleRepositoryImpl vehicleRepository;

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String VIN = "X";

        VehicleData expectedVehicleData = new VehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );

        vehicleRepository.store(VIN, expectedVehicleData);

        VehicleData actualVehicleData =  vehicleRepository.getVehicleData(VIN);

        assertEquals("Should return the stored vehicle data", expectedVehicleData, actualVehicleData);


    }
}
