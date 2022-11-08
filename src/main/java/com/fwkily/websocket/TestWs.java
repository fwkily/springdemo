package com.fwkily.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/fwkTest")
@Slf4j
public class TestWs {

    @OnOpen
    public void test(Session session){
        log.info("成功:{}",session.getId());
//        log.info("session:{}", JSONObject.toJSONString(session));
    }

    @OnClose
    public void close(Session session){
        log.info("断开:{}",session.getId());
//        log.info("session:{}", JSONObject.toJSONString(session));
    }

    @OnMessage
    public void messsage(Session session,String message) throws IOException {
        log.info("id:{}",session.getId());
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        remote.sendText("你好！fwk！");


    }




}
