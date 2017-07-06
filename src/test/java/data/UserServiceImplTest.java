package data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.RootContextConfig;
import pojo.User;
import service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RootContextConfig.class)
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;

	@Test
	public void test() {
		User user = userService.findOne(33);
		System.out.println(user);
	}

}
