package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import pojo.Reply;
import pojo.User;

public interface ReplyMapper {

	@Insert("insert into reply(reply_id,discussion_id,pid,user_id,body,create_time,name) "
			+ "values(#{replyId},#{discussionId},#{parentReply.replyId},#{userId},#{body},#{createTime},#{name})")
	void save(Reply reply);

	@Select("select * from reply where discussion_id=#{discussionId} order by create_time ")
	@Results({
		@Result(id=true, column="reply_id", property="replyId"),
		@Result( column="discussion_id", property="discussionId"),
		@Result( column="pid", property="parentReply.replyId"),
		@Result( column="user_id", property="userId"),
		@Result( column="create_time", property="createTime")
	})
	List<Reply> findAll(String discussionId);

	@Select("select * from reply where reply_id=#{replyId}")
	@Results({
		@Result(id=true, column="reply_id", property="replyId"),
		@Result( column="discussion_id", property="discussionId"),
		@Result( column="pid", property="parentReply.replyId"),
		@Result( column="user_id", property="userId"),
		@Result( column="create_time", property="createTime")
	})
	Reply findOne(String replyId);

//	@Select("select * from reply where reply_id=#{replyId}")
//	List<User> findNotifyUsers(String replyId);

	



}
