package pojo;

import java.util.Date;

public class Reply {

	private String replyId;
	
	private String discussionId;
	
	private String userId;
	
	private String body;
	
	private Date createTime;
	
	private Reply parentReply;
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Reply getParentReply() {
		return parentReply;
	}

	public void setParentReply(Reply parentReply) {
		this.parentReply = parentReply;
	}
	
	
	
}
