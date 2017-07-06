package controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

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
    public ModelAndView login(ModelAndView model,
    					String name, 
    					String password,
    					String vcode,
    					HttpSession session){
    	System.out.println(name+":"+password+":"+vcode);
    	System.out.println((String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
    	model.setViewName("index");
    	return model;
    }
}
