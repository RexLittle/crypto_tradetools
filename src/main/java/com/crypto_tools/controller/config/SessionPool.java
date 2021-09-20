package com.crypto_tools.controller.config;

import com.crypto_tools.controller.WebSocketDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SessionPool {
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static Logger log = LoggerFactory.getLogger(SessionPool.class);
    private static String userId;

    /**
     * セッションを追加する
     * @param userId
     * @param session
     */
    public static void addSessions(String userId,Session session) {
        if (havingSession(userId)) {
            userId = getUserId();
        }
        SessionPool.sessions.put(userId,session);
    }
    
    public static String getUserId(){
        userId = "";
        for (int i = 0; i < 6; i++) {
            userId += (char)(Math.random()*26+'a');
            userId +=  String.valueOf(new Random().nextInt(9));
        }
        
        return userId;
    }

    /**
     * セッションID既にmapの中にあるかの確認
     * @param userId
     * @return
     */
    public static boolean havingSession(String userId){
        for(String userId_loop : SessionPool.sessions.keySet())
        {
            if(userId == userId_loop){
                return true;
            }
        }

        return false;
    }
    public static void close(String userId){
        try{
            Session session = SessionPool.sessions.get(userId);
            if(session != null){
                sessions.get(userId).close();
                SessionPool.sessions.remove(userId);
            }
        } catch(IOException e){
            log.info("セッション:{"+userId+"}閉じるのが失敗しました");
        }

    }

    /**
     * 特定配信
     * @param userId
     * @param message
     */
    public static void sendMessage(String userId, String message) {

            Session session = sessions.get(userId);
            synchronized(session) {
                try{
                session.getBasicRemote().sendText(message);
//            session.getAsyncRemote().sendText(message);
                } catch(IOException e){
                    log.info("セッション:{"+userId+"}が切断されました");
                }
            }


    }

    /**
     * 全員ping配信
     */
    public static String checkLive()  {
            for(String userId : SessionPool.sessions.keySet())
        {
                if(!SessionPool.sessions.get(userId).isOpen()){
                    log.info("セッション:{"+userId+"}が切断されました");
                    try{
                        WebSocketDispatcher.closeWBClient_Exchange(userId);
                    }catch(IOException d){
                        d.printStackTrace();
                    }
                    SessionPool.sessions.remove(userId);
                    return userId;
                }




        }
        return null;
    }



}


