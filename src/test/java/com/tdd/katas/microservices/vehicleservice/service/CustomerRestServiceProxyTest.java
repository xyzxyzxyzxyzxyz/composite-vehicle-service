package com.tdd.katas.microservices.vehicleservice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@RestClientTest(CustomerRestServiceProxy.class)
public class CustomerRestServiceProxyTest {

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private CustomerRestServiceProxy customerRestServiceProxy;


    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", customerRestServiceProxy);
    }
    
}
