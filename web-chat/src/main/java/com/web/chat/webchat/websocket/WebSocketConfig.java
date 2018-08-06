package com.web.chat.webchat.websocket;

import com.web.chat.webchat.model.Authentication;
import com.web.chat.webchat.model.Users;
import org.apache.catalina.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;

import java.util.Map;

/**
 * Created by zitao.li on 2018/8/6.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * websocket对外暴露服务，并允许跨域访问
         */
        registry.addEndpoint("/webSocket/webSocketEndPoint").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * 用户可以订阅来自"/topic"和"/user"的消息，
         * 在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
         *
         * 在此项目中，使用/topic来群发，使用/user来实现点对点发送
         *
         * 客户端只可以订阅这两个前缀的主题
         */
        registry.enableSimpleBroker("/topic","/user");

        /*
         * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给Controller
         */
        registry.setApplicationDestinationPrefixes("/app");

        /*
         * 客户端点对点方式时，发送消息时的前缀需为"/user"
         */
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                //1. 判断是否首次连接请求
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    //2. 验证是否登录
                    String username = accessor.getNativeHeader("username").get(0);
                    String password = accessor.getNativeHeader("password").get(0);

                    for (Map.Entry<String, String> entry : Users.USERS_MAP.entrySet()) {
                        if (entry.getKey().equals(username) && entry.getValue().equals(password)) {
                            //验证成功,登录
                            Authentication user = new Authentication(username); // access authentication header(s)}
                            accessor.setUser(user);
                            return message;
                        }
                    }
                    return null;
                }

                //不是首次连接，已经成功登陆
                return message;
            }
        });
    }
}
