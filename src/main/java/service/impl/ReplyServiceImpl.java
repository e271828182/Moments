package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mappers.DiscussionMapper;
import mappers.ReplyMapper;
import pojo.Reply;
import pojo.User;
import service.ReplyService;
import service.UserService;
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	UserService userMapper;
	

	@Override
	public void save(Reply reply) {
		String userId = reply.getUserId();
		reply.setName(userMapper.findOne(userId).getName());		
		replyMapper.save(reply);
	}

	@Override
	public List<Reply> findAll(String discussionId) {
		return replyMapper.findAll(discussionId);
	}

	@Override
	public List<User> findNotifyUsers(String replyId) {
		Reply reply = replyMapper.findOne(replyId);
		
		Reply parentReply;
		List<User> users = new ArrayList<>();
		while((parentReply= reply.getParentReply())!=null){
			User user = userMapper.findOne(parentReply.getUserId());
			users.add(user);
			reply = parentReply;
		}
		return users;
	}

	@Override
	public void deleteReplyById(String replyId) {
		Reply reply = replyMapper.findReplyByIdForUpdate(replyId);
		if(reply.getParentReply()!=null){
			deleteReplyById(reply.getParentReply().getReplyId());
		}
		replyMapper.deleteReplyById(reply.getReplyId());
		
	}

	@Override
	public void deleteReplyByUserId(String userId) {
		List<Reply> replys = replyMapper.findReplysByUserId(userId);
		for (Reply reply : replys) {
			deleteReplyById(reply.getReplyId());
		}
	}
	
	

}
