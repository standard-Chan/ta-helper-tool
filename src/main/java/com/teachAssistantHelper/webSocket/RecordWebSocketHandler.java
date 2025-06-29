package com.teachAssistantHelper.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// RecordWebSocketHandler.java
public class RecordWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        String roomKey = getRoomKeyFromUri(uri);
        session.getAttributes().put("roomKey", roomKey);

        Set<WebSocketSession> sessions = roomSessions.computeIfAbsent(roomKey, k -> ConcurrentHashMap.newKeySet());
        sessions.add(session);

        // 접속자 수 브로드캐스트
        broadcastUserCount(roomKey);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomKey = (String) session.getAttributes().get("roomKey");
        if (roomKey != null) {
            Set<WebSocketSession> sessions = roomSessions.get(roomKey);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    roomSessions.remove(roomKey);
                } else {
                    // 접속자 수 갱신 브로드캐스트
                    broadcastUserCount(roomKey);
                }
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomKey = (String) session.getAttributes().get("roomKey");
        if (roomKey == null) return;

        Set<WebSocketSession> sessions = roomSessions.get(roomKey);
        if (sessions == null) return;

        for (WebSocketSession s : sessions) {
            if (s.isOpen() && !s.equals(session)) {
                s.sendMessage(message); // 변경 내용을 그대로 다른 사용자에게 전달
            }
        }
    }

    private String getRoomKeyFromUri(String uri) {
        // 예: ws://localhost:8080/ws/classRecord?classId=1&weekNo=3
        URI parsed = URI.create(uri);
        String query = parsed.getQuery();
        Map<String, String> params = Arrays.stream(query.split("&"))
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        return params.get("classId") + "_" + params.get("weekNo");
    }

    private void broadcastUserCount(String roomKey) throws Exception {
        Set<WebSocketSession> sessions = roomSessions.get(roomKey);
        if (sessions == null) return;

        String message = String.format("{\"type\":\"USER_COUNT\", \"count\": %d}", sessions.size());
        TextMessage msg = new TextMessage(message);

        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(msg);
            }
        }
    }
}
