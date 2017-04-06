package com.tdd.katas.microservices.composite.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CompositeController.class)
public class CompositeControllerTest {

    @Autowired
    private CompositeController compositeController;

    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", compositeController);
    }

}
