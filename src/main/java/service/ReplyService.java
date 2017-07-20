package service;

import java.util.List;

import pojo.Reply;
import pojo.User;

public interface ReplyService {

	void save(Reply reply);

	List<Reply> findAll(String discussionId);

	List<User> findNotifyUsers(String replyId);
	
	void deleteReplyById(String replyId);

	void deleteReplyByUserId(String userId);



}
