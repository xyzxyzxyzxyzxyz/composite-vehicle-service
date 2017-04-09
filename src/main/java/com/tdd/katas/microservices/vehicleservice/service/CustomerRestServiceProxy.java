package com.tdd.katas.microservices.vehicleservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
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
class CustomerRestServiceProxy {

    private final RestTemplate restTemplate;
    private static String URL = "/customers";

    @Autowired
    public CustomerRestServiceProxy(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${customer.microservice.url}") String baseUrl,
            @Value("${customer.microservice.port}") int port) {
        restTemplate = restTemplateBuilder
                .rootUri(baseUrl + ":" + port)
                .build();
    }

    public CustomerData getCustomerData(String customerId) throws HttpClientErrorException, HttpServerErrorException {
        try {
            ResponseEntity<CustomerData> responseEntity = restTemplate.getForEntity(URL + "/" + customerId, CustomerData.class);
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
