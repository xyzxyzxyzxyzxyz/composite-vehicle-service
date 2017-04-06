package com.tdd.katas.microservices.composite.controller;

import com.tdd.katas.microservices.composite.model.VehicleData;
import com.tdd.katas.microservices.composite.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class CompositeController {

    @Autowired
    private CompositeService compositeService;

    @GetMapping("/{vin}")
    public ResponseEntity<VehicleData> getVehicleData(@PathVariable String vin){
        VehicleData vehicleData = compositeService.getVehicleData(vin);

        if (vehicleData == null) {
            return new ResponseEntity<VehicleData>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<VehicleData>(vehicleData, HttpStatus.OK);
    }

}
