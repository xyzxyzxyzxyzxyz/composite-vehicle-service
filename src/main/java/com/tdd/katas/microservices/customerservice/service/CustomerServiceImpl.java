package com.tdd.katas.microservices.customerservice.service;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerData getCustomerData(String customer_id) {
        return null;
    }
}
