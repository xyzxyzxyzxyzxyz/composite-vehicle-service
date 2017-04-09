package com.tdd.katas.microservices.vehicleservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.katas.microservices.vehicleservice.model.*;
import com.tdd.katas.microservices.vehicleservice.repository.VehicleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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

    @Test
    public void The_service_propagates_the_server_errors_from_the_remote_customer_service() {
        final String mockVinCode = "X";
        final String mockCustomerId = "CUSTOMER_X";

        // Will return a normal VehicleData from the repository, with the expected customerId
        given(vehicleRepository.getVehicleData(mockVinCode)).willReturn(new VehicleData(mockCustomerId));
        // Will return normal content from the non failing proxies
        given(carRestServiceProxy.getCarData(mockVinCode)).willReturn(new HashMap<String,Object>());
        given(partRestServiceProxy.getPartData(mockVinCode)).willReturn(new ArrayList<>());
        // This proxy will fail with a server error
        given(customerRestServiceProxy.getCustomerData(mockCustomerId)).willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer service not ready"));

        try {
            // Call the service
            vehicleService.getVehicleData(mockVinCode);
            fail("Should have thrown an exception");
        } catch (HttpServerErrorException e) {
            // The error has been propagated by the service
        }

        // We know that the repository must be called to get the customerId, which is a necessary input for the customer service
        verify(vehicleRepository).getVehicleData(mockVinCode);
        // We know that the customer service must be called
        verify(customerRestServiceProxy).getCustomerData(mockCustomerId);
    }


}
