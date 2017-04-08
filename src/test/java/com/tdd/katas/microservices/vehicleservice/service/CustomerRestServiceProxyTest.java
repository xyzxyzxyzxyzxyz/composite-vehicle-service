package com.tdd.katas.microservices.vehicleservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest(CustomerRestServiceProxy.class)
public class CustomerRestServiceProxyTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private CustomerRestServiceProxy customerRestServiceProxy;

    @Autowired
    ObjectMapper customerMapper;


    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", customerRestServiceProxy);
    }



    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String customerId = "1";

        String mockCustomerData =
            "{" +
                "\"customerId\" : \""  + customerId + "\" ,"  +
                "\"name\" : \"Maria\" ," +
                "\"surnames\" : \"De los Palotes\" " +
            "}";

        Map<String, Object> expectedCustomerData = customerMapper.readValue(mockCustomerData, new TypeReference<HashMap<String,Object>>(){});

        this.server.expect(requestTo("/customers/" + customerId))
                .andRespond(withSuccess(mockCustomerData, MediaType.APPLICATION_JSON));
        Map<String,Object> actualCustomerData = customerRestServiceProxy.getCustomerData(customerId);

        assertEquals("Customer data must match", expectedCustomerData, actualCustomerData);
        
    }
    
}