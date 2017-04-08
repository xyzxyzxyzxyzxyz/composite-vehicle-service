package com.tdd.katas.microservices.vehicleservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(CarRestServiceProxy.class)
public class CarRestServiceProxyTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private CarRestServiceProxy carRestServiceProxy;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", carRestServiceProxy);
    }



    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String vinCode = "1";

        String mockCarData =
            "{" +
                "\"plateNumber\" : \"1234\" ,"  +
                "\"model\" : \"Seat Leon\" ," +
                "\"color\" : \"Red\" " +
            "}";

        Map<String, Object> expectedCustomerData = objectMapper.readValue(mockCarData, new TypeReference<HashMap<String,Object>>(){});

        this.server.expect(requestTo("/cars/" + vinCode))
                .andRespond(withSuccess(mockCarData, MediaType.APPLICATION_JSON));
        Map<String,Object> actualCarData = carRestServiceProxy.getCarData(vinCode);

        assertEquals("Customer data must match", expectedCustomerData, actualCarData);
        
    }

    @Test
    public void It_returns_null_if_the_vin_does_not_exist() throws Exception {

        final String vinCode = "NON_EXISTING_ID";

        this.server.expect(requestTo("/cars/" + vinCode))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Map<String,Object> actualCarData = carRestServiceProxy.getCarData(vinCode);

        assertNull("Car must not exist", actualCarData);

    }

    @Test(expected = HttpServerErrorException.class)
    public void It_throws_an_exception_if_the_service_returns_a_service_error() throws Exception {

        this.server.expect(requestTo("/cars/ANYTHING"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        carRestServiceProxy.getCarData("ANYTHING");
    }

}
