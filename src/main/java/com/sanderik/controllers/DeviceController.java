package com.sanderik.controllers;

import com.sanderik.helpers.TokenGenerator;
import com.sanderik.models.Device;
import com.sanderik.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class DeviceController {

    @Autowired private DeviceRepository deviceRepository;

    @Autowired private TokenGenerator tokenGenerator;

    @PostMapping("/device")
    public Device postDevice(@Valid @RequestBody Device device) {
        Device deviceResult = deviceRepository.findOneByChipId(device.getChipId());

        if(deviceResult == null){
            deviceResult = new Device(device.getChipId(), tokenGenerator.generateToken());
            deviceRepository.save(deviceResult);
        }

        return deviceResult;
    }

    @RequestMapping("/send/{message}")
    @Description("Call for sending messages to the last known Websocket session, " +
            "only works when server has a socket connection with an ESP.")
    public String sendMessage(@PathVariable("message") String message) throws IOException {
        VibrateMessageHandler.sendMsg(message);

        return message;
    }
}
