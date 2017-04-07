package com.tdd.katas.microservices.customerservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;

public interface CustomerService {
    CustomerData getCustomerData(String customer_id);
}
