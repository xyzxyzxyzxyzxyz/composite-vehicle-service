package com.tdd.katas.microservices.vehicleservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
class CustomerRestServiceProxy {

    private final RestTemplate restTemplate;
    private static String URL = "/customers";

    public CustomerRestServiceProxy(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Map<String,Object> getCustomerData(String customerId) {
        ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(URL + "/" + customerId, HashMap.class);
        return responseEntity.getBody();
    }

}
