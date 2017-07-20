package service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mappers.DiscussionMapper;
import mappers.ReplyMapper;
import mappers.UserMapper;
import pojo.Discussion;
import pojo.Reply;
import pojo.User;
import service.DiscussionService;
import service.ReplyService;

@Service
public class DiscussionServiceImpl implements DiscussionService {
	
	@Autowired
	DiscussionMapper discussionMapper;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	ReplyService replyService;
	@Override
	public List<Discussion> findTopTen() {
		return discussionMapper.findTopTen();
	}

	@Override
	public Discussion findOne(String discussionId) {
		return discussionMapper.findOne(discussionId);
	}

	@Override
	public void save(Discussion discussion) {
		discussionMapper.save(discussion);

	}

	@Override
	public void update(Discussion discussion) {
		discussionMapper.update(discussion);
		
	}

	@Override
	public Integer upNumAdd(String discussionId) {
		Discussion discussion = findOne(discussionId);
		Integer UpNum = discussion.increaseUpNum();
		update(discussion);
		return UpNum;
	}

	@Override
	public Integer downNumAdd(String discussionId) {
		Discussion discussion = findOne(discussionId);
		Integer downNum = discussion.increasedownNum();
		update(discussion);
		return downNum;
	}

	@Override
	public User findUserByDiscussionId(String discussionId) {
		Discussion discussion = discussionMapper.findOne(discussionId);
		String userId = discussion.getUserId();
		return userMapper.findOne(userId);
	}

	@Scheduled(cron = "${jobs.schedule.cron:0 40 14 ? * *}")
    public void deleteStaleDiscussions(){
		System.out.println("定时执行成功");
    }

	@Override
	public void deleteDiscussionByUserId(String userId) {
		List<Discussion> discussions = discussionMapper.findDiscussionByUserId(userId);
		for (Discussion discussion : discussions) {
			List<Reply> replys = replyMapper.findAll(discussion.getDiscussionId());
			for (Reply reply : replys) {
				if(userId!=reply.getUserId())
				replyService.deleteReplyById(reply.getReplyId());
			}
			
			
			discussionMapper.deleteDiscussionById(discussion.getDiscussionId());
			
		}
		
	}


	

}
