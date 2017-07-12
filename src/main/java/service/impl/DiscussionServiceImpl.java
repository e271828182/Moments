package service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mappers.DiscussionMapper;
import mappers.UserMapper;
import pojo.Discussion;
import pojo.User;
import service.DiscussionService;

@Service
public class DiscussionServiceImpl implements DiscussionService {
	
	@Autowired
	DiscussionMapper discussionMapper;
	@Autowired
	private UserMapper userMapper;

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


	

}
