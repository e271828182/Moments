package service;

import java.util.List;

import pojo.Reply;

public interface ReplyService {

	void save(Reply reply);

	List<Reply> findAll(String discussionId);

}
