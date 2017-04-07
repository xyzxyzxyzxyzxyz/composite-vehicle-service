package com.tdd.katas.microservices.customerservice.controller;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @GetMapping("/{customerId}")
    public CustomerData getCustomerData(@PathVariable String customerId) {
        return null;
    }

}
