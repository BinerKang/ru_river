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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.biner.ru.model.ChatInfo;
import com.biner.ru.model.IpInfo;
import com.biner.ru.util.MaxMindUtils;

public class SpringWebSocketHandler extends TextWebSocketHandler {
    
	private static final Map<String, WebSocketSession> users;
	
	private static final Map<String, IpInfo> chatMembers;
    
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    
    static {
        users = new ConcurrentHashMap<String, WebSocketSession>();
        chatMembers = new ConcurrentHashMap<String, IpInfo>();
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
        logger.info("connect to the websocket success......当前数量:" + users.size());
        String ip = session.getRemoteAddress().getAddress().getHostAddress();
        IpInfo ipInfo = null;
        try {
			ipInfo = MaxMindUtils.getIpInfo(ip);
		} catch (Exception e) {
			ipInfo = new IpInfo();
			ipInfo.setIp(ip);
			logger.error("Request Freegeoip has error:::", e);
		}
        ChatInfo chat = null;
        // 发送欢迎消息以及成员列表给在线的人
        chatMembers.put(sessionId, ipInfo);
        if (!users.isEmpty()) {
        	chat = new ChatInfo(chatMembers.values(), null, ipInfo, ChatInfo.JOIN);
        	sendMessageToAll(chat);
        }
        // 发送成员列表给新加入的人
        users.put(sessionId, session);
        chat = new ChatInfo(chatMembers.values(), null, ipInfo, ChatInfo.JOIN);
        sendMessageBySessionId(sessionId, chat);
    }
    
    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String sessionId = (String) session.getAttributes().get("WEBSOCKET_SESSIONID");
        users.remove(sessionId);
        IpInfo ipInfo = chatMembers.get(sessionId);
        chatMembers.remove(sessionId);
        // 发送离开消息给在线的人
        if (!chatMembers.isEmpty()) {
        	ChatInfo chat = new ChatInfo(chatMembers.values(), null, ipInfo, ChatInfo.LEAVE);
        	sendMessageToAll(chat);
        }
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String sessionId = (String) session.getAttributes().get("WEBSOCKET_SESSIONID");
    	ChatInfo chat = new ChatInfo(null, message.getPayload(), chatMembers.get(sessionId), ChatInfo.MSG);
    	sendMessageToAll(chat);
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
    
    public void sendMessageBySessionId(String sessionId, ChatInfo chat) {
    	sendMessageBySessionId(sessionId, JSONObject.toJSONString(chat, SerializerFeature.DisableCircularReferenceDetect));
    }
    
    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageBySessionId(String sessionId, String message) {
    	WebSocketSession user = users.get(sessionId);
        try {
            if (user != null && user.isOpen()) {
                user.sendMessage(new TextMessage(message));
            }
        } catch (IOException e) {
            logger.error("WebSocket send message to:" + sessionId + " has error.", e);
        }
    }
    
    public void sendMessageToAll(ChatInfo chat) {
    	sendMessageToAll(JSONObject.toJSONString(chat, SerializerFeature.DisableCircularReferenceDetect));
    }
    
    /**
     * 给所有在线用户发送消息
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
