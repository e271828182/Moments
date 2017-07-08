package pojo;


public class Reply {

	private String replyId;
	
	private String body;
	
	private String createTime;
	
	private Reply parentReply;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Reply getParentReply() {
		return parentReply;
	}

	public void setParentReply(Reply parentReply) {
		this.parentReply = parentReply;
	}
	
	
	
}
