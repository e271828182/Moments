package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import pojo.Reply;

public interface ReplyMapper {

	@Insert("insert into reply(reply_id,discussion_id,pid,user_id,body,create_time) "
			+ "values(#{replyId},#{discussionId},#{parentReply.replyId},#{userId},#{body},#{createTime})")
	void save(Reply reply);

	@Select("select * from reply order by create_time desc")
	@Results({
		@Result(id=true, column="reply_id", property="replyId"),
		@Result(id=true, column="discussion_id", property="discussionId"),
		@Result(id=true, column="pid", property="parentReply.replyId"),
		@Result(id=true, column="user_id", property="userId"),
		@Result(id=true, column="create_time", property="createTime")
	})
	List<Reply> findAll();

}
