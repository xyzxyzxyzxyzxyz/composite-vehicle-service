package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CarData;
import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import com.tdd.katas.microservices.vehicleservice.model.PartData;
import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = VehicleServiceImpl.class)
public class VehicleServiceImplTest {

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    public void The_service_delegates_the_calls_to_the_repository() {
        String VIN = "X";

        VehicleData expectedVehicleData = new VehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );

        given(vehicleRepository.getVehicleData(VIN)).willReturn(expectedVehicleData);

        VehicleData actualVehicleData = vehicleService.getVehicleData(VIN);

        // The service must delegate the call to the repository with the same input
        verify(vehicleRepository).getVehicleData(VIN);

        assertEquals("The service should return the VehicleData as provided by the repository", expectedVehicleData, actualVehicleData);
    }

}
