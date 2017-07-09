package controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pojo.Reply;
import service.DiscussionService;
import service.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	DiscussionService discussionService;
	
	@Autowired
	ReplyService replyService;

	@RequestMapping("/upNumAdd")
	public void upNumAdd(HttpServletResponse response,
						String userId,
						String discussionId){
		try {
			response.getWriter().write(discussionService.upNumAdd(discussionId).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/downNumAdd")
	public void downNumAdd(HttpServletResponse response,
						String userId,
						String discussionId){
		try {
			response.getWriter().write(discussionService.downNumAdd(discussionId).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/reply/add")
	public String replyAdd(Reply reply,
						Model model){
		if("".equals(reply.getUserId())){
			model.addAttribute("allErrors", new String[]{"对不起，您还未登录，不能评论！"});
			return "forward:/discussion/toDiscussion";
		}
		reply.setCreateTime(new Date());
		reply.setReplyId(UUID.randomUUID().toString());
//		Reply parent = new Reply();parent.setReplyId("1");
//		reply.setParentReply(parent);
		replyService.save(reply);
		return "forward:/discussion/toDiscussion";
	}
}
