package com.tdd.katas.microservices.vehicleservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.katas.microservices.vehicleservice.model.*;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VehicleServiceImpl.class, ObjectMapper.class })
public class VehicleServiceImplTest {

    @Autowired
    private VehicleService vehicleService;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerRestServiceProxy customerRestServiceProxy;
    @MockBean
    private CarRestServiceProxy carRestServiceProxy;
    @MockBean
    private PartRestServiceProxy partRestServiceProxy;


    @Test
    public void The_service_delegates_the_calls_to_the_repository_and_the_remote_services() throws IOException {
        String VIN = "X";

        String mockCustomerId = "1";

        // Mock the VehicleRepository output
        VehicleData mockVehicleData = new VehicleData(mockCustomerId);
        given(vehicleRepository.getVehicleData(VIN)).willReturn(mockVehicleData);

        // Mock the CustomerRestServiceProxy output
        String mockCustomerDataJson =
                "{" +
                    "\"customerId\" : \""  + mockCustomerId + "\" ,"  +
                    "\"name\" : \"Maria\" ," +
                    "\"surnames\" : \"De los Palotes\" " +
                "}";
        Map<String, Object> mockCustomerData = objectMapper.readValue(mockCustomerDataJson, new TypeReference<HashMap<String,Object>>(){});
        given(customerRestServiceProxy.getCustomerData(mockCustomerId)).willReturn(mockCustomerData);

        // Mock the CarRestServiceProxy output
        String mockCarDataJson =
                "{" +
                    "\"plateNumber\" : \"1234\" ,"  +
                    "\"model\" : \"Seat Leon\" ," +
                    "\"color\" : \"Red\" " +
                "}";
        Map<String, Object> mockCarData = objectMapper.readValue(mockCarDataJson, new TypeReference<HashMap<String,Object>>(){});
        given(carRestServiceProxy.getCarData(VIN)).willReturn(mockCarData);

        // Mock the PartRestServiceProxy output
        String mockPartDataListJson =
                "[" +
                    "{" +
                        "\"partId\" : \"1\" ,"  +
                        "\"description\" : \"Wheel\"" +
                    "}," +
                    "{" +
                        "\"partId\" : \"2\" ,"  +
                        "\"description\" : \"door\"" +
                    "}" +
                "]";
        List<Map<String,Object>> mockPartDataList = objectMapper.readValue(mockPartDataListJson, new TypeReference<List<Map<String,Object>>>(){});
        given(partRestServiceProxy.getPartData(VIN)).willReturn(mockPartDataList);


        // Define the expected service output
        CompositeVehicleData expectedCompositeVehicleData = new CompositeVehicleData(
                new CustomerData(mockCustomerId,"Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                        new PartData("1","Wheels"),
                        new PartData("2","Doors"))
        );

        // Call the service
        CompositeVehicleData actualCompositeVehicleData = vehicleService.getVehicleData(VIN);

        // The service must call the repository with the same input
        verify(vehicleRepository).getVehicleData(VIN);
        // The service must call the CustomerRestServiceProxy with the customerId
        verify(customerRestServiceProxy).getCustomerData(mockCustomerId);
        // The service must call the CarRestServiceProxy with the VIN
        verify(carRestServiceProxy).getCarData(VIN);
        // The service must call the PartRestServiceProxy with the VIN
        verify(partRestServiceProxy).getPartData(VIN);

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
