package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import controller.validation.UserLoginValidGroup;
import controller.validation.UserRegisterValidGroup;
import exception.CustomException;
import pojo.User;
import security.CustomAuthenticationProvider;
import service.UserService;

/**
 * 
 * @ClassName: LoginController
 * @Description: 登陆和校验
 * @author star
 */
@Controller
public class LoginController {

    @Autowired
    private Producer captchaProducer;
    
    @Autowired
	private UserService userService;

    /**
     * 
     * @Title: getKaptchaImage
     * @param @param request
     * @param @param response
     * @param @throws Exception
     * @return void
     * @throws
     */
    @RequestMapping(value = "/captcha-image")
    public void getKaptchaImage(HttpServletRequest request,
            					HttpServletResponse response,
            					HttpSession session) throws Exception {
        //禁止缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println(capText);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
    
    @RequestMapping("/login")
    public String login(Model model,
    					@Validated(value={UserLoginValidGroup.class}) @ModelAttribute("user") User user,BindingResult bindingResult,
    					String vcode,
    					HttpSession session){
    	if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for(ObjectError o : allErrors){
				System.out.println(o.getDefaultMessage());
			}
			model.addAttribute("allErrors", allErrors);
			return "thymeleaf/login";
		}
    	if(!((String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY)).equals(vcode)){
    		model.addAttribute("allErrors", new String[]{"对不起，验证码不正确！"});
    		return "thymeleaf/login";
    	}
    	
    	try {
			Authentication AuthenticationRequest = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword());
			
			AuthenticationManager authenticationManager = new CustomAuthenticationProvider();
			
			Authentication authenticate = authenticationManager.authenticate(AuthenticationRequest);
			
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("allErrors", new String[]{"对不起，用户名或密码不正确！"});
    		return "thymeleaf/login";
		}
    	
    	
    	 System.out.println("Successfully authenticated. Security context contains: " +
                 SecurityContextHolder.getContext().getAuthentication());
    	
    	
    	
    	
    	
    	
//    	User user2 = userService.findUserByNameAndPassword(user.getName(),user.getPassword());
//    	if(user2==null){
//    		model.addAttribute("allErrors", new String[]{"对不起，用户名或密码不正确！"});
//    		return "thymeleaf/login";
//    	}
    	session.setAttribute("userMsg", SecurityContextHolder.getContext().getAuthentication().getCredentials());
    	
    	return "redirect:/";
    }
    
    
    @RequestMapping("/register")
    public String toReigster(Model model,
    					@Validated(value={UserRegisterValidGroup.class}) @ModelAttribute("user") User user,BindingResult bindingResult,
    					String password2,
    					MultipartFile user_pic,
						@Value(value = "${user.pic.filepath}") String path,
    					HttpSession session){
    	if(bindingResult.hasErrors()){
			List<ObjectError> allErrors = bindingResult.getAllErrors();
			for(ObjectError o : allErrors){
				System.out.println(o.getDefaultMessage());
			}
			model.addAttribute("allErrors", allErrors);
			return "thymeleaf/register";
		}
    	if(userService.findUserByName(user.getName()) != null){
    		model.addAttribute("allErrors", new String[]{"用户名已经存在"});
			return "thymeleaf/register";
    	}

    	if(!password2.equals(user.getPassword())){
    		model.addAttribute("allErrors", new String[]{"两次密码不一致"});
			return "thymeleaf/reigster";
    	}
    	if(user_pic!=null){
			String newFileName = handelPic(user_pic,path);
			user.setPic(newFileName);
		}
    	user.setUserId(UUID.randomUUID().toString());
    	
    	userService.addUser(user);
    	session.setAttribute("userMsg", user);
    	
    	return "redirect:/";
    }
    
    private String handelPic(MultipartFile user_pic,String path){
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
		}catch(IOException e){
			e.printStackTrace();
			throw new CustomException("照片上传失败");
		}
		return randomUUID+picSuffix;
	}
}
