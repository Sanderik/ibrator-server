package com.sanderik.configurations;

import com.sanderik.controllers.VibrateMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(vibrateMessageHandler(), "/ws/vibrate");
    }

    @Bean
    public WebSocketHandler vibrateMessageHandler(){
        return new VibrateMessageHandler();
    }
}
