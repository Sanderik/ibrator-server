package com.sanderik.controllers;

import com.sanderik.models.Device;
import com.sanderik.repositories.DeviceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class VibrateMessageHandler extends TextWebSocketHandler {

    @Autowired
    private DeviceRepository deviceRepository;

    private static HashMap<Device, WebSocketSession> deviceWsSessions;

    public VibrateMessageHandler(){
        super();
        deviceWsSessions = new HashMap<>();
    }
    // Method that handles INCOMMING messages
    // This method is only used to authenticate the ESP module.
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        JSONObject payload = new JSONObject(message.getPayload());

        if(!payload.has("token")){
            session.close();
        }
        else {
            authenticateDevice(payload.getString("token"), session);
        }
    }

    // Remove the Set<User, WebSocketSession> from the Hashmap when the connection is closed.
    // TODO : This does not work on ESP, but with a JS browser client it DOES work for some reaosn?
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Set<Device> keySet = deviceWsSessions.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), session))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        Device device = keySet.iterator().next();
        device.setActive(false);
        deviceRepository.save(device);

        deviceWsSessions.remove(keySet.iterator().next());
        session.close();
    }


    // This method can be used by controllers to access the active websocket session for the device.
    public static WebSocketSession getActiveSessionForDevice(Device device){
        return deviceWsSessions.get(device);
    }

    private void authenticateDevice(String connectionToken, WebSocketSession session) throws IOException {
        Device device = deviceRepository.findOneByConnectionToken(connectionToken);
        if(device == null){
            session.close();
        } else {
            deviceWsSessions.put(device, session);
            device.setActive(true);
            deviceRepository.save(device);

            TextMessage msg = new TextMessage(device.getUser().getEmail() + " is successfully authenticated.");
            session.sendMessage(msg);
        }
    }
}
