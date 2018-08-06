package com.web.chat.webchat.controller;

import com.web.chat.webchat.model.Authentication;
import com.web.chat.webchat.model.Greeting;
import com.web.chat.webchat.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Description:
 * Author: 杰明Jamin
 * Date: 2017/11/4 11:49
 */
@Controller
public class GreetingController2 {

    @MessageMapping("/demo2/hello/{typeId}")
    @SendTo("/topic/demo2/greetings")
    public Greeting greeting(HelloMessage message, StompHeaderAccessor headerAccessor) throws Exception {

        Authentication user = (Authentication) headerAccessor.getUser();

        String sessionId = headerAccessor.getSessionId();

        return new Greeting(user.getName(), "sessionId: " + sessionId + ", message: " + message.getMessage());
    }

}
