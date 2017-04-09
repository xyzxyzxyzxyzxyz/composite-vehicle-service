package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.PartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
class PartRestServiceProxy {
    private final RestTemplate restTemplate;
    private static String URL = "/parts";

    @Autowired
    public PartRestServiceProxy (
            RestTemplateBuilder restTemplateBuilder,
            @Value("${part.microservice.url}") String baseUrl,
            @Value("${part.microservice.port}") int port) {
        restTemplate = restTemplateBuilder
                .rootUri(baseUrl + ":" + port)
                .build();
    }

    public PartData[] getPartData(String vinCode) throws HttpClientErrorException, HttpServerErrorException{
        try {
            ResponseEntity<PartData[]> responseEntity = restTemplate.getForEntity(URL + "/" + vinCode, PartData[].class);
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
