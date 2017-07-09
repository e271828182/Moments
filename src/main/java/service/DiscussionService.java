package service;

import java.util.List;

import pojo.Discussion;

public interface DiscussionService {

	List<Discussion> findTopTen();


	void save(Discussion discussion);


	Discussion findOne(String discussionId);


	void update(Discussion discussion);


	Integer upNumAdd(String discussionId);


	Integer downNumAdd(String discussionId);



}
