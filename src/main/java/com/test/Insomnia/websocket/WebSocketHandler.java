package com.test.Insomnia.websocket;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketHandler extends TextWebSocketHandler {

	private static ArrayList<WebSocketSession> sessions = new ArrayList<>();

	private void broadCast(String message) {
		for (WebSocketSession session : sessions) {
			try {
				session.sendMessage(new TextMessage(message));
				// 比較：javax.websocket.Session.getBasicRemote().sendText( message );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		broadCast(String.format("【%s#%s】%s", session.getAttributes().get("authenticated"),
				session.getId().substring(0, 5) + "...", "Connection 建立了..."));
		// session.getId()之值例如：931dc03b-ddfe-c37e-db8e-88c4d965c866
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String clientMessage = message.getPayload();
		broadCast(String.format("【%s#%s】%s", session.getAttributes().get("authenticated"),
				session.getId().substring(0, 5) + "...", clientMessage));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		broadCast(String.format("【%s#%s】%s", session.getAttributes().get("authenticated"),
				session.getId().substring(0, 5) + "...", "Connection 關閉了..."));
	}

}
