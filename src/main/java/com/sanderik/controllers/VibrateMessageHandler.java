package com.sanderik.controllers;

import com.sanderik.models.Device;
import com.sanderik.repositories.DeviceRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.HashMap;

public class VibrateMessageHandler extends TextWebSocketHandler {

    @Autowired
    private DeviceRepository deviceRepository;

    private static HashMap<Long, WebSocketSession> deviceIdsWsSessions;

    public VibrateMessageHandler(){
        super();
        deviceIdsWsSessions = new HashMap<>();
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
    // This does not work on ESP, but with a JS browser client it DOES work for some reaosn?
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        Set<Long> keySet = deviceIdsWsSessions.entrySet()
//                .stream()
//                .filter(entry -> Objects.equals(entry.getValue(), session))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toSet());
//
//        Long device = keySet.iterator().next();
//        device.setActive(false);
//        deviceRepository.save(device);
//
//        deviceIdsWsSessions.remove(keySet.iterator().next());
//        session.close();
//    }


    // This method can be used by controllers to access the active websocket session for the device.
    public static WebSocketSession getActiveSessionForDevice(Long deviceId){
        return deviceIdsWsSessions.get(deviceId);
    }

    private void authenticateDevice(String connectionToken, WebSocketSession session) throws IOException {
        Device device = deviceRepository.findOneByConnectionToken(connectionToken);
        if(device == null){
            session.close();
        } else {
            deviceIdsWsSessions.put(device.getId(), session);
            device.setActive(true);
            deviceRepository.save(device);

//            TextMessage msg = new TextMessage(device.getUser().getEmail() + " is successfully authenticated.");
//            session.sendMessage(msg);
        }
    }
}
