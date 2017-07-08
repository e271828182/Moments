package controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@RequestMapping("/index/toDiscussion")
	public String toDiscussion( String discussionId,
								Model model){
		Discussion discussion = discussionService.findOne(discussionId);
		model.addAttribute("discussion",discussion);		
		return "thymeleaf/showDiscussion";
	}
	
	@RequestMapping("/index/toCreate")
	public String toCreate(Model model){
		model.addAttribute("discussionId",UUID.randomUUID().toString());
		return "thymeleaf/createDiscussion";
	}
	
	@RequestMapping("/index/toCheck")
	public String toCheck(Discussion discussion,
							Model model,
							HttpSession session){
		if(discussion.getCreateTime()==null)discussion.setCreateTime(new Date());
		discussion.setLastUpdateTime(new Date());
		discussion.setUserId("0001");
		model.addAttribute("discussion",discussion);
		session.setAttribute("discussion", discussion);
		return "thymeleaf/previewDiscussion";
	}
	
	@RequestMapping("/index/toSave")
	public String toSave(HttpSession session){
		Discussion discussion = (Discussion)session.getAttribute("discussion");
		discussionService.save(discussion);
		session.removeAttribute("discussion");
		return "redirect:/";
	}
	
}
