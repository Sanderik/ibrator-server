package com.sanderik.controllers;

import com.sanderik.helpers.TokenGenerator;
import com.sanderik.models.Device;
import com.sanderik.models.User;
import com.sanderik.models.viewmodels.AddDeviceModel;
import com.sanderik.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class DeviceController extends BaseController{

    @Autowired private DeviceRepository deviceRepository;

    @Autowired private TokenGenerator tokenGenerator;

    @PostMapping("/device")
    public Device registerDevice(@Valid @RequestBody Device device) {
        Device deviceResult = deviceRepository.findOneByChipId(device.getChipId());

        if(deviceResult == null){
            deviceResult = new Device(device.getChipId(), tokenGenerator.generateToken());
            deviceRepository.save(deviceResult);
        }

        return deviceResult;
    }

    @RequestMapping(value = "/device/add", method = RequestMethod.POST)
    public ModelAndView addDevice(@ModelAttribute AddDeviceModel addDeviceModel, Model model){
        User user     = this.getUser();
        Device device = deviceRepository.findOneByConnectionToken(addDeviceModel.getConnectionToken());

        if(device == null){
            // TODO : Handle error messages in view
            model.addAttribute("error", "Connection token not found or expired.");
        } else {
            user.addDevice(device);
            device.setUser(user);
            device.setName(addDeviceModel.getName());

            deviceRepository.save(device);
            userRepository.save(user);
        }

        return new ModelAndView("redirect:/welcome");
    }

    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable("message") String message) throws IOException {
        VibrateMessageHandler.sendMsg(message);

        return message;
    }
}
