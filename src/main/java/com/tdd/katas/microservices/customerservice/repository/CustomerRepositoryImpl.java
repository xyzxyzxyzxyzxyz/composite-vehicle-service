package com.tdd.katas.microservices.customerservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
    public void store(String customer_id, CustomerData expectedCustomerData) {
    }

    @Override
    public CustomerData getCustomerData(String customer_id) {
        return null;
    }
}
