package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mappers.ReplyMapper;
import pojo.Reply;
import service.ReplyService;
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	ReplyMapper replyMapper;

	@Override
	public void save(Reply reply) {

		replyMapper.save(reply);
	}

	@Override
	public List<Reply> findAll(String discussionId) {
		return replyMapper.findAll();
	}

}
