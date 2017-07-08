package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.Discussion;
import service.DiscussionService;

@Controller
public class IndexController {
	
	@Autowired
	DiscussionService discussionService;

	@RequestMapping("/")
	public String toIndex(Model model){
		List<Discussion> discussions = discussionService.findTopTen();
		model.addAttribute("discussions",discussions);		
		return "thymeleaf/index";
	}
	
	@RequestMapping("index/{discussionId}")
	public String toDiscussion(@PathVariable("discussionId") String discussionId,
								Model model){
		Discussion discussion = discussionService.findOne();
		model.addAttribute("discussion",discussion);		
		return "thymeleaf/discussion";
	}
	
}
