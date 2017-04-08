package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CarData;
import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import com.tdd.katas.microservices.vehicleservice.model.PartData;
import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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

        CompositeVehicleData expectedCompositeVehicleData = new CompositeVehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );

        given(vehicleRepository.getVehicleData(VIN)).willReturn(expectedCompositeVehicleData);

        CompositeVehicleData actualCompositeVehicleData = vehicleService.getVehicleData(VIN);

        // The service must delegate the call to the repository with the same input
        verify(vehicleRepository).getVehicleData(VIN);

        assertEquals("The service should return the CompositeVehicleData as provided by the repository", expectedCompositeVehicleData, actualCompositeVehicleData);
    }


    @Test
    public void The_service_propagates_the_errors_from_the_repository() {

        given(vehicleRepository.getVehicleData(any())).willThrow(new IllegalStateException("database not ready"));

        try {
            CompositeVehicleData actualCompositeVehicleData = vehicleService.getVehicleData("X");
            fail("Should have thrown an exception");
        } catch (IllegalStateException e) {
            // The error has been propagated by the service
        }

        // The service must delegate the call to the repository with the same input
        verify(vehicleRepository).getVehicleData(any());


    }
}
