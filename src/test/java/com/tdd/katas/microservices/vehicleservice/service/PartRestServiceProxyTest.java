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
@RestClientTest(PartRestServiceProxy.class)
public class PartRestServiceProxyTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private PartRestServiceProxy partRestServiceProxy;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", partRestServiceProxy);
    }

    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String vinCode = "1";

        String mockPartData =
                "{" +
                        "\"plateNumber\" : \"1234\" ,"  +
                        "\"model\" : \"Seat Leon\" ," +
                        "\"color\" : \"Red\" " +
                        "}";

        Map<String, Object> expectedCustomerData = objectMapper.readValue(mockPartData, new TypeReference<HashMap<String,Object>>(){});

        this.server.expect(requestTo("/parts/" + vinCode))
                .andRespond(withSuccess(mockPartData, MediaType.APPLICATION_JSON));
        Map<String,Object> actualPartData = partRestServiceProxy.getPartData(vinCode);

        assertEquals("Customer data must match", expectedCustomerData, actualPartData);

    }
}
