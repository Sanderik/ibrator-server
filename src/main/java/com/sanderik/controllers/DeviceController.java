package com.sanderik.controllers;

import com.sanderik.helpers.TokenGenerator;
import com.sanderik.models.Device;
import com.sanderik.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping("/device")
    public Device postDevice(@Valid @RequestBody Device device) {
        Device _device = deviceRepository.findOneByChipId(device.getChipId());

        if(_device == null){
            _device = new Device(TokenGenerator.generateToken());
            deviceRepository.save(device);
        }

        return _device;
    }
}
