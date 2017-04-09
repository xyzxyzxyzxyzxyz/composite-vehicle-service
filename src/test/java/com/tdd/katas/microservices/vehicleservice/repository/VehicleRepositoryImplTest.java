package com.tdd.katas.microservices.vehicleservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ContextConfiguration(classes = VehicleRepositoryImpl.class)
@RunWith(SpringRunner.class)
public class VehicleRepositoryImplTest {

    @Autowired
    private VehicleRepositoryImpl vehicleRepository;


    @Before
    public void setUp() {
        // Clear repository between tests
        vehicleRepository.deleteAllVehicleData();
    }

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String VIN = "X";

        VehicleData expectedVehicleData = new VehicleData("CUSTOMER_X");

        vehicleRepository.createVehicleData(VIN, expectedVehicleData);

        VehicleData actualVehicleData =  vehicleRepository.getVehicleData(VIN);

        assertEquals("Should return the stored vehicle data", expectedVehicleData, actualVehicleData);
    }

    @Test
    public void The_repository_returns_null_for_invalid_input() throws Exception {
        VehicleData existingVehicleData = new VehicleData("CUSTOMER_X");

        vehicleRepository.createVehicleData("X", existingVehicleData);

        VehicleData actualVehicleData =  vehicleRepository.getVehicleData("CUSTOMER_Y");

        assertNull("The vehicle data should not exist in the database", actualVehicleData);
    }

    @Test
    public void The_repository_accepts_creating_a_non_existing_vin() throws Exception {
        final String VIN = "X";

        VehicleData expectedVehicleData = new VehicleData("CUSTOMER_X");

        vehicleRepository.createVehicleData(VIN, expectedVehicleData);

        VehicleData actualVehicleData =  vehicleRepository.getVehicleData(VIN);

        assertEquals("The vehicle data should exist in the repository after creation", expectedVehicleData, actualVehicleData);
    }

    @Test
    public void The_repository_does_not_accept_creating_an_already_existing_vin() throws Exception {
        final String VIN = "X";

        VehicleData expectedVehicleData = new VehicleData("CUSTOMER_X");

        vehicleRepository.createVehicleData(VIN, expectedVehicleData);

        VehicleData actualVehicleData =  vehicleRepository.getVehicleData(VIN);

        assertEquals("The vehicle data should exist in the repository after creation", expectedVehicleData, actualVehicleData);

        try {
            vehicleRepository.createVehicleData(VIN, expectedVehicleData);
            fail("Should not have accepted the creation of an already existing VIN");
        }
        catch (IllegalStateException e) {
            // Ok
        }

    }

    @Test
    public void The_repository_allows_deleting_all_the_data() throws Exception {
        final String VIN_X = "VIN_X";
        final String VIN_Y = "VIN_Y";

        vehicleRepository.createVehicleData(VIN_X, new VehicleData("CUSTOMERID_X"));
        vehicleRepository.createVehicleData(VIN_Y, new VehicleData("CUSTOMERID_Y"));

        assertNotNull("The vehicle data should exist after creation", vehicleRepository.getVehicleData(VIN_X));
        assertNotNull("The vehicle data should exist after creation", vehicleRepository.getVehicleData(VIN_Y));

        vehicleRepository.deleteAllVehicleData();

        assertNull("The vehicle data should not exist after clearing the repository", vehicleRepository.getVehicleData(VIN_X));
        assertNull("The vehicle data should not exist after clearing the repository", vehicleRepository.getVehicleData(VIN_Y));
    }


}
