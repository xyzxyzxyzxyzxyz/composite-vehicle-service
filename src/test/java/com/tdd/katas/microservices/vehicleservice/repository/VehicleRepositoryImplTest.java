package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CarData;
import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import com.tdd.katas.microservices.vehicleservice.model.PartData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = VehicleRepositoryImpl.class)
@RunWith(SpringRunner.class)
public class VehicleRepositoryImplTest {

    @Autowired
    private VehicleRepositoryImpl vehicleRepository;

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String VIN = "X";

        CompositeVehicleData expectedCompositeVehicleData = new CompositeVehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );

        vehicleRepository.store(VIN, expectedCompositeVehicleData);

        CompositeVehicleData actualCompositeVehicleData =  vehicleRepository.getVehicleData(VIN);

        assertEquals("Should return the stored vehicle data", expectedCompositeVehicleData, actualCompositeVehicleData);
    }

    @Test
    public void The_repository_returns_null_for_invalid_input() throws Exception {
        CompositeVehicleData existingCompositeVehicleData = new CompositeVehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );
        vehicleRepository.store("X", existingCompositeVehicleData);

        CompositeVehicleData actualCompositeVehicleData =  vehicleRepository.getVehicleData("Y");

        assertNull("The vehicle data should not exist in the database", actualCompositeVehicleData);
    }

}
