package service.impl;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import mappers.DiscussionMapper;
import mappers.ReplyMapper;
import mappers.UserMapper;
import pojo.User;
import service.DiscussionService;
import service.ReplyService;
import service.UserService;
import util.CreateExcel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	DiscussionMapper discussionMapper;
	
	@Autowired
	DiscussionService discussionService;
	
	@Override
	public List<User> findAllUsers() {
		return userMapper.findAllUsers();
	}

	@Override
	public void deleteUser(String userId) {
		discussionService.deleteDiscussionByUserId(userId);
		replyService.deleteReplyByUserId(userId);
		userMapper.deleteUser(userId);
		
	}

	@Override
	public User findOne(String userId) {
		return userMapper.findOne(userId);
		
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
		
	}

	@Override
	public void addUser(User user) {
		userMapper.addUser(user);
		
	}

	@Override
	public List<User> findUserListByIds(String[] ids) {
		return userMapper.findUserListByIds(ids);
	}

	@Override
	public HSSFWorkbook getExcelByIds(String[] ids,String... pic_path) {
		List<User> userlist = ids==null?findAllUsers():findUserListByIds(ids);
		CreateExcel<User> excel = new CreateExcel<>(User.class);
		try {
			return excel.getExcel(userlist,pic_path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public User findUserByNameAndPassword(String name, String password) {
		return userMapper.findUserByNameAndPassword(name,password);
	}

	@Override
	public User findUserByName(String name) {
		return userMapper.findUserByName(name);
	}

	@Override
	public List<User> findAllUsersPages(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return userMapper.findAllUsers();
	}
}
