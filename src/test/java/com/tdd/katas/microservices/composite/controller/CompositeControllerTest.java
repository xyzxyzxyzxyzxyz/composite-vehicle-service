package com.tdd.katas.microservices.composite.controller;

import com.tdd.katas.microservices.composite.model.CarData;
import com.tdd.katas.microservices.composite.model.CustomerData;
import com.tdd.katas.microservices.composite.model.PartData;
import com.tdd.katas.microservices.composite.model.VehicleData;
import com.tdd.katas.microservices.composite.service.CompositeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CompositeController.class)
@WithMockUser
public class CompositeControllerTest {


    @Autowired
    private CompositeController compositeController;

    @MockBean
    private CompositeService compositeService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", compositeController);
    }

    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String VIN = "1";

        VehicleData expectedVehicleData = new VehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                    new PartData("1","Wheels"),
                    new PartData("2","Doors"))
        );


        given(compositeService.getVehicleData(VIN)).willReturn(expectedVehicleData);

        this.mvc.perform(
                    get("/vehicles/" + VIN)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.customerData.customerId", is(expectedVehicleData.getCustomerData().getCustomerId())))
                .andExpect(jsonPath("$.customerData.name", is(expectedVehicleData.getCustomerData().getName())))
                .andExpect(jsonPath("$.customerData.surnames", is(expectedVehicleData.getCustomerData().getSurnames())))
                .andExpect(jsonPath("$.carData.plateNumber", is(expectedVehicleData.getCarData().getPlateNumber())))
                .andExpect(jsonPath("$.carData.model", is(expectedVehicleData.getCarData().getModel())))
                .andExpect(jsonPath("$.carData.color", is(expectedVehicleData.getCarData().getColor())))
                .andExpect(jsonPath("$.partDataList[0].partId", is(expectedVehicleData.getPartDataList().get(0).getPartId())))
                .andExpect(jsonPath("$.partDataList[0].description", is(expectedVehicleData.getPartDataList().get(0).getDescription())))
                .andExpect(jsonPath("$.partDataList[1].partId", is(expectedVehicleData.getPartDataList().get(1).getPartId())))
                .andExpect(jsonPath("$.partDataList[1].description", is(expectedVehicleData.getPartDataList().get(1).getDescription())));

        verify(compositeService.getVehicleData(VIN));

    }

}
