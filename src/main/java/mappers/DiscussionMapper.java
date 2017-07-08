package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import pojo.Discussion;

public interface DiscussionMapper {

//	@Select("select * from discussion order by up_num limit 0 , 9")
	List<Discussion> findTopTen();

	@Select("select * from discussion where discussion_id = #{discussionId}")
	Discussion findOne(String discussionId);

	@Insert("insert into discussion(discussion_id,user_id,topic,name,body,create_time,last_update_time) "
			+ "values (#{discussionId},#{userId},#{topic},#{name},#{body},#{createTime},#{lastUpdateTime})")
	void save(Discussion discussion);


}
