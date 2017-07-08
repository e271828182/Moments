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

}
