package com.tdd.katas.microservices.vehicleservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
class CustomerRestServiceProxy {

    private final RestTemplate restTemplate;

    public CustomerRestServiceProxy(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Map<String,Object> getCustomerData(String customerId) {
        return  null;
    }
}
