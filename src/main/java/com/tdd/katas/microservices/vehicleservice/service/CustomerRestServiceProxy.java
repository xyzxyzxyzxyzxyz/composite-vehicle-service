package com.tdd.katas.microservices.vehicleservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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

    public Map<String,Object> getCustomerData(String customerId) throws HttpClientErrorException, HttpServerErrorException {
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(URL + "/" + customerId, HashMap.class);
            return responseEntity.getBody();
        }
        catch(HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            else {
                throw e;
            }
        }
    }

}
