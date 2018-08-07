package com.web.chat.webchat.controller;

import com.web.chat.webchat.model.Authentication;
import com.web.chat.webchat.model.Greeting;
import com.web.chat.webchat.model.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Created by zitao.li on 2018/8/6.
 */
@Controller
public class GreetingController3 {

    private final SimpMessagingTemplate messagingTemplate;

    /*
     * 实例化Controller的时候，注入SimpMessagingTemplate
     */
    @Autowired
    public GreetingController3(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/demo3/hello/{destUsername}")
    @SendToUser("/demo3/greetings")
    public Greeting greeting(@DestinationVariable String destUsername, HelloMessage message, StompHeaderAccessor headerAccessor) throws Exception {

        Authentication user = (Authentication) headerAccessor.getUser();

        String sessionId = headerAccessor.getSessionId();

        Greeting greeting = new Greeting(user.getName(), message.getMessage());

        /*
         * 对目标进行发送信息
         */
        messagingTemplate.convertAndSendToUser(destUsername, "/demo3/greetings", greeting);

        return new Greeting("友情提示", "消息发送成功");
    }

}
