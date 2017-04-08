package com.tdd.katas.microservices.vehicleservice.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class PartRestServiceProxy {
    private final RestTemplate restTemplate;
    private static String URL = "/parts";

    public PartRestServiceProxy(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Object> getPartData(String vinCode) {
        ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(URL + "/" + vinCode, HashMap.class);
        return responseEntity.getBody();
    }
}
