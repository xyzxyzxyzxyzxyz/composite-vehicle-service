package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CarData;
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
class CarRestServiceProxy {

    private final RestTemplate restTemplate;
    private static String URL = "/cars";

    @Autowired
    public CarRestServiceProxy(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${car.microservice.url}") String baseUrl,
            @Value("${car.microservice.port}") int port) {
        restTemplate = restTemplateBuilder
                .rootUri(baseUrl + ":" + port)
                .build();
    }

    public CarData getCarData(String vinCode) throws HttpClientErrorException, HttpServerErrorException {
        try {
            ResponseEntity<CarData> responseEntity = restTemplate.getForEntity(URL + "/" + vinCode, CarData.class);
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
