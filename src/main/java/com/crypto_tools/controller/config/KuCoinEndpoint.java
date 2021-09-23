package com.crypto_tools.controller.config;

import com.crypto_tools.controller.WebSocketDispatcher;
import com.crypto_tools.exchangapi.ApiServiceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.crypto_tools.exchangapi.ApiServiceGenerator.userId;

//@ServerEndpoint(value = "/marketStream/kucoin")
//@Component
public class KuCoinEndpoint {
    //クライアントとのセッションで、これを通してクライアントにデータ送信
    private static Logger log = LoggerFactory.getLogger(KuCoinEndpoint.class);
    private static final AtomicInteger onLineCount= new AtomicInteger(0);


    /**
     * 接続出来た
     * @PathParam は変数 URI パスフラグメントをメソッド呼び出しへマップできるようにします。
     */
    @OnOpen
    public void onOpen(Session session) throws InterruptedException, IOException {
        //新しい接続ごとにID生成して、送信を行う
        String userId;
        userId = SessionPool.getUserId();
        SessionPool.addSessions(userId,session);
        SessionPool.sendMessage(userId,"userId:"+userId+",");
        log.info("User ID:" + userId + "がKuCoinEndpointに接続しています");
        int cnt = onLineCount.incrementAndGet(); // 在线数加1
        log.info("今の人数は"+cnt);
//        WebSocketDispatcher.sendKuCoinData(userId);
    }

    /**
     * 接続を切られた
     */
    @OnClose
    public void onClose() throws IOException {
        int cnt = onLineCount.decrementAndGet();
        log.info("User ID:" + SessionPool.checkLive() +"の接続が切られました、今の人数は"+cnt);
    }

    /**
     *
     *
     * @param message
     * クライアントからのmessage
     */
    @OnMessage
    public void onMessage(String message) throws IOException, InterruptedException {
        log.info("クライアントからのメッセージ: {}",message);
    }

    /**
     * エラー
     * @param error
     */
    @OnError
    public void onError(Throwable error) throws IOException {
        log.error("エラー発生");
        error.printStackTrace();
    }



}
