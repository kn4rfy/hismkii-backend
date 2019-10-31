package com.hisd3.hismk2.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@Configuration
@EnableWebSocketMessageBroker
 class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{
    @Override
     void configureMessageBroker(MessageBrokerRegistry config)
    {
        config.enableSimpleBroker("/channel")
        config.setApplicationDestinationPrefixes("/app")
    }

    @Override
     void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS()
    }
}