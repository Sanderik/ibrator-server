package com.sanderik.controllers;

import com.sanderik.helpers.TokenGenerator;
import com.sanderik.models.Device;
import com.sanderik.models.User;
import com.sanderik.models.viewmodels.AddDeviceModel;
import com.sanderik.models.viewmodels.ControlDeviceViewModel;
import com.sanderik.repositories.DeviceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class DeviceController extends BaseController{

    @Autowired private DeviceRepository deviceRepository;

    @Autowired private TokenGenerator tokenGenerator;

    @GetMapping("/device/{id}/control")
    public ModelAndView controlDevice(@PathVariable("id") Long id, Model model){
        List<Device> devices = this.getUser().getDevices()
                .stream()
                .filter(device -> Objects.equals(device.getId(), id))
                .collect(Collectors.toList());

        if(devices.size() == 0){
            model.addAttribute("Error", "Device not found or no access to this device");
            return new ModelAndView("redirect:/welcome");
        }

        model.addAttribute("device", devices.get(0));
        return new ModelAndView("control");
    }

    @PostMapping("/device/control")
    public ResponseEntity<ModelAndView> controlDevice(@ModelAttribute ControlDeviceViewModel controlDeviceViewModel, Model model) throws IOException {
        List<Device> devices = this.getUser().getDevices()
                .stream()
                .filter(device -> Objects.equals(device.getId(), controlDeviceViewModel.getDeviceId()))
                .collect(Collectors.toList());

        if(devices.size() == 0){
            model.addAttribute("Error", "Device not found or no access to this device");
            return ResponseEntity.badRequest().body(new ModelAndView("redirect:/welcome"));
        }

        Device device = devices.get(0);
        WebSocketSession session = VibrateMessageHandler.getActiveSessionForDevice(device.getId());
        model.addAttribute("device", devices.get(0));

        if(session == null){
            model.addAttribute("error", "No active session found, please turn on your device first and make sure it's connected to the internet.");
            return ResponseEntity.badRequest().body(new ModelAndView("welcome"));
        }

        JSONObject obj = new JSONObject();
        obj.put("duration", controlDeviceViewModel.getIntensity());
        session.sendMessage(new TextMessage(obj.toString()));

        return ResponseEntity.ok().body(new ModelAndView("welcome"));
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
}
