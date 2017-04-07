package com.tdd.katas.microservices.customerservice.service;

import com.tdd.katas.microservices.customerservice.repository.CustomerRepository;
import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerData getCustomerData(String customer_id) {
        return customerRepository.getCustomerData(customer_id);
    }
}
