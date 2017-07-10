package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mappers.DiscussionMapper;
import pojo.Discussion;
import service.DiscussionService;

@Service
public class DiscussionServiceImpl implements DiscussionService {
	
	@Autowired
	DiscussionMapper discussionMapper;

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

	

}
