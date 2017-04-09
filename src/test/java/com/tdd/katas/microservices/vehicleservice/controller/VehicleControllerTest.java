package com.tdd.katas.microservices.vehicleservice.controller;

import com.tdd.katas.microservices.carservice.model.CarData;
import com.tdd.katas.microservices.customerservice.model.CustomerData;
import com.tdd.katas.microservices.partservice.model.PartData;
import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
import com.tdd.katas.microservices.vehicleservice.service.VehicleService;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VehicleController.class)
@WithMockUser
public class VehicleControllerTest {


    @Autowired
    private VehicleController vehicleController;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void Can_create_instance() {
        assertNotNull("Should be able to create instance", vehicleController);
    }

    @Test
    public void It_returns_valid_data_for_a_valid_input() throws Exception {

        final String VIN = "1";

        CompositeVehicleData expectedCompositeVehicleData = new CompositeVehicleData(
                new CustomerData("1","Sergio", "Osuna Medina"),
                new CarData("W111","Seat Leon","Red"),
                Arrays.asList(
                    new PartData("1","Wheels"),
                    new PartData("2","Doors"))
        );


        given(vehicleService.getVehicleData(VIN)).willReturn(expectedCompositeVehicleData);

        this.mvc.perform(
                    get("/vehicles/" + VIN)
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.customerData.customerId", is(expectedCompositeVehicleData.getCustomerData().getCustomerId())))
                .andExpect(jsonPath("$.customerData.name", is(expectedCompositeVehicleData.getCustomerData().getName())))
                .andExpect(jsonPath("$.customerData.surnames", is(expectedCompositeVehicleData.getCustomerData().getSurnames())))
                .andExpect(jsonPath("$.carData.plateNumber", is(expectedCompositeVehicleData.getCarData().getPlateNumber())))
                .andExpect(jsonPath("$.carData.model", is(expectedCompositeVehicleData.getCarData().getModel())))
                .andExpect(jsonPath("$.carData.color", is(expectedCompositeVehicleData.getCarData().getColor())))
                .andExpect(jsonPath("$.partDataList[0].partId", is(expectedCompositeVehicleData.getPartDataList().get(0).getPartId())))
                .andExpect(jsonPath("$.partDataList[0].description", is(expectedCompositeVehicleData.getPartDataList().get(0).getDescription())))
                .andExpect(jsonPath("$.partDataList[1].partId", is(expectedCompositeVehicleData.getPartDataList().get(1).getPartId())))
                .andExpect(jsonPath("$.partDataList[1].description", is(expectedCompositeVehicleData.getPartDataList().get(1).getDescription())));

        verify(vehicleService).getVehicleData(VIN);

    }

    @Test
    public void It_returns_404_if_the_VIN_does_not_exist() throws Exception {

        given(vehicleService.getVehicleData(any())).willReturn(null);

        this.mvc
            .perform(
                get("/vehicles/" + "PEPITO")
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNotFound());

        verify(vehicleService).getVehicleData(any());

    }

    @Test
    public void It_returns_500_if_the_service_throws_an_error() throws Exception {

        final String VIN = "1";

        given(vehicleService.getVehicleData(any())).willThrow(
                new IllegalStateException("database is not ready"));

        this.mvc
                .perform(
                        get("/vehicles/" + VIN)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());

        verify(vehicleService).getVehicleData(any());

    }

}
