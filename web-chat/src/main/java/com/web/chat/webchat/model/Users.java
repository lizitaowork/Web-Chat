package com.web.chat.webchat.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟用户
 */
public class Users {

    public static final Map<String, String> USERS_MAP = new HashMap<String, String>();

    static {
        USERS_MAP.put("admin", "admin");
        USERS_MAP.put("jamin", "jamin");
        USERS_MAP.put("allen", "allen");
        USERS_MAP.put("james", "james");
    }
}
