package mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pojo.User;

public interface UserMapper {
	
	@Select("select * from usertable")
	public List<User> findAllUsers();

	@Delete("delete from usertable where id = #{id}")
	public void deleteUser(Integer id);

	@Select("select * from usertable where id = #{id}")
	public User findOne(Integer id);

	@Update("update usertable set name = #{name}, sex = #{sex}, age = #{age}, birthday = #{birthday}, pic = #{pic} where id = #{id}")
	public void updateUser(User user);

	@Update("insert into usertable(id,name,sex,age,birthday,pic) values(#{id},#{name},#{sex},#{age},#{birthday},#{pic})")
	public void addUser(User user);

	public List<User> findUserListByIds(Integer[] ids);
}
