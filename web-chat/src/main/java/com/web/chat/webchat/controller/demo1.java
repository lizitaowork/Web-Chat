package com.web.chat.webchat.controller;

import com.web.chat.webchat.model.Greeting;
import com.web.chat.webchat.model.HelloMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * Created by zitao.li on 2018/8/6.
 */
@Controller
public class demo1 {
    @MessageMapping("/demo1/hello/{typeId}")
    @SendTo("/topic/demo1/greetings")
    public Greeting greeting(@DestinationVariable Integer typeId, HelloMessage message, @Headers Map<String, Object> headers) throws Exception {
        return new Greeting(headers.get("simpSessionId").toString(), typeId + "---" + message.getMessage());
    }
}
