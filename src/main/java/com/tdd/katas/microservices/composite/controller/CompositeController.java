package com.tdd.katas.microservices.composite.controller;

import com.tdd.katas.microservices.composite.model.VehicleData;
import com.tdd.katas.microservices.composite.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class CompositeController {

    @Autowired
    private CompositeService compositeService;

    @GetMapping("/{vin}")
    public VehicleData getVehicleData(@PathVariable String vin){
        return compositeService.getVehicleData(vin);
    }

}
