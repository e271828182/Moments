package pojo;

import java.util.Date;
import java.util.List;

public class Discussion {

	private String discussionId;
	
	private String userId;
	
	private String topic;//分类
	
	private String name;
	
	private String body;
	
	private Date createTime;
	
	private Date lastUpdateTime;
	
	private Integer upNum;
	
	private Integer downNum;
	
	private Integer replyNum;
	
	private Integer status;//0是草稿，1表示已经发布，2表示时间太长不允许回复
		
	private List<Reply> replys;
	
	public void initdata(){
		upNum = 0;
		downNum = 0;
		replyNum = 0;
		System.out.println("初始化成功");
	}
	
	public Integer increaseUpNum(){
		return ++upNum;
	}
	public Integer increasedownNum(){
		return ++downNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getUpNum() {
		return upNum;
	}

	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
	}

	public Integer getDownNum() {
		return downNum;
	}

	public void setDownNum(Integer downNum) {
		this.downNum = downNum;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
}
