package service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import pojo.User;

public interface UserService {
	
	public List<User> findAllUsers();

	@Transactional
	public void deleteUser(String userId);

	public User findOne(String userId);

	@Transactional
	public void updateUser(User user);

	@Transactional
	public void addUser(User user);

	public List<User> findUserListByIds(String[] ids);

	public HSSFWorkbook getExcelByIds(String[] ids,String... path);

	public User findUserByNameAndPassword(String name, String password);

	public User findUserByName(String name);

	public List<User> findAllUsersPages(int pageNum, int pageSize);

}
