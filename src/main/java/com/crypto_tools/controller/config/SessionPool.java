package com.crypto_tools.controller.config;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionPool {
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static void close(String sessionId) throws IOException{
        Session session = SessionPool.sessions.get(sessionId);
        if(session != null){
            sessions.get(sessionId).close();
        }
    }

    public static void sendMessage(String sessionId, String message) {
        sessions.get(sessionId).getAsyncRemote().sendText(message);
    }

    public static void sendMessage(String message) {
       for(String sessionId : SessionPool.sessions.keySet())
       {
           SessionPool.sessions.get(sessionId).getAsyncRemote().sendText(message);
       }
    }



}
