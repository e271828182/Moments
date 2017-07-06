package service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import pojo.User;

public interface UserService {
	
	public List<User> findAllUsers();

	@Transactional
	public void deleteUser(Integer id);

	public User findOne(Integer id);

	@Transactional
	public void updateUser(User user);

	@Transactional
	public void addUser(User user);

	public List<User> findUserListByIds(Integer[] ids);

	public HSSFWorkbook getExcelByIds(Integer[] ids,String... path);

}
