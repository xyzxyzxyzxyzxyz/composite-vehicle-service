package com.tdd.katas.microservices.vehicleservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CarRestServiceProxy {

    private final RestTemplate restTemplate;
    private static String URL = "/cars";

    public CarRestServiceProxy(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Object> getCarData(String vinCode) {
        try {
            ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(URL + "/" + vinCode, HashMap.class);
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
