package com.biner.ru.webSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> users;
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    static {
        users = new ConcurrentHashMap<String, WebSocketSession>();
    }
    
    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        String sessionId = (String) session.getAttributes().get("WEBSOCKET_SESSIONID");
        users.put(sessionId, session);
        logger.info("connect to the websocket success......当前数量:" + users.size());
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }
    
    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String sessionId = (String) session.getAttributes().get("WEBSOCKET_SESSIONID");
        logger.info("用户"+sessionId+"已退出！");
        users.remove(sessionId);
        logger.info("剩余在线用户" + users.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override    
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        super.handleTextMessage(session, message);
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
        	session.close();
        }
        String sessionId = (String) session.getAttributes().get("WEBSOCKET_SESSIONID");
        logger.debug("websocket connection closed......");
        users.remove(sessionId);
    }

    public boolean supportsPartialMessages() {
        return false;
    }
    
    
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageBySessionId(String sessionId, String message) {
    	WebSocketSession user = users.get(sessionId);
        try {
            if (user.isOpen()) {
                user.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            logger.error("WebSocket send message to:" + sessionId + " has error.", e);
        }
    }
    
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToAll(String message) {
        for (Entry<String, WebSocketSession> entry : users.entrySet()) {
            try {
                if (entry.getValue().isOpen()) {
                	entry.getValue().sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
            	logger.error("WebSocket send message to:" + entry.getKey() + " has error.", e);
            }
        }
    }

}
