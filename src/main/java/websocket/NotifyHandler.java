package websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import pojo.User;
import service.DiscussionService;
import service.ReplyService;

public class NotifyHandler extends AbstractWebSocketHandler {
	
	@Autowired
	ReplyService replyService;
	
	
	@Autowired
	DiscussionService discussionService;

	private static final Logger logger = LoggerFactory.getLogger(NotifyHandler.class);
	
	public static ConcurrentHashMap<String,WebSocketSession> webSocketMap = new ConcurrentHashMap<>();
	
	public static ConcurrentHashMap<String,List<String>> peindingMessageMap = new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String[] url = session.getUri().toString().split("/");
		String userId = url[3];
		webSocketMap.put(userId,session);
		
		//用户登录时推送挂起的消息
		if(peindingMessageMap.get(userId)!=null){
			for (String message : peindingMessageMap.get(userId)) {
				
				session.sendMessage(new TextMessage(message));
			}
			peindingMessageMap.remove(userId);
		}
	}

	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	
		String[] url = session.getUri().toString().split("/");
		String discussionIdAndreplyId = url[4];
		List<User> users = new ArrayList<>();
		if(discussionIdAndreplyId.length()==36){
			String discussionId = discussionIdAndreplyId;
			User user = discussionService.findUserByDiscussionId(discussionId);
			users.add(user);
		}
		if(discussionIdAndreplyId.length()==72){
			String replyId = discussionIdAndreplyId.substring(36);
			String discussionId = discussionIdAndreplyId.substring(0, 36);
			users = replyService.findNotifyUsers(replyId);
			User user = discussionService.findUserByDiscussionId(discussionId);
			users.add(user);
		}
		String notifyMessage = message.getPayload();
		for (User user : users) {
			WebSocketSession webSocketSession = webSocketMap.get(user.getUserId());
			if(!session.equals(webSocketSession) && webSocketSession!=null){
				webSocketSession.sendMessage(new TextMessage(notifyMessage));
			}else{
				
				//未发送的消息存到map中,下次用户登录则推送
				List<String> messages = peindingMessageMap.get(user.getUserId());
				if(messages==null) messages = new ArrayList<String>();
				messages.add(notifyMessage);
				peindingMessageMap.put(user.getUserId(), messages);
			}
		}
		
	}

	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Collection<WebSocketSession> values = webSocketMap.values();
		values.remove(session);
		logger.info("当前在线用户数：："+webSocketMap.size());
	}
	
	@Scheduled(cron = "${jobs.schedule.cron:0 40 14 ? * *}")
	public void sendMessage() throws IOException{
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = smt.format(new Date());
		System.out.println("定时推送成功:"+format);
		
		Set<Entry<String, WebSocketSession>> entrySet = webSocketMap.entrySet();
		for (Entry<String, WebSocketSession> entry:entrySet) {
			entry.getValue().sendMessage(new TextMessage("定时推送："+format));
		}
		
	}

	
	@Scheduled(fixedDelayString="${jobs.schedule.fixedDelay}")
	public void sendMessage2() throws IOException{
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = smt.format(new Date());
		System.out.println("定时刷新推送成功:"+format);
		Set<Entry<String, WebSocketSession>> entrySet = webSocketMap.entrySet();
		for (Entry<String, WebSocketSession> entry:entrySet) {
			entry.getValue().sendMessage(new TextMessage("刷新推送:"+format));
		}
	}
}
