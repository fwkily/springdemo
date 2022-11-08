package com.fwkily.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/test/{name}")
@Component
public class WsController {

    @OnMessage
    public void test(Session session, String message, @PathParam("name") String name){
        System.out.println("session:" + session);
        System.out.println("message:" + message);
        System.out.println("name:" + name);
    }


}
