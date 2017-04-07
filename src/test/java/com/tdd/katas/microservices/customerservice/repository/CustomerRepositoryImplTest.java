package com.tdd.katas.microservices.customerservice.repository;

import com.tdd.katas.microservices.vehicleservice.model.CustomerData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = CustomerRepositoryImpl.class)
@RunWith(SpringRunner.class)
public class CustomerRepositoryImplTest
{

    @Autowired
    private CustomerRepositoryImpl customerRepository;

    @Test
    public void The_repository_returns_valid_output_for_valid_input() throws Exception {
        String CUSTOMER_ID = "X";

        CustomerData expectedCustomerData = new CustomerData(CUSTOMER_ID,"Sergio", "Osuna Medina");

        customerRepository.store(CUSTOMER_ID, expectedCustomerData);

        CustomerData actualCustomerData =  customerRepository.getCustomerData(CUSTOMER_ID);

        assertEquals("Should return the stored customer data", expectedCustomerData, actualCustomerData);
    }



}
