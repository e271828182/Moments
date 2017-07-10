package websocket;

import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class ReplyHandler extends AbstractWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ReplyHandler.class);
	
	public static CopyOnWriteArraySet<WebSocketSession> webSocketSet = new CopyOnWriteArraySet<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		webSocketSet.add(session);
	}

	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("Received message: " + message.getPayload());
		System.out.println(message.toString());
		for (WebSocketSession webSocketSession : webSocketSet) {
			webSocketSession.sendMessage(new TextMessage("有回复，请查看"));
		}
		
	}
	
}
