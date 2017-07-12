package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pojo.User;

public interface UserMapper {
	
	@Select("select * from usertable")
	@Results({
		@Result(id=true, column="user_id", property="userId")		
	})
	public List<User> findAllUsers();

	@Delete("delete from usertable where user_id = #{userId}")
	public void deleteUser(String userId);

	
	@Select("select * from usertable where user_id = #{userId}")
	@Results({
		@Result(id=true, column="user_id", property="userId"),			
	})
	public User findOne(String userId);

	@Update("update usertable set name = #{name}, nickname=#{nickname}, password=#{password}, sex = #{sex}, age = #{age}, "
			+ "birthday = #{birthday}, telephone = #{telephone}, email = #{email}, pic = #{pic} where user_id = #{userId}")
	public void updateUser(User user);

	@Update("insert into usertable(user_id, name, nickname, password, sex, age, birthday, telephone, email, pic) "
			+ "values(#{userId}, #{name}, #{nickname}, #{password}, #{sex}, #{age}, #{birthday}, #{telephone}, #{email}, #{pic})")
	public void addUser(User user);

	public List<User> findUserListByIds(String[] ids);

	@Select("select * from usertable where name = #{name} and password = #{password}")
	@Results({
		@Result(id=true, column="user_id", property="userId"),			
	})
	public User findUserByNameAndPassword(@Param("name")String name, @Param("password")String password);

	@Select("select * from usertable where name = #{name}")
	@Results({
		@Result(id=true, column="user_id", property="userId"),			
	})
	public Object findUserByName(String name);
}
