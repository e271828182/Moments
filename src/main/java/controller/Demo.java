package controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.User;

@Controller
public class Demo {

	@RequestMapping("/getdemo")
	public String getDemo(Model model){
		User u = new User();
		u.setAge(10);
		u.setUserId("aa");
		u.setName("fasf");
		u.setBirthday(new Date());
		model.addAttribute("user", u);
		return "test/demo";
	}
}
