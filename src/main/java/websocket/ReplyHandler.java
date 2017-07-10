package websocket;

import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
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
		System.out.println(message.getPayload());
		System.out.println(session.getTextMessageSizeLimit());
		System.out.println(session.getPrincipal());
		for (WebSocketSession webSocketSession : webSocketSet) {
			if(!session.equals(webSocketSession)){
				if(webSocketSession.isOpen())
					webSocketSession.sendMessage(new TextMessage("有回复，请查看"));				
			}
		}
	}

	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		webSocketSet.remove(session);
		System.out.println("移除后的大小：："+webSocketSet.size());
	}
}
