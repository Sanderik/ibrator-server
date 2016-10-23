package com.sanderik.controllers;

import com.sanderik.helpers.TokenGenerator;
import com.sanderik.models.Device;
import com.sanderik.models.User;
import com.sanderik.models.viewmodels.AddDeviceModel;
import com.sanderik.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class DeviceController extends BaseController{

    @Autowired private DeviceRepository deviceRepository;

    @Autowired private TokenGenerator tokenGenerator;

    @GetMapping("/device")
    public String getDevice(){
        return "wtf is dit";
    }

    @PostMapping("/device")
    public Device registerDevice(@Valid @RequestBody Device device) {
        Device deviceResult = deviceRepository.findOneByChipId(device.getChipId());

        if(deviceResult == null){
            deviceResult = new Device(device.getChipId(), tokenGenerator.generateToken());
            deviceRepository.save(deviceResult);
        }

        return deviceResult;
    }

    @RequestMapping(value = "/device/change", method = RequestMethod.POST)
    public ModelAndView updateDeviceState(@ModelAttribute Device device, Model model) {
        Device deviceResult = deviceRepository.findOne(device.getId());

        if (deviceResult == null) {
            model.addAttribute("error", "No device found");
        } else {
            deviceResult.setActive(device.isActive());
            deviceRepository.save(deviceResult);
        }

        return new ModelAndView("redirect:/welcome");
    }

    @RequestMapping(value = "/device/add", method = RequestMethod.POST)
    public ModelAndView addDevice(@ModelAttribute AddDeviceModel addDeviceModel, Model model){
        User user     = this.getUser();
        Device device = deviceRepository.findOneByConnectionToken(addDeviceModel.getConnectionToken());

        if(device == null){
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
        User user     = this.getUser();
//        VibrateMessageHandler.sendMsg(message);

        return message;
    }
}
