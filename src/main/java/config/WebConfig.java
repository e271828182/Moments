package config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import exception.CustomExceptionResolver;
import viewResolver.XlsViewResolver;





@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"controller","marcopolo","controller.impl"})
@EnableScheduling
@PropertySource("classpath:config.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
	
	/*
	 *在使用restController的时候中文会默认显示iso-8859-1格式。 
	 *@requestmapping使用了RequestMappingHandlerAdapter来处理请求，
	 *对于@responsebody，当为string时，会调用默认构造方法里面add的StringHttpMessageConverter，
	 *需要注意的是，这个converter默认的编码是“ISO-8859-1”，所以需要重新定义StringHttpMessageConverter的字符集。
	 */

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
		converters.add(messageConverter);
	}

	@Override
	public Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setProviderClass(HibernateValidator.class);
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:ValidationMessage");
		validator.setValidationMessageSource(messageSource);
		return validator;
	}
	
	
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
	}
	


	/**
	 * 注册自定义日期转换器
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(customFormat1());
		registry.addFormatter(customFormat2());
	}
	
	private DateFormatter customFormat1(){
		DateFormatter dateFormatter = new DateFormatter();
		dateFormatter.setPattern("yyyy-MM-dd HH:mm:ss");
		return dateFormatter;
	}
	private DateFormatter customFormat2(){
		DateFormatter dateFormatter = new DateFormatter();
		dateFormatter.setPattern("yyyy-MM-dd");
		return dateFormatter;
	}
	

	/**
	 * 配置集中转发页面
	 */
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("thymeleaf/index");
//		registry.addViewController("/tologin").setViewName("thymeleaf/login");
//		registry.addViewController("/toAdd").setViewName("thymeleaf/userAdd");
//	}
	/**
	 * 全局异常处理
	 * @return
	 */
//	@Bean
//	public CustomExceptionResolver customExceptionResolver(){
//		return new CustomExceptionResolver();
//	}
	
	/**静态资源 DispatcherServlet配置了映射“/”，拦截所有的路径交给handeradapter处理，导致静态资源无法访问
	 * 方式一：启用默认servlet处理静态资源
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	/**
	 * 方式二：处理静态资源js,css,img,覆盖 WebMvcConfigurerAdapter类，spring-boot中静态资源放在classpath下，常用这种方式
	 * @param registry
	 * @return
	 */
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/WEB-INF/*/**")
//		.addResourceLocations("/WEB-INF/js")
//		.addResourceLocations("/WEB-INF/css");
//	}

	/**
	 * 配置文件上传处理
	 * @return
	 */
	@Bean
	public StandardServletMultipartResolver multipartResolver(){
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
	
	/**
	 * 验证码生成器,配置文件为defaultKaptcha.properties
	 * @return DefaultKaptcha
	 * @throws IOException 
	 */
	@Bean
	public DefaultKaptcha defaultKaptcha() throws IOException{
		Properties propertiesy = new Properties();
		propertiesy.load(this.getClass().getClassLoader().getResourceAsStream("defaultKaptcha.properties"));
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(new Config(propertiesy));
		return defaultKaptcha;
	}
	
	
	/**
	 * thymeleaf模板引擎配置
	 * @param templateEngine
	 * @return
	 */
	
	@Bean
	public ViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine);
		thymeleafViewResolver.setCharacterEncoding("utf-8");
		thymeleafViewResolver.setCache(false);
		thymeleafViewResolver.setViewNames(new String[]{"thymeleaf/*"});
		thymeleafViewResolver.setOrder(0);
		return thymeleafViewResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine(ServletContextTemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	@Bean
	public ServletContextTemplateResolver templateResolver(ServletContext servletContext) {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCharacterEncoding("utf-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
	/**
	 * tiles布局框架视图解析器配置
	 * @param templateEngine
	 * @return
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
	    TilesConfigurer tiles = new TilesConfigurer();
	    tiles.setDefinitions(new String[] {"/WEB-INF/views/layout/tiles.xml"});
	    tiles.setCheckRefresh(true);
	    return tiles;
	  }
	  
	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setViewNames(new String[]{"jsp/*"});
		tilesViewResolver.setCache(false);
		tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}
	
	
	/**
	 * 增加不同的视图解析器，根据后缀名实现。xls视图、pdf视图等（没有后缀名则转发到jsp页面）
	 * resolvers添加不同的视图解析器链
	 * 多个视图解析器要设置名称以防止冲突
	 * @param manager
	 * @return
	 */
	@Bean 
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager){
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		List<ViewResolver> resolvers = new ArrayList<>();
		resolvers.add(viewResolver());
		resolvers.add(xlsViewResolver());
		resolver.setViewResolvers(resolvers);
		return resolver;
		
	}
	@Bean
	public XlsViewResolver xlsViewResolver(){
		XlsViewResolver xlsViewResolver = new XlsViewResolver();
		return xlsViewResolver;
	}
	@Bean
	public UrlBasedViewResolver viewResolver(){
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		urlBasedViewResolver.setPrefix("/WEB-INF/views/");
		urlBasedViewResolver.setSuffix(".jsp");
		urlBasedViewResolver.setViewClass(JstlView.class);
		urlBasedViewResolver.setViewNames(new String[]{"test/*"});
		return urlBasedViewResolver;
	}

}
