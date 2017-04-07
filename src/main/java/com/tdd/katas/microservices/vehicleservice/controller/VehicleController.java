package com.tdd.katas.microservices.vehicleservice.controller;

import com.tdd.katas.microservices.vehicleservice.model.VehicleData;
import com.tdd.katas.microservices.vehicleservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/{vin}")
    public ResponseEntity<VehicleData> getVehicleData(@PathVariable String vin){
        VehicleData vehicleData;

        try {
            vehicleData = vehicleService.getVehicleData(vin);
        } catch(Throwable error) {
            return new ResponseEntity<VehicleData>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (vehicleData == null) {
            return new ResponseEntity<VehicleData>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<VehicleData>(vehicleData, HttpStatus.OK);
    }

}
