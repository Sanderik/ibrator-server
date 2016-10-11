package com.sanderik.controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;

public class VibrateMessageHandler extends TextWebSocketHandler {

    private static WebSocketSession currentSession;

    // TODO : Check if this if safe?
    static void sendMsg(String payload) throws IOException {
        currentSession.sendMessage(new TextMessage(payload));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        currentSession = session;
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        session.sendMessage(msg);
    }
}
