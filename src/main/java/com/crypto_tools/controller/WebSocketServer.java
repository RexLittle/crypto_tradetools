package com.crypto_tools.controller;


import com.crypto_tools.exchangapi.CallBack;
import com.crypto_tools.exchangapi.ExchangeFactory;
import com.crypto_tools.exchangapi.okex.websocket.OkexTickersResp;
import com.crypto_tools.exchangapi.okex.websocket.OkexWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws/asset")
@Component
public class WebSocketServer {

    public void init() {
        System.out.println("websocke加载");
    }

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    private static final AtomicInteger onLineCount= new AtomicInteger(0);

    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();


    /**
     * 接続出来た後働くメソッド
     */
    @OnOpen
    public void onOpen(Session session) throws InterruptedException {

        System.out.println("hello");
        OkexWebSocketClient webSocketClient = (OkexWebSocketClient) ExchangeFactory.createExchangeFactory().createOkexClients().createWebSocketClient();

        webSocketClient.MarketTickersEvent(new CallBack<OkexTickersResp>() {
            @Override
            public void onResponse(OkexTickersResp var1) {
                System.out.println(Arrays.toString(var1.getData()));


            }


            @Override
            public void onFailure(Throwable cause) {
                System.out.println(cause);
            }
        });




        SessionSet.add(session);

        int cnt = onLineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
        SendMessage(session, "连接成功");
        while (true){
            Thread.sleep(2000);
        }
    }

    /**
     * 接続が切った後働くメソッド
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        int cnt = onLineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * クライアントからmessageを受けた後働くメソッド
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);
        SendMessage(session, "收到消息，消息内容："+message);

    }

    /**
     * エラー
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
//            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)",message,session.getId()));
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if(session.isOpen()){
                SendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String message,String sessionId) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session!=null){
            SendMessage(session, message);
        }
        else{
            log.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }
}

