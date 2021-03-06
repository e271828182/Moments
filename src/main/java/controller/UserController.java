package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;

import controller.validation.UserLoginValidGroup;
import exception.CustomException;
import pojo.User;
import service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public ModelAndView UserList(ModelAndView model){
		List<User> usersLise = userService.findAllUsers();
		model.addObject("usersLise", usersLise);
		model.setViewName("thymeleaf/userList");
		return model;
	}
	
	@RequestMapping("/{pagemodel}/pagechange")
	public ModelAndView UserListpage(ModelAndView model,
									@PathVariable("pagemodel") String pagemodle,
									@RequestParam(defaultValue="1") int currpage){
		int pageNum = currpage + ("prepage".equals(pagemodle) ? -1:1);
		int pageSize = 5;
		//获取总数，再设置currpage最大值
		currpage = pageNum<=0?1:pageNum;
		model.addObject("currpage", currpage);
		List<User> usersLise = userService.findAllUsersPages(pageNum,pageSize);
		model.addObject("usersLise", usersLise);
		model.setViewName("thymeleaf/userList");
		return model;
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam String userId, @Value(value = "${user.pic.filepath}") String path){
		User user = userService.findOne(userId);
		String oldPic = user.getPic();
		
		if(!StringUtils.isEmpty(oldPic)){
			File oldfile = new File(path+oldPic);
			if(oldfile.exists()) oldfile.delete();
		}
		userService.deleteUser(userId);
		return "forward:/list";
	}
	
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(ModelAndView model,@RequestParam String userId){
		User user = userService.findOne(userId);
		model.addObject("user", user);
		model.setViewName("thymeleaf/userEdit");
		return model;
	}
	
	@RequestMapping("/edit")
	public String edit(@ModelAttribute("user") User user,
						@Value(value = "${user.pic.filepath}") String path,
						MultipartFile user_pic) throws IOException{

		String newFileName = handelPic(user_pic,path);
		if(!"".equals(newFileName) && newFileName!=null){
			user = userService.findOne(user.getUserId());
			String oldPic = user.getPic();
			
			if(!StringUtils.isEmpty(oldPic)){
				File oldfile = new File(path+oldPic);
				if(oldfile.exists()) oldfile.delete();
			}
			user.setPic(newFileName);
			userService.updateUser(user);			
		}
		return "redirect:/list";
	}
	
	private String handelPic(MultipartFile user_pic,String path) throws IOException{
		String picName = user_pic.getOriginalFilename();
		if("".equals(picName)||picName==null)return "";
		String picSuffix = picName.substring(picName.lastIndexOf("."));
		UUID randomUUID = UUID.randomUUID();
		File newfile = new File(path+randomUUID+picSuffix);
		try (InputStream in = user_pic.getInputStream();
			FileOutputStream out = new FileOutputStream(newfile);){		
			FileChannel channel = out.getChannel();
			byte[] b = new byte[64];
			int n = -1;
			while((n=in.read(b))!=-1){
				ByteBuffer buffer = ByteBuffer.wrap(b, 0, n);
				channel.write(buffer);
			}
			out.flush();
		}
		return randomUUID+picSuffix;
	}
	
	@RequestMapping("/add")
	public String add(@Validated(value={UserLoginValidGroup.class}) @ModelAttribute("user") User user,BindingResult bindingResult,
						MultipartFile user_pic,
						@Value(value = "${user.pic.filepath}") String path,
						Model model) throws IOException, CustomException{
		
		if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			model.addAttribute("allErrors", allErrors);
			return "thymeleaf/userAdd";
		}
		
		if(user_pic!=null){
			String newFileName = handelPic(user_pic,path);
			user.setPic(newFileName);
		}
		user.setUserId(UUID.randomUUID().toString());
		userService.addUser(user);
		return "redirect:/list";
	}
	
	@RequestMapping("/img")
	public void img(HttpServletResponse response,
						@Value(value = "${user.pic.filepath}") String path,
						@RequestParam String userId){
		User user = userService.findOne(userId);
		if(user==null || user.getPic()==null || "".equals(user.getPic()))return;
		response.setContentType("image/jpeg");
		try(FileInputStream in = new FileInputStream(path+user.getPic());
			ServletOutputStream out = response.getOutputStream();) {
			FileChannel channel = in.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
			channel.read(buffer);
			byte[] array = buffer.array();
			out.write(array);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/download")
	public void download(HttpServletResponse response,
						@Value(value = "${user.pic.filepath}") String path,
						@RequestParam(value="userId", required=false) String[] ids) throws IOException{
		HSSFWorkbook excel = userService.getExcelByIds(ids,path);
		if(excel!=null){
			response.setContentType("application/octet-stream; charset=utf-8");
			String filename = "人员信息.xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			excel.write(response.getOutputStream());
		}else{
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("<h1 style='color:red;text-align:center'>文件下载失败，sorry！！！</h1>");
			response.setHeader("refresh","3;url=/list");
		}
	}
}
