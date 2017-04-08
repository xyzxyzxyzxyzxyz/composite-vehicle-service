package com.tdd.katas.microservices.vehicleservice.controller;

import com.tdd.katas.microservices.vehicleservice.model.CompositeVehicleData;
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
    public ResponseEntity<CompositeVehicleData> getVehicleData(@PathVariable String vin){
        CompositeVehicleData compositeVehicleData;

        try {
            compositeVehicleData = vehicleService.getVehicleData(vin);
        } catch(Throwable error) {
            return new ResponseEntity<CompositeVehicleData>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (compositeVehicleData == null) {
            return new ResponseEntity<CompositeVehicleData>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<CompositeVehicleData>(compositeVehicleData, HttpStatus.OK);
    }

}
