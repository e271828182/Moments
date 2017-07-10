package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mappers.ReplyMapper;
import pojo.Reply;
import service.ReplyService;
import service.UserService;
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	UserService userService;

	@Override
	public void save(Reply reply) {
		String userId = reply.getUserId();
		reply.setName(userService.findOne(userId).getName());		
		replyMapper.save(reply);
	}

	@Override
	public List<Reply> findAll(String discussionId) {
		return replyMapper.findAll(discussionId);
	}

}
