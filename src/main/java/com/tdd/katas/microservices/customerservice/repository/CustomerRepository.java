package com.tdd.katas.microservices.customerservice.repository;


import com.tdd.katas.microservices.vehicleservice.model.CustomerData;

public interface CustomerRepository {
    CustomerData getCustomerData(String customer_id);
}
