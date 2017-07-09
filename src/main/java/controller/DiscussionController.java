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
import pojo.Reply;
import pojo.User;
import service.DiscussionService;
import service.ReplyService;

@Controller
public class DiscussionController {
	
	@Autowired
	DiscussionService discussionService;
	
	@Autowired
	ReplyService replyService;

	@RequestMapping("/")
	public String toIndex(Model model){
		List<Discussion> discussions = discussionService.findTopTen();
		model.addAttribute("discussions",discussions);		
		return "thymeleaf/index";
	}
	
	@RequestMapping("/discussion/toDiscussion")
	public String toDiscussion( String discussionId,
								Model model){
		Discussion discussion = discussionService.findOne(discussionId);
		model.addAttribute("discussion",discussion);
		List<Reply> replys = replyService.findAll(discussionId);
		model.addAttribute("replys",replys);
		return "thymeleaf/showDiscussion";
	}
	
	@RequestMapping("/discussion/toCreate")
	public String toCreate(Model model,
							HttpSession session){
		User user = (User)session.getAttribute("userMsg");
		if(user==null) return "redirect:/toLogin";
		model.addAttribute("discussionId",UUID.randomUUID().toString());
		return "thymeleaf/createDiscussion";
	}
	
	@RequestMapping("/discussion/toCheck")
	public String toCheck(Discussion discussion,
							Model model,
							HttpSession session){
		if(discussion.getCreateTime()==null)discussion.setCreateTime(new Date());
		discussion.setLastUpdateTime(new Date());
		User user = (User)session.getAttribute("userMsg");
		if(user==null) return "redirect:/toLogin";
		
		discussion.setUserId(user.getUserId());
		discussion.setStatus(0);
		discussion.initdata();
		discussionService.save(discussion);
		model.addAttribute("discussion",discussion);
		return "thymeleaf/previewDiscussion";
	}
	
	@RequestMapping("/discussion/toSave")
	public String toSave(String discussionId){
		Discussion discussion = discussionService.findOne(discussionId);
		discussion.setStatus(1);
		discussionService.update(discussion);
		return "redirect:/";
	}
	
}
