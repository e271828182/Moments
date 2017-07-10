package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pojo.Discussion;

public interface DiscussionMapper {

//	@Select("select * from discussion order by up_num limit 0 , 9")
	List<Discussion> findTopTen();

//	@Select("select * from discussion where discussion_id = #{discussionId}")
	Discussion findOne(String discussionId);

	@Insert("insert into discussion(discussion_id,user_id,topic,name,body,create_time,last_update_time,status,up_Num,down_num,reply_num) "
			+ "values (#{discussionId},#{userId},#{topic},#{name},#{body},#{createTime},#{lastUpdateTime},#{status},#{upNum},#{downNum},#{replyNum})")
	void save(Discussion discussion);

	@Update("update discussion set user_id=#{userId}, topic=#{topic}, name=#{name}, body=#{body}, last_update_time=#{lastUpdateTime}, status=#{status},"
			+ "up_Num=#{upNum}, down_num=#{downNum}, reply_num=#{replyNum} where discussion_id=#{discussionId}")
	void update(Discussion discussion);


}
